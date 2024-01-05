package io.qifan.mall.server.inviter.history.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.history.entity.InviteHistory;
import io.qifan.mall.server.inviter.history.entity.InviteHistoryDraft;
import io.qifan.mall.server.inviter.history.entity.InviteHistoryTable;
import io.qifan.mall.server.inviter.history.entity.dto.InviteHistorySpec;
import io.qifan.mall.server.inviter.history.repository.InviteHistoryRepository;
import io.qifan.mall.server.inviter.root.entity.Inviter;
import io.qifan.mall.server.inviter.root.entity.InviterFetcher;
import io.qifan.mall.server.inviter.root.entity.InviterTable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class InviteHistoryService {
    private final InviteHistoryRepository inviteHistoryRepository;
    private final JSqlClient sqlClient;

    public InviteHistory findById(String id) {
        return inviteHistoryRepository.findById(id, InviteHistoryRepository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(String code) {
        InviteHistoryTable historyTable = InviteHistoryTable.$;
        InviteHistory inviteHistory = inviteHistoryRepository.sql().createQuery(historyTable)
                .where(historyTable.inviteeId().eq(StpUtil.getLoginIdAsString()))
                .select(historyTable)
                .fetchOneOrNull();
        if (inviteHistory != null) return "";
        return inviteHistoryRepository.save(InviteHistoryDraft.$.produce(draft -> {
            InviterTable inviterTable = InviterTable.$;
            // 查询出邀请者的信息
            Inviter inviter = sqlClient.createQuery(inviterTable)
                    .where(inviterTable.code().eq(code))
                    .select(inviterTable.fetch(InviterFetcher.$.creator(true)))
                    .fetchOptional()
                    .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "邀请码不存在"));
            draft.setInviter(inviter.creator());
            draft.setStatus(1);
            draft.applyInvitee(userDraft -> userDraft.setId(StpUtil.getLoginIdAsString()));
        })).id();
    }

    public Page<InviteHistory> query(QueryRequest<InviteHistorySpec> queryRequest) {
        return inviteHistoryRepository.findPage(queryRequest, InviteHistoryRepository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        inviteHistoryRepository.deleteAllById(ids);
        return true;
    }

    /**
     * 当用户扫描进入小程序时，记录邀请码被点击。
     *
     * @param code
     * @return
     */
    public Boolean check(String code) {
        inviteHistoryRepository.save(InviteHistoryDraft.$.produce(draft -> {
            InviterTable inviterTable = InviterTable.$;
            // 查询出邀请者的信息
            Inviter inviter = sqlClient.createQuery(inviterTable)
                    .where(inviterTable.code().eq(code))
                    .select(inviterTable.fetch(InviterFetcher.$.creator(true)))
                    .fetchOptional()
                    .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "邀请码不存在"));
            draft.setInviter(inviter.creator());
            draft.setStatus(0);
        }));
        return true;
    }

    public Page<InviteHistory> userInviteHistory(QueryRequest<InviteHistorySpec> queryRequest) {
        InviteHistorySpec.TargetOf_inviter targetOfInviter = new InviteHistorySpec.TargetOf_inviter();
        targetOfInviter.setId(StpUtil.getLoginIdAsString());
        queryRequest.getQuery().setInviter(targetOfInviter);
        return inviteHistoryRepository.findPage(queryRequest, InviteHistoryRepository.COMPLEX_FETCHER);
    }
}
package io.qifan.mall.server.inviter.root.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.oss.service.OSSService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.root.entity.Inviter;
import io.qifan.mall.server.inviter.root.entity.InviterDraft;
import io.qifan.mall.server.inviter.root.entity.InviterTable;
import io.qifan.mall.server.inviter.root.entity.dto.InviterSpec;
import io.qifan.mall.server.inviter.root.repository.InviterRepository;
import java.io.ByteArrayInputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class InviterService {

  private final InviterRepository inviterRepository;
  private final WxMaService wxMaService;
  private final OSSService ossService;


  public Inviter findById(String id) {
    return inviterRepository.findById(id, InviterRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  @SneakyThrows
  public String save() {
    String code = RandomStringUtils.randomAlphanumeric(10);
    inviterRepository.sql().createQuery(InviterTable.$)
        .where(InviterTable.$.code().eq(code)).select(InviterTable.$)
        .fetchOptional().ifPresent((a) -> {
          throw new BusinessException(ResultCode.SaveError, "请重试");
        });
    byte[] wxaCodeUnlimitBytes = wxMaService.getQrcodeService()
        .createWxaCodeUnlimitBytes("code=" + code, "pages/index/index", false, "develop", 430, true,
            null, true);
    String qrCode = ossService.upload(code + ".png", new ByteArrayInputStream(wxaCodeUnlimitBytes));
    Inviter inviter = InviterDraft.$.produce(draft -> {
      draft.setCode(code)
          .setQrCode(qrCode);
    });
    return inviterRepository.save(inviter).id();
  }

  public Page<Inviter> query(QueryRequest<InviterSpec> queryRequest) {
    return inviterRepository.findPage(queryRequest, InviterRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    inviterRepository.deleteAllById(ids);
    return true;
  }

  public Inviter getInviteInfo() {
    InviterTable inviterTable = InviterTable.$;
    return inviterRepository.sql().createQuery(inviterTable)
        .where(inviterTable.creator().id().eq(StpUtil.getLoginIdAsString())).select(inviterTable)
        .fetchOne();
  }
}
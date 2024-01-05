package io.qifan.mall.server.auth;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.mall.server.auth.model.AuthModel;
import io.qifan.mall.server.auth.model.WeChatAuth;
import io.qifan.mall.server.inviter.history.service.InviteHistoryService;
import io.qifan.mall.server.user.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service(IAuthStrategy.WECHAT)
@Slf4j
@AllArgsConstructor
public class WechatAuthStrategyImpl implements IAuthStrategy {

    private final WxMaService wxMaService;
    private final StringRedisTemplate redisTemplate;
    private final JSqlClient jSqlClient;
    private final InviteHistoryService inviteHistoryService;
    private final static String OPENID_KEY = "openid:";

    @Override
    public SaTokenInfo auth(AuthModel authModel) {
        try {
            WeChatAuth weChatAuth = (WeChatAuth) authModel;
            WxMaJscode2SessionResult session = wxMaService.getUserService()
                    .getSessionInfo(weChatAuth.getLoginCode());
            String openid = session.getOpenid();
            Boolean isSet = redisTemplate.opsForValue()
                    .setIfAbsent(OPENID_KEY + openid, openid, 5, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(isSet)) {
                return null;
            }
            String phoneNumber = wxMaService.getUserService().getPhoneNoInfo(weChatAuth.getPhoneCode())
                    .getPhoneNumber();

            UserWechatTable wechatTable = UserWechatTable.$;
            Optional<UserWechat> weChatUserOptional = jSqlClient.createQuery(wechatTable)
                    .where(wechatTable.openId().eq(openid))
                    .select(wechatTable).fetchOptional();

            // openId -> 微信用户是否存在 -> 不存在则创建微信用户
            UserWechat userWeChat = weChatUserOptional.orElseGet(
                    () -> jSqlClient.insert(UserWechatDraft.$.produce(draft -> {
                        UserTable userTable = UserTable.$;
                        // 如果该手机号通过别的渠道已经在本系统创建过，则关联。否则用该手机号创建一个用户
                        User userInfo = jSqlClient.createQuery(userTable)
                                .where(UserTable.$.phone().eq(phoneNumber)).select(userTable)
                                .fetchOptional().orElseGet(() -> UserDraft.$.produce(userDraft -> {
                                    userDraft.setNickname("默认用户")
                                            .setPhone(phoneNumber);
                                }));
                        draft.setUser(userInfo);
                        draft.setOpenId(openid);
                    })).getModifiedEntity());
            StpUtil.login(userWeChat.user().id(), LoginDevice.MP_WECHAT);
            if (StringUtils.hasText(weChatAuth.getInviteCode())) {
                inviteHistoryService.save(weChatAuth.getInviteCode());
            }
            return StpUtil.getTokenInfo();
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}

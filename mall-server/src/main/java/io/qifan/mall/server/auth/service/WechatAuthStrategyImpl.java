package io.qifan.mall.server.auth.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.security.AuthErrorCode;
import io.qifan.mall.server.auth.model.AuthModel;
import io.qifan.mall.server.auth.model.LoginDevice;
import io.qifan.mall.server.auth.model.WeChatAuth;
import io.qifan.mall.server.user.wechat.entity.UserWeChat;
import io.qifan.mall.server.user.wechat.entity.UserWeChatTable;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;

@Service(IAuthStrategy.WECHAT)
@Slf4j
@AllArgsConstructor
public class WechatAuthStrategyImpl implements IAuthStrategy {
  private final WxMaService wxMaService;
  private final JSqlClient jSqlClient;

  @SneakyThrows
  @Override
  public SaTokenInfo auth(AuthModel authModel) {
    WeChatAuth weChatAuth = (WeChatAuth) authModel;
    // 能解析出openId就已经代表认证成功
    WxMaJscode2SessionResult session = wxMaService.getUserService()
        .getSessionInfo(weChatAuth.getLoginCode());
    String openid = session.getOpenid();
    UserWeChatTable t = UserWeChatTable.$;
    UserWeChat userWechat = jSqlClient.createQuery(t)
        .where(t.openId().eq(openid))
        .select(t)
        .fetchOptional()
        .orElseThrow(
            () -> new BusinessException(AuthErrorCode.USER_PERMISSION_UNAUTHENTICATED, "请绑定手机号"));
    // 登录的设备是微信
    StpUtil.login(userWechat.user().id(), LoginDevice.MP_WECHAT);
    return StpUtil.getTokenInfo();
  }
}

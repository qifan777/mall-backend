package io.qifan.mall.server.user.wechat.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.sms.SmsService;
import io.qifan.mall.server.auth.model.LoginDevice;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserDraft;
import io.qifan.mall.server.user.entity.UserTable;
import io.qifan.mall.server.user.repository.UserRepository;
import io.qifan.mall.server.user.wechat.entity.UserWeChat;
import io.qifan.mall.server.user.wechat.entity.UserWeChatDraft;
import io.qifan.mall.server.user.wechat.entity.UserWeChatTable;
import io.qifan.mall.server.user.wechat.entity.dto.UserWeChatInput;
import io.qifan.mall.server.user.wechat.entity.dto.UserWeChatSpec;
import io.qifan.mall.server.user.wechat.model.UserWeChatRegisterInput;
import io.qifan.mall.server.user.wechat.repository.UserWeChatRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserWeChatService {

  private final UserWeChatRepository userWeChatRepository;
  private final SmsService smsService;
  private final UserRepository userRepository;
  private final WxMaService wxMaService;

  public UserWeChat findById(String id) {
    return userWeChatRepository.findById(id, UserWeChatRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(UserWeChatInput userWeChatInput) {
    return userWeChatRepository.save(userWeChatInput).id();
  }

  public Page<UserWeChat> query(QueryRequest<UserWeChatSpec> queryRequest) {
    return userWeChatRepository.findPage(queryRequest, UserWeChatRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    userWeChatRepository.deleteAllById(ids);
    return true;
  }

  @SneakyThrows
  public SaTokenInfo register(UserWeChatRegisterInput registerInput) {
    boolean checked = smsService.checkSms(registerInput.getPhone(), registerInput.getCode());
    if (!checked) {
      throw new BusinessException(ResultCode.ValidateError, "验证码错误");
    }
    UserWeChatTable t1 = UserWeChatTable.$;
    WxMaJscode2SessionResult session = wxMaService.getUserService()
        .getSessionInfo(registerInput.getLoginCode());
    String openid = session.getOpenid();
    // 微信注册的流程如下
    // 1. 先查询微信用户表记录是否存在
    // 2. 如果存在,则直接返回登录信息
    // 3. 如果不存在,则说明该用户是第一次通过微信小程序使用起凡商城,则需要在微信用户表注册
    // 4. 创建微信用户需要手机号用户和openId(微信小程序用户唯一标识)
    // 5. 查询手机号用户是否存在(假设起凡商城还有app端,有些用户可能在app端可能注册过了)
    // 6. 如果存在直接返回手机号用户信息创建微信用户
    // 7. 如果不存在则说明用户是第一次使用起凡商城,则用手机号注册用户.
    // 8. 创建微信用户,并关联手机号用户
    // 9. 用手机号用户id登录.注意: 登录的id一定是user表id而不是第三方用户表user_wechat表的id.
    UserWeChat userWeChat = userWeChatRepository.sql()
        .createQuery(t1)
        .where(t1.openId().eq(openid))
        .select(t1)
        .fetchOptional()
        // 如果用openId去查询微信用户表记录为空,则说明该用户从未在小程序登录过
        .orElseGet(() -> {
          UserTable t2 = UserTable.$;
          // 查询手机号对应的用户
          User user = userRepository.sql().createQuery(t2)
              .where(t2.phone().eq(registerInput.getPhone()))
              .select(t2)
              .fetchOptional()
              // 手机号查询的用户为空,则说明该用户从未使用过起凡商城
              .orElseGet(() -> {
                return userRepository.save(UserDraft.$.produce(draft -> {
                  draft.setNickname("微信用户")
                      // 此处密码无需加密,
                      .setPassword("123456")
                      .setPhone(registerInput.getPhone());
                }));
              });
          // 创建微信用户,将手机号对应的用户和微信的openId绑定.
          return userWeChatRepository.save(UserWeChatDraft.$.produce(draft -> {
            draft.setUser(user)
                .setOpenId(openid);
          }));
        });
    StpUtil.login(userWeChat.user().id(), LoginDevice.MP_WECHAT);
    return StpUtil.getTokenInfo();
  }
}
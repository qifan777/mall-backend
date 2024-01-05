package io.qifan.mall.server.auth;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.qifan.mall.server.auth.model.PhonePasswordAuth;
import io.qifan.mall.server.auth.model.WeChatAuth;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private final Map<String, IAuthStrategy> authStrategyMap;

    @PostMapping("phone-password")
    public SaTokenInfo authByPhonePassword(@RequestBody PhonePasswordAuth phonePasswordAuth) {
        return authStrategyMap.get(IAuthStrategy.PASSWORD).auth(phonePasswordAuth);
    }

    @PostMapping("wechat")
    public SaTokenInfo authByWecChat(@RequestBody WeChatAuth weChatAuth) {
        return authStrategyMap.get(IAuthStrategy.WECHAT).auth(weChatAuth);
    }
}

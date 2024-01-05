package io.qifan.mall.server.auth;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.security.AuthErrorCode;
import io.qifan.mall.server.auth.model.AuthModel;
import io.qifan.mall.server.auth.model.PhonePasswordAuth;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.entity.UserTable;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;

@Service(IAuthStrategy.PASSWORD)
@AllArgsConstructor
public class PasswordAuthStrategyImpl implements IAuthStrategy {

    private final JSqlClient jSqlClient;


    @Override
    public SaTokenInfo auth(AuthModel authModel) {
        PhonePasswordAuth phonePasswordAuth = (PhonePasswordAuth) authModel;
        UserTable userTable = UserTable.$;
        // 从数据库去根据手机号找到相应的用户
        User databaseUser = jSqlClient.createQuery(userTable)
                .where(userTable.phone().eq(phonePasswordAuth.getPhoneNumber()))
                .select(userTable.fetch(UserFetcher.$.allScalarFields()))
                .fetchOptional().orElseThrow(() -> new BusinessException(AuthErrorCode.USER_LOGIN_NOT_EXIST));

        // 将请求用户的密码与数据库密码进行比对
        // BCrypt
        if (!BCrypt.checkpw(phonePasswordAuth.getPassword(), databaseUser.password())) {
            throw new BusinessException(AuthErrorCode.USER_LOGIN_PASSWORD_ERROR);
        }
        // 生成token记录
        StpUtil.login(databaseUser.id(), LoginDevice.BROWSER);

        return StpUtil.getTokenInfo();
    }
}

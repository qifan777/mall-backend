package io.qifan.mall.server.user.controller;

import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.entity.UserTable;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.LikeMode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

  public final static UserFetcher userFetcher = UserFetcher.$.avatar().gender();
  private final JSqlClient jSqlClient;

  @PostMapping("list")
  public List<@FetchBy(value = "userFetcher") User> list(@RequestParam String nickname) {
    UserTable t1 = UserTable.$;
    return jSqlClient.createQuery(t1)
        .where(t1.nickname().like(nickname, LikeMode.ANYWHERE))
        .select(t1.fetch(userFetcher))
        .execute();
  }
}

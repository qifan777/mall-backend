package io.qifan.mall.server.agent;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.mall.server.coupon.user.event.UserEvent.UserCreateEvent;
import io.qifan.mall.server.dict.model.DictConstants.Gender;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserDraft;
import io.qifan.mall.server.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class AgentTest {

  @Autowired
  ApplicationEventPublisher eventPublisher;
  @Autowired
  UserRepository userRepository;

  @Test
  public void userCreateTest() {
    User user = UserDraft.$.produce(draft -> draft.setNickname("起凡").setPhone("1365698799")
        .setGender(Gender.MALE));
    StpUtil.login("0f07d638f1bc401188d86dc650ab06a7");
    eventPublisher.publishEvent(new UserCreateEvent("0f07d638f1bc401188d86dc650ab06a7", null));
//    eventPublisher.publishEvent(new UserCreateEvent(user.id(),""));

  }
}

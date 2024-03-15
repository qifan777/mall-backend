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
//    User user = userRepository.save(
//        UserDraft.$.produce(draft -> draft.setNickname("起凡3").setPhone("1365698790")
//            .setPassword("123456")
//            .setGender(Gender.MALE)));
//    StpUtil.login(user.id());
//    eventPublisher.publishEvent(
//        new UserCreateEvent(user.id(), "9MPvc3G0"));
  }
  @Test
  public void orderRebateTest(){

  }
}

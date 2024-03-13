package io.qifan.mall.server.coupon.user.event;

import lombok.Data;

@Data
public class UserEvent {

  public record UserCreateEvent(String userId, String inviteCode) {

  }
}

package io.qifan.mall.server.coupon.user.model;

import java.util.List;
import lombok.Data;

@Data
public class GiftCouponInput {

  String couponId;
  List<String> userIds;

}

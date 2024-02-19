package io.qifan.mall.server.order.service.processor;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result.DecryptNotifyResult;
import io.qifan.mall.server.order.entity.ProductOrder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NotifyWeChatContext {
  private DecryptNotifyResult decryptNotifyResult;
  private ProductOrder productOrder;
}

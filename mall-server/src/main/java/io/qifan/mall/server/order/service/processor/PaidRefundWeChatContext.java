package io.qifan.mall.server.order.service.processor;

import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.refund.entity.RefundRecord;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaidRefundWeChatContext {

  private ProductOrder productOrder;
  private RefundRecord refundRecord;
  private WxPayRefundV3Request payRefundV3Request;
  private WxPayRefundV3Result payRefundV3Result;
}

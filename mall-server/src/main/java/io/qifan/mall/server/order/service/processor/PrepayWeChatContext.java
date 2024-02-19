package io.qifan.mall.server.order.service.processor;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PrepayWeChatContext {

  private String orderId;
  private WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request;
  private WxPayUnifiedOrderV3Result.JsapiResult jsapiResult;
}

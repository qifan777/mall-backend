package io.qifan.mall.server.order.service.processor;

import cn.dev33.satoken.stp.StpUtil;
import com.binarywang.spring.starter.wxjava.pay.properties.WxPayProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result.JsapiResult;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.model.WxPayPropertiesExtension;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.entity.ProductOrderItemFetcher;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.payment.entity.PaymentFetcher;
import io.qifan.mall.server.product.sku.entity.ProductSkuFetcher;
import io.qifan.mall.server.user.wechat.entity.UserWeChat;
import io.qifan.mall.server.user.wechat.entity.UserWeChatTable;
import io.qifan.mall.server.user.wechat.repository.UserWeChatRepository;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@OrderStateProcessor(state = "TO_BE_PAID", event = "PREPAY", sceneId = "WE_CHAT_PAY")
@Slf4j
@AllArgsConstructor
public class PrepayWeChatProcessor extends
    AbstractStateProcessor<WxPayUnifiedOrderV3Result.JsapiResult, PrepayWeChatContext> {

  private final WxPayService wxPayService;
  private final ObjectMapper objectMapper;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ProductOrderRepository productOrderRepository;
  private final UserWeChatRepository userWeChatRepository;
  private final WxPayPropertiesExtension wxPayPropertiesExtension;
  private final WxPayProperties wxPayProperties;

  @Override
  public void prepare(StateContext<PrepayWeChatContext> context) {
    ProductOrder productOrder =
        productOrderRepository.findById(context.getContext().getOrderId(),
                ProductOrderFetcher.$.allScalarFields().payment(PaymentFetcher.$.allScalarFields()))
            .orElseThrow(() -> new BusinessException(ResultCode.NotFindError));
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    ZonedDateTime dateTime = ZonedDateTime.now().plusHours(1);
    // 查找当前用户关联的微信用户
    UserWeChatTable weChatTable = UserWeChatTable.$;
    UserWeChat userWeChat = userWeChatRepository.sql().createQuery(weChatTable)
        .where(weChatTable.userId().eq(StpUtil.getLoginIdAsString()))
        .select(weChatTable)
        .fetchOptional()
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "非小程序用户, 请注册"));
    WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = new WxPayUnifiedOrderV3Request();
    // 支付价格
    WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
    amount.setTotal(productOrder.payment().payAmount()
        .multiply(BigDecimal.valueOf(
            100)).intValue());
    // 获取支付人信息
    WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
    payer.setOpenid(userWeChat.openId());
    wxPayUnifiedOrderV3Request.setPayer(payer)
        .setAmount(amount)
        // 回调地址
        .setNotifyUrl(wxPayPropertiesExtension.getNotifyUrl())
        // 小程序appid
        .setAppid(wxPayProperties.getAppId())
        // 商家号
        .setMchid(wxPayProperties.getMchId())
        // 支付描述
        .setDescription(productOrder.remark())
        // 订单系统的订单号
        .setOutTradeNo(productOrder.id())
        // 过期时间
        .setTimeExpire(dateTimeFormatter.format(dateTime))
        .setDescription("www.jarcheng.top");
    context.getContext().setWxPayUnifiedOrderV3Request(wxPayUnifiedOrderV3Request);
    log.info("预支付订单内容：{}", wxPayUnifiedOrderV3Request);
  }

  @Override
  @SneakyThrows
  public R<JsapiResult> check(StateContext<PrepayWeChatContext> context) {
    ProductOrder productOrder = productOrderRepository
        .findById(context.getContext().getOrderId(),
            ProductOrderFetcher.$
                .creator(true)
                .items(ProductOrderItemFetcher.$
                    .skuCount()
                    .productSku(ProductSkuFetcher.$.allScalarFields())))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    if (!StpUtil.getLoginIdAsString().equals(productOrder.creator().id())) {
      throw new BusinessException(ResultCode.NotGrant, "无法支付他人订单");
    }
    Object prepayRes = redisTemplate.opsForValue()
        .get("prepay:" + context.getContext().getOrderId());
    if (prepayRes != null) {
      log.info("已存在预支付订单：{}", prepayRes);
      // 如果存在预支付订单则直接发起支付
      WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = objectMapper.readValue(
          objectMapper.writeValueAsString(prepayRes), WxPayUnifiedOrderV3Result.JsapiResult.class);
      R<WxPayUnifiedOrderV3Result.JsapiResult> ok = R.ok(jsapiResult);
      // 不执行后续的方法
      ok.setCode(ResultCode.Fail.getCode());
      return ok;
    }
    return R.ok(null);
  }

  @Override
  public String getNextState(StateContext<PrepayWeChatContext> context) {
    // 状态不变，仍然是待支付状态
    return ProductOrderStatus.TO_BE_PAID.getKeyEnName();
  }

  @Override
  public R<WxPayUnifiedOrderV3Result.JsapiResult> action(String nextState,
      StateContext<PrepayWeChatContext> context) {
    JsapiResult
        wxPayAppOrderResult;
    try {
      wxPayAppOrderResult = wxPayService.createOrderV3(TradeTypeEnum.JSAPI,
          context.getContext()
              .getWxPayUnifiedOrderV3Request());
    } catch (WxPayException e) {
      throw new BusinessException(ResultCode.TransferStatusError, e.getReturnMsg());
    }
    log.info("预支付订单，下单成功：{}", wxPayAppOrderResult);
    context.getContext().setJsapiResult(wxPayAppOrderResult);
    return R.ok(wxPayAppOrderResult);
  }

  @Override
  public R<WxPayUnifiedOrderV3Result.JsapiResult> save(String nextState,
      StateContext<PrepayWeChatContext> context) {
    log.info("保存预支付订单");
    // 一个小时后预支付订单过期
    redisTemplate.opsForValue()
        .set("prepay:" + context.getContext().getOrderId(),
            context.getContext().getJsapiResult(),
            1, TimeUnit.HOURS);
    return R.ok(context.getContext().getJsapiResult());
  }


  @Override
  public void after(StateContext<PrepayWeChatContext> context) {

  }
}

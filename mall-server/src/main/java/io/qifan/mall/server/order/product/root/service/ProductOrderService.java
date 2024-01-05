package io.qifan.mall.server.order.product.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.service.CouponUserService;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.product.root.entity.ProductOrder;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderCalculateInput;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderCalculateInput.TargetOf_items;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderPriceView;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.product.root.repository.ProductOrderRepository;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductOrderService {

  private final ProductOrderRepository productOrderRepository;
  private final ProductSkuRepository productSkuRepository;
  private final CouponUserService couponUserService;

  public ProductOrder findById(String id) {
    return productOrderRepository.findById(id, ProductOrderRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(ProductOrderInput productOrderInput) {
    return productOrderRepository.save(productOrderInput).id();
  }

  public Page<ProductOrder> query(QueryRequest<ProductOrderSpec> queryRequest) {
    return productOrderRepository.findPage(queryRequest, ProductOrderRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    productOrderRepository.deleteAllById(ids);
    return true;
  }

  public ProductOrderPriceView calculate(ProductOrderCalculateInput calculateInput) {
    ProductOrderPriceView productOrderPriceView = new ProductOrderPriceView();
    productOrderPriceView.setVipAmount(BigDecimal.ZERO);
    productOrderPriceView.setDeliveryFee(BigDecimal.ZERO);
    productOrderPriceView.setCouponAmount(BigDecimal.ZERO);
    productOrderPriceView.setAvailableCoupons(List.of());
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (TargetOf_items item : calculateInput.getItems()) {
      ProductSku productSku = productSkuRepository.findById(item.getProductSkuId())
          .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
      totalPrice = totalPrice.add(
          productSku.price().multiply(BigDecimal.valueOf(item.getProductCount())));
    }
    productOrderPriceView.setProductAmount(totalPrice);
    List<CouponUser> couponUsers = couponUserService.availableCoupons(totalPrice);
    if (!couponUsers.isEmpty()) {
      productOrderPriceView.setAvailableCoupons(couponUsers.stream().map(BaseEntity::id).toList());
    }
    if (StringUtils.hasText(calculateInput.getCouponUser().getId())) {
      productOrderPriceView.setCouponAmount(
          couponUserService.calculate(calculateInput.getCouponUser().getId(), totalPrice));
    }
    return productOrderPriceView;
  }
}
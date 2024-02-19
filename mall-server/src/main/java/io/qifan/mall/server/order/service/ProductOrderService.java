package io.qifan.mall.server.order.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.infrastructure.statemachine.machine.StateMachine;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext.StateEvent;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.service.processor.NewCreateContext;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductOrderService {

  private final ProductOrderRepository productOrderRepository;
  private final StateMachine stateMachine;

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

  public String create(ProductOrderInput productOrderInput) {
    StateEvent stateEvent = StateEvent.builder()
        .orderState(ProductOrderStatus.TO_BE_CREATE.getKeyEnName())
        .eventType("CREATE")
        .sceneId("*")
        .businessCode("*").build();
    NewCreateContext newCreateContext = new NewCreateContext().setProductOrderInput(
        productOrderInput);
    R<String> res = stateMachine.action(new StateContext<>(stateEvent, newCreateContext));
    return res.getResult();
  }
}
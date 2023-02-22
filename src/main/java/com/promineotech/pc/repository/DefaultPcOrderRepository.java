/**
 * 
 */
package com.promineotech.pc.repository;

import javax.validation.Valid;
import org.springframework.stereotype.Component;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent Ma
 *
 */
@Component
@Slf4j
public class DefaultPcOrderRepository implements PcOrderRepository {

  @Override
  public Order createOrder(@Valid OrderRequest orderRequest) {
    log.debug("Order={}",orderRequest);
    return null;
  }

}

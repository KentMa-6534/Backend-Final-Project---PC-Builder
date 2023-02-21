/**
 * 
 */
package com.promineotech.pc.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultPcOrderController implements PcOrderController {

  @Override
  public Order createOrder(@Valid OrderRequest orderRequest) {
    log.debug("Order={}", orderRequest);
    return null;
  }

}

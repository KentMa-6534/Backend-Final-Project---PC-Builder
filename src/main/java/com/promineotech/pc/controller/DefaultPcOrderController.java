/**
 * 
 */
package com.promineotech.pc.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.service.PcOrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultPcOrderController implements PcOrderController {

  @Autowired
  private PcOrderService pcOrderService;
  
  @Override
  public Order createOrder(@Valid OrderRequest orderRequest) {
    log.debug("Order={}", orderRequest);
    return pcOrderService.createOrder(orderRequest);
  }

}

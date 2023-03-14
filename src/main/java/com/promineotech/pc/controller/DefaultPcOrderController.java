/**
 * 
 */
package com.promineotech.pc.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.repository.PcOrderRepository;
import com.promineotech.pc.service.PcOrderService;
import lombok.extern.slf4j.Slf4j;
/*
 * This class tells Spring Boot to marshal and unmarshal JSON to Java and tells Spring to expect 
 * controller mapping within application.
 */
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

  @Override
  public List<OrderPk> fetchOrders(Long orderPk) {
    log.info("orderPk={}", orderPk);
    return pcOrderService.fetchOrders(orderPk);
  }

  @Override
  public Optional<OrderPk> updateOrder(Long orderPk, Long cpuPk) {
    log.info("orderPk={}, cpuPK={}", orderPk, cpuPk);
    return pcOrderService.updateOrders(orderPk, cpuPk);
  }

  @Override
  public OrderPk deleteOrder(Long orderPk) {
    log.info("orderPk={}", orderPk);
    return pcOrderService.deleteOrders(orderPk);
  }
  
 




}

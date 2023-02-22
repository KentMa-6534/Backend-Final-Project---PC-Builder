/**
 * 
 */
package com.promineotech.pc.service;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.repository.PcOrderRepository;

/**
 * @author Kent Ma
 *
 */
@Service
public class DefaultPcOrderService implements PcOrderService {
  @Autowired
  private PcOrderRepository pcOrderRepository;

  @Override
  public Order createOrder(@Valid OrderRequest orderRequest) {
    // TODO Auto-generated method stub
    return pcOrderRepository.createOrder(orderRequest);
  }

}

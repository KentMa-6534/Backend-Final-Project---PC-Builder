/**
 * 
 */
package com.promineotech.pc.repository;

import javax.validation.Valid;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;

/**
 * @author Kent Ma
 *
 */
public interface PcOrderRepository {

  /**
   * @param orderRequest
   * @return
   */
  Order createOrder(@Valid OrderRequest orderRequest);

}

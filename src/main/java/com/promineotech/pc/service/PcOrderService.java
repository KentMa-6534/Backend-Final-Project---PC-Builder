/**
 * 
 */
package com.promineotech.pc.service;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;

/**
 * @author Kent Ma
 * Allows controller level to be built after interface is created and allows service layer 
 * to be built while controller is active. Allows for easier mocking implementation. 
 */
public interface PcOrderService {

  /**
   * @param orderRequest
   * @return
   */
  Order createOrder(@Valid OrderRequest orderRequest);

  /**
   * @param orderPk
   * @return
   */
  List<OrderPk> fetchOrders(Long orderPk);

  /**
   * @param orderPk
   * @param cpuPk
   * @return
   */
  Optional<OrderPk> updateOrders(Long orderPk, Long cpuPk);

  /**
   * @param orderPk
   * @return
   */
  OrderPk deleteOrders(Long orderPk);

  /**
   * @param orderRequest
   * @return
   */

}

/**
 * 
 */
package com.promineotech.pc.entity.order;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kent Ma
 * Contains primary keys of all entities to perform GET, PUT, and DELETE operations on the Order entity.
 */
@Data
@Builder
public class OrderPk {
  private Long orderPk;
  private Long customerPk;
  private Long cpuPk;
  private Long cpuCoolerPk;
  private Long motherboardPk;
  private Long memoryPk;
  private Long storagePk;
  private Long videoCardPk;
  private Long pcCasePk;
  private Long powerSupplyPk;
  private Long price;
}

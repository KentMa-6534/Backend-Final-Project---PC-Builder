/**
 * 
 */
package com.promineotech.pc.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.customer.Customer;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;

/**
 * @author Kent Ma
 *
 */
public interface PcOrderRepository {

  Optional<Customer> fetchCustomer(String customer);

  /**
   * @param cpu
   * @return
   */
  Optional<Cpu> fetchCpu(String cpu);

  /**
   * @param cpuCooler
   * @return
   */
  Optional<CpuCooler> fetchCpuCooler(@NotNull String cpuCooler);

  /**
   * @param motherboard
   * @return
   */
  Optional<Motherboard> fetchMotherboard(@NotNull String motherboard);
  /**
   * @param memory
   * @return
   */
  
  Optional<Memory> fetchMemory(@NotNull String memory);
  /**
   * @param storage
   * @return
   */
  
  Optional<Storage> fetchStorage(@NotNull String storage);

  /**
   * @param videoCard
   * @return
   */
  Optional<VideoCard> fetchVideoCard(@NotNull String videoCard);

  /**
   * @param pcCase
   * @return
   */
  Optional<PcCase> fetchPcCase(@NotNull String pcCase);

  /**
   * @param powerSupply
   * @return
   */
  Optional<PowerSupply> fetchPowerSupply(@NotNull String powerSupply);

  /**
   * @param accessories
   * @return
   */
  List<Accessories>fetchAccessories(List<String> accessoryId);

  /**
   * @param customer
   * @param cpu
   * @param cpuCooler
   * @param motherboard
   * @param memory
   * @param storage
   * @param videoCard
   * @param pcCase
   * @param powerSupply
   * @param price
   * @param accessories 
   * @return
   * 
   * Method to put an order in the Order table
   */
  Order saveOrder(Customer customer, Cpu cpu, CpuCooler cpuCooler, Motherboard motherboard,
      Memory memory, Storage storage, VideoCard videoCard, PcCase pcCase, PowerSupply powerSupply,
      BigDecimal price, List<Accessories> accessories);

  /**
   * @param orderPk
   * @return
   * Method to read orders in the Order table
   */
  List<OrderPk> fetchOrders(Long orderPk);

  /**
   * @param orderPk
   * @param cpuPk
   * @return
   * Method to update orders in the Order table
   */
  Optional<OrderPk> updateOrders(Long orderPk, Long cpuPk);

  /**
   * @param orderPk
   * @return
   * Method to delete orders in the Order table
   */
  OrderPk deleteOrders(Long orderPk);

  /**
   * @param customerId
   * @return
   */
}

/**
 * 
 */
package com.promineotech.pc.entity.order;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.customer.Customer;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import lombok.Builder;
import lombok.Data;
/*
 * Contains all entity classes necessary to create a POST operation for an order in this application.
 */
@Data
@Builder
public class Order {
  private Long orderPK;
  private Customer customer;
  private Cpu cpu;
  private CpuCooler cpuCooler;
  private Motherboard motherboard;
  private Memory memory;
  private Storage storage;
  private VideoCard videoCard;
  private PcCase pcCase;
  private PowerSupply powerSupply;
  private List<Accessories> accessories;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getOrderPK() {
    return orderPK;
  }
  }


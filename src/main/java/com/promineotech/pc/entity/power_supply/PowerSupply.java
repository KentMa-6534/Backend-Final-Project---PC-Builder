/**
 * 
 */
package com.promineotech.pc.entity.power_supply;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.pc.entity.motherboard.CpuSocket;
import com.promineotech.pc.entity.motherboard.FormFactor;
import com.promineotech.pc.entity.motherboard.Motherboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * The Cpu class contains all information related to an order's power supply.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerSupply implements Comparable<PowerSupply>{
  private Long powerSupplyPK;
  private String powerSupplyId;
  private String manufacturer;
  private String powerSupplyName;
  private int wattage;
  private Modular modular;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getPowerSupplyPk() {
    return powerSupplyPK;
  }
  
  @Override
  public int compareTo(PowerSupply that) {
    //@formatter:off
    return Comparator
        .comparing(PowerSupply::getManufacturer)
        .thenComparing(PowerSupply:: getPowerSupplyName)
        .thenComparing(PowerSupply::getWattage)
        .thenComparing(PowerSupply::getModular)
        .compare(this, that);
    //@formatter:on
  }
}

/**
 * 
 */
package com.promineotech.pc.entity.cpu_cooler;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpuCooler implements Comparable<CpuCooler>{
  private Long cpuCoolerPK;
  private String cpuCoolerId;
  private String manufacturer;
  private String coolerName;
  private boolean isWaterCooled;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getCpuCoolerPk() {
    return cpuCoolerPK;
  }
  
  @Override
  public int compareTo(CpuCooler that) {
    //@formatter:off
    return Comparator
        .comparing(CpuCooler::getCpuCoolerId)
        .thenComparing(CpuCooler:: getManufacturer)
        .thenComparing(CpuCooler::getCoolerName)
        .compare(this, that);
    //@formatter:on
  }
}

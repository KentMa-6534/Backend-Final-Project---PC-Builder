/**
 * 
 */
package com.promineotech.pc.entity.cpu;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * The Cpu class contains all information related to an order's CPU.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpu implements Comparable<Cpu> {
  private Long cpuPK;
  private String cpuId;
  private CpuBrand cpuBrand;
  private String cpuName;
  private int coreCount;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getCpuPk() {
    return cpuPK;
  }
  
  @Override
  public int compareTo(Cpu that) {
    //@formatter:off
    return Comparator
        .comparing(Cpu::getCpuBrand)
        .thenComparing(Cpu:: getCpuName)
        .thenComparing(Cpu::getCoreCount)
        .compare(this, that);
    //@formatter:on
  }
}

/**
 * 
 */
package com.promineotech.pc.entity.motherboard;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Motherboard implements Comparable<Motherboard>{
  private Long motherboardPK;
  private String motherboardId;
  private String manufacturer;
  private String motherboardName;
  private CpuSocket cpuSocket;
  private FormFactor formFactor;
  private int memorySlots;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getMotherboardPk() {
    return motherboardPK;
  }
  
  @Override
  public int compareTo(Motherboard that) {
    //@formatter:off
    return Comparator
        .comparing(Motherboard::getManufacturer)
        .thenComparing(Motherboard:: getMotherboardName)
        .thenComparing(Motherboard::getCpuSocket)
        .thenComparing(Motherboard::getFormFactor)
        .thenComparing(Motherboard::getMemorySlots)
        .compare(this, that);
    //@formatter:on
  }
}

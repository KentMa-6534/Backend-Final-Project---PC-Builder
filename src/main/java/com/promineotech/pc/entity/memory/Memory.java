/**
 * 
 */
package com.promineotech.pc.entity.memory;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memory implements Comparable<Memory>{
  private Long memoryPK;
  private String memoryId;
  private String manufacturer;
  private String memoryName;
  private MemoryType memoryType;
  private String memorySpeed;
  private String memorySize;
  private BigDecimal price;  
  
  @JsonIgnore
  public Long getMotherboarPk() {
    return memoryPK;
  }
  
  @Override
  public int compareTo(Memory that) {
    //@formatter:off
    return Comparator
        .comparing(Memory::getManufacturer)
        .thenComparing(Memory:: getMemoryName)
        .thenComparing(Memory::getMemoryType)
        .thenComparing(Memory::getMemorySpeed)
        .thenComparing(Memory::getMemorySize)
        .compare(this, that);
    //@formatter:on
  }
}

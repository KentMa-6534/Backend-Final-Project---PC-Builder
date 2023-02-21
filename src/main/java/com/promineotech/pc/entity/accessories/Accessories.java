/**
 * 
 */
package com.promineotech.pc.entity.accessories;

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
public class Accessories implements Comparable<Accessories>{
  private Long accessoryPK;
  private String accessoryId;
  private Category category;
  private String manufacturer;
  private String accessoryName;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getAccessoryPk() {
    return accessoryPK;
  }
  
  @Override
  public int compareTo(Accessories that) {
    //@formatter:off
    return Comparator
        .comparing(Accessories::getCategory)
        .thenComparing(Accessories::getManufacturer)
        .thenComparing(Accessories::getAccessoryName)
        .compare(this, that);
    //@formatter:on
}
}

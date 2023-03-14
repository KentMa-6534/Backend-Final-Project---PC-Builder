/**
 * 
 */
package com.promineotech.pc.entity.pc_case;

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
 * The PcCase class contains all information related to an order's Pc Case.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PcCase implements Comparable<PcCase> {
  private Long casePK;
  private String caseId;
  private String manufacturer;
  private String caseName;
  private CaseType caseType;
  private Color color;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getCasePk() {
    return casePK;
  }
  
  @Override
  public int compareTo(PcCase that) {
    //@formatter:off
    return Comparator
        .comparing(PcCase::getManufacturer)
        .thenComparing(PcCase:: getCaseName)
        .thenComparing(PcCase::getCaseType)
        .thenComparing(PcCase::getColor)
        .compare(this, that);
    //@formatter:on
  }
}

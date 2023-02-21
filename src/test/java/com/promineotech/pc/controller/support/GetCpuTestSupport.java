/**
 * 
 */
package com.promineotech.pc.controller.support;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;

/**
 * @author Kent Ma
 *
 */
public class GetCpuTestSupport extends BaseTest{
  protected List<Cpu> buildExpected() {
    List<Cpu> list = new LinkedList<>();
    // @formatter:off
    list.add(Cpu.builder()
        .cpuBrand(CpuBrand.INTEL)
        .cpuName("Core i7-13700K")
        .coreCount(16)
        .price(new BigDecimal("410.00"))
        .build()
        );
    // @formatter:on
    
    Collections.sort(list);
    return list;
  }

  protected void AssertErrorMessageValid(Map<String, Object> error, HttpStatus status) {
    //@formatter:off
    assertThat(error)
      .containsKey("message")
      .containsEntry("status code", status.value())
      .containsEntry("uri", "/pc-builder/search/cpu")
      .containsKey("timestamp")
      .containsEntry("reason", status.getReasonPhrase());
    //@formatter:on
  }
  
}

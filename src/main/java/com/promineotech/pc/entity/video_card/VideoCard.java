/**
 * 
 */
package com.promineotech.pc.entity.video_card;

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
public class VideoCard implements Comparable<VideoCard>{
  private Long videoCardPK;
  private String videoCardId;
  private VideoCardBrand videoCardBrand;
  private String videoCardName;
  private String videoCardMemory;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getvideoCardPk() {
    return videoCardPK;
  }
  
  @Override
  public int compareTo(VideoCard that) {
    //@formatter:off
    return Comparator
        .comparing(VideoCard::getVideoCardBrand)
        .thenComparing(VideoCard:: getVideoCardName)
        .thenComparing(VideoCard::getVideoCardMemory)
        .compare(this, that);
    //@formatter:on
  }
}

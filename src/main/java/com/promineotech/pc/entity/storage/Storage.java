/**
 * 
 */
package com.promineotech.pc.entity.storage;

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
 * The Storage class contains all information related to an order's storage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage implements Comparable<Storage>{
  private Long storagePK;
  private String storageId;
  private String manufacturer;
  private String storageName;
  private String storageCapacity;
  private String storageType;
  private boolean storageCache;
  private BigDecimal price;

  @JsonIgnore
  public Long getStoragePk() {
    return storagePK;
  }
  @Override
  public int compareTo(Storage that) {
    //@formatter:off
    return Comparator
        .comparing(Storage::getManufacturer)
        .thenComparing(Storage:: getStorageName)
        .thenComparing(Storage::getStorageCapacity)
        .thenComparing(Storage::getStorageType)
        .compare(this, that);
    //@formatter:on
  }
}

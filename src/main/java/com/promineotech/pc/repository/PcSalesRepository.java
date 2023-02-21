/**
 * 
 */
package com.promineotech.pc.repository;

import java.util.List;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.accessories.Category;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.memory.MemoryType;
import com.promineotech.pc.entity.motherboard.FormFactor;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.pc_case.CaseType;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.Modular;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import com.promineotech.pc.entity.video_card.VideoCardBrand;

/**
 * @author Kent Ma
 *
 */
public interface PcSalesRepository {

  List<Cpu> fetchCpus(CpuBrand cpuBrand, String name);

  /**
   * @param coolerBrand
   * @param isWaterCooled
   * @return
   */
  List<CpuCooler> fetchCpuCoolers(String coolerName, boolean isWaterCooled);

  /**
   * @param motherboardName
   * @param formFactor
   * @return
   */
  List<Motherboard> fetchMotherboard(String motherboardName, FormFactor formFactor);

  /**
   * @param memoryName
   * @param memoryType
   * @return
   */
  List<Memory> fetchMemory(String memoryName, MemoryType memoryType);

  /**
   * @param storageName
   * @param storageType
   * @return
   */
  List<Storage> fetchStorage(String storageName, String storageType);

  /**
   * @param videoCardBrand
   * @param videoCardName
   * @return
   */
  List<VideoCard> fetchVideoCards(VideoCardBrand videoCardBrand, String videoCardName);

  /**
   * @param caseName
   * @param caseType
   * @return
   */
  List<PcCase> fetchCases(String caseName, CaseType caseType);

  /**
   * @param powerSupplyName
   * @param modular
   * @return
   */
  List<PowerSupply> fetchPsu(String powerSupplyName, Modular modular);

  /**
   * @param category
   * @param accessoryName
   * @return
   */
  List<Accessories> fetchAccessories(Category category, String accessoryName);

}

/**
 * 
 */
package com.promineotech.pc.controller;

import java.util.List;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
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
import com.promineotech.pc.service.PcSalesService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent Ma
 *
 */
@RestController
@Slf4j
public class DefaultPcSalesController implements PcSalesController {
  
  @Autowired
  private PcSalesService pcSalesService;
  
  @Override
  public List<Cpu> fetchCpus(CpuBrand cpuBrand, String name) {
    log.debug("cpuBrand={}, name={}", cpuBrand, name);
    return pcSalesService.fetchCpus(cpuBrand, name);
  }

  @Override
  public List<CpuCooler> fetchCpuCoolers(String coolerName, boolean isWaterCooled) {
    log.debug("coolerBrand={}, isWaterCooled={}", coolerName, isWaterCooled);
    return pcSalesService.fetchCpuCoolers(coolerName, isWaterCooled);
  }

  @Override
  public List<Motherboard> fetchMotherboard( String motherboardName, FormFactor formFactor) {
    log.debug("motherboardName={}, formFactor={}", motherboardName, formFactor);
    return pcSalesService.fetchMotherboard(motherboardName, formFactor);
  }

  @Override
  public List<Memory> fetchMemory(String memoryName, MemoryType memoryType) {
    log.debug("memoryName={}, memoryType={}", memoryName, memoryType);
    return pcSalesService.fetchMemory(memoryName, memoryType);
  }

  @Override
  public List<Storage> fetchStorage(String storageName, String storageType) {
    log.debug("storageName={}, storageType={}", storageName, storageType);
    return pcSalesService.fetchStorage(storageName, storageType);
  }

  @Override
  public List<VideoCard> fetchVideoCards(VideoCardBrand videoCardBrand, String videoCardName) {
    log.debug("videoCardBrand={}, videoCardName={}", videoCardBrand, videoCardName);
    return pcSalesService.fetchVideoCards(videoCardBrand, videoCardName);
  }

  @Override
  public List<PcCase> fetchCases(String caseName, CaseType caseType) {
    log.debug("caseName={}, caseType={}", caseName, caseType);
    return pcSalesService.fetchCases(caseName, caseType);
  }

  @Override
  public List<PowerSupply> fetchPsu(String powerSupplyName, Modular modular) {
    log.debug("caseName={}, caseType={}", powerSupplyName, modular);
    return pcSalesService.fetchPsu(powerSupplyName, modular);
  }

  @Override
  public List<Accessories> fetchAccessories(Category category, String accessoryName) {
    log.debug("category={}, accessoryName={}", category, accessoryName);
    return pcSalesService.fetchAccessories(category, accessoryName);
  }

}

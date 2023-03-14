/**
 * 
 */
package com.promineotech.pc.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.promineotech.pc.repository.PcSalesRepository;
import lombok.extern.slf4j.Slf4j;
/*
 * Service tells Spring to manage entity services since all entities are targets for Bean Injection.
 * PCSalesService interface.
 */
@Service
@Slf4j
public class DefaultPcSalesService implements PcSalesService {
  
  @Autowired
  private PcSalesRepository pcSalesRepository;
  
  @Transactional(readOnly = true)
  @Override
  public List<Cpu> fetchCpus(CpuBrand cpuBrand, String name) {
    log.info("The fetchCpus method was called with cpuBrand={} and name={}", cpuBrand, name);
    
    List<Cpu> cpus = pcSalesRepository.fetchCpus(cpuBrand, name);
    
    if(cpus.isEmpty()) {
      String msg = String.format("No CPUs found with cpuBrand = %s and name = %s", cpuBrand, name);
      throw new NoSuchElementException(msg);
    }
    
    Collections.sort(cpus);
    return cpus;
  }
  @Transactional(readOnly = true)
  @Override
  public List<CpuCooler> fetchCpuCoolers(String coolerName, boolean isWaterCooled) {
    log.info("The fetchCpuCoolers method was called with coolerBrand={} and isWaterCooled={}", coolerName, isWaterCooled);
    
    List<CpuCooler> cpuCooler = pcSalesRepository.fetchCpuCoolers(coolerName, isWaterCooled);
    
    if(cpuCooler.isEmpty()) {
      String msg = String.format("No CPU Coolers found with coolerBrand = %s and isWaterCooled = %s", coolerName, isWaterCooled);
      throw new NoSuchElementException(msg);
    }
    
    Collections.sort(cpuCooler);
    return cpuCooler;
  }
  @Transactional(readOnly = true)
  @Override
  public List<Motherboard> fetchMotherboard(String motherboardName, FormFactor formFactor) {
    log.info("The fetchMotherboard method was called with motherboardName={} and formFactor={}", motherboardName, formFactor);
    
    List<Motherboard> motherboard = pcSalesRepository.fetchMotherboard(motherboardName, formFactor);
    
    if(motherboard.isEmpty()) {
      String msg = String.format("No motherboards found with motherboardName = %s and formFactor = %s", motherboardName, formFactor);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(motherboard);
    return motherboard;
  }
  @Transactional (readOnly = true)
  @Override
  public List<Memory> fetchMemory(String memoryName, MemoryType memoryType) {
    log.info("The fetchMemory method was called with memoryName={} and memoryType={}", memoryName, memoryType);
    
    List<Memory> memory = pcSalesRepository.fetchMemory(memoryName, memoryType);
    
    if(memory.isEmpty()) {
      String msg = String.format("No RAM found with memoryName = %s and memoryType = %s", memoryName, memoryType);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(memory);
    return memory;
  }
  @Transactional (readOnly = true)
  @Override
  public List<Storage> fetchStorage(String storageName, String storageType) {
    log.info("The fetchStorage method was called with storageName={} and storageType={}", storageName, storageType);
    
    List<Storage> storage = pcSalesRepository.fetchStorage(storageName, storageType);
    
    if(storage.isEmpty()) {
      String msg = String.format("No storage found with storageName = %s and storageType = %s", storageName, storageType);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(storage);
    return storage;

}
  @Transactional (readOnly = true)
  @Override
  public List<VideoCard> fetchVideoCards(VideoCardBrand videoCardBrand, String videoCardName) {
    log.info("The fetchVideoCards method was called with videoCardBrand={} and videoCardName={}", videoCardBrand, videoCardName);
    
    List<VideoCard> videoCard = pcSalesRepository.fetchVideoCards(videoCardBrand, videoCardName);
    
    if(videoCard.isEmpty()) {
      String msg = String.format("No video cards found with videoCardBrand = %s and videoCardName = %s", videoCardBrand, videoCardName);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(videoCard);
    return videoCard;
  }
  @Transactional(readOnly = true)
  @Override
  public List<PcCase> fetchCases(String caseName, CaseType caseType) {
    log.info("The fetchVideoCards method was called with caseName={} and caseType={}", caseName, caseType);
    
    List<PcCase> pcCase = pcSalesRepository.fetchCases(caseName, caseType);
    
    if(pcCase.isEmpty()) {
      String msg = String.format("No cases found with caseName = %s and caseType = %s", caseName, caseType);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(pcCase);
    return pcCase;
  }
  @Transactional(readOnly = true)
  @Override
  public List<PowerSupply> fetchPsu(String powerSupplyName, Modular modular) {
    log.info("The fetchPsu method was called with powerSupplyName={} and modular={}", powerSupplyName, modular);
    
    List<PowerSupply> psu = pcSalesRepository.fetchPsu(powerSupplyName, modular);
    
    if(psu.isEmpty()) {
      String msg = String.format("No cases found with caseName = %s and caseType = %s", powerSupplyName, modular);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(psu);
    return psu;
  }
  @Override
  public List<Accessories> fetchAccessories(Category category, String accessoryName) {
    log.info("The fetchAcessories method was called with category={} and accessoryName={}", category, accessoryName);
    
    List<Accessories> accessories = pcSalesRepository.fetchAccessories(category, accessoryName);
    
    if(accessories.isEmpty()) {
      String msg = String.format("No accessories found with category = %s and accessoryName = %s", category, accessoryName);
      throw new NoSuchElementException(msg);
    }
    Collections.sort(accessories);
    return accessories;
  }
}

/**
 * 
 */
package com.promineotech.pc.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.customer.Customer;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import com.promineotech.pc.repository.PcOrderRepository;
import lombok.extern.slf4j.Slf4j;

/*
 * Service tells Spring to manage entity services since all entities are targets for Bean Injection.
 * PCSalesService interface.
 */
@Service
@Slf4j
public class DefaultPcOrderService implements PcOrderService {
  @Autowired
  private PcOrderRepository pcOrderRepository;
  
  @Transactional
  @Override
  public Order createOrder(OrderRequest orderRequest) {
   
    Customer customer = getCustomer(orderRequest);

    Cpu cpu = getCpu(orderRequest);
    
    CpuCooler cpuCooler = getCpuCooler(orderRequest);
    
    Motherboard motherboard = getMotherboard(orderRequest);
    
    Memory memory = getMemory(orderRequest);
    
    Storage storage = getStorage(orderRequest);
    
    VideoCard videoCard = getVideoCard(orderRequest);
    
    PcCase pcCase = getPcCase(orderRequest);
    
    PowerSupply powerSupply = getPowerSupply(orderRequest);
    
    List<Accessories> accessories = getAccessories(orderRequest);
    
    BigDecimal price = cpu.getPrice()
        .add(cpuCooler.getPrice())
        .add(motherboard.getPrice())
        .add(memory.getPrice())
        .add(storage.getPrice())
        .add(videoCard.getPrice())
        .add(pcCase.getPrice())
        .add(powerSupply.getPrice());
    
    for(Accessories accessory: accessories) {
     price = price.add(accessory.getPrice());
    }
    
    return pcOrderRepository.saveOrder(customer, cpu, cpuCooler, motherboard, memory, storage, videoCard, pcCase, powerSupply, price,
        accessories);
  }

  private List<Accessories> getAccessories(OrderRequest orderRequest){
    return pcOrderRepository.fetchAccessories(orderRequest.getAccessories());
  }
  
  protected PowerSupply getPowerSupply(OrderRequest orderRequest) {
    return pcOrderRepository.fetchPowerSupply(orderRequest.getPowerSupply())
        .orElseThrow(()-> new NoSuchElementException("Power supply with ID=" + orderRequest.getPowerSupply() + " was not found"));
  }

  protected PcCase getPcCase(OrderRequest orderRequest) {
    return pcOrderRepository.fetchPcCase(orderRequest.getPcCase())
        .orElseThrow(()-> new NoSuchElementException("Case with ID=" + orderRequest.getPcCase() + " was not found"));
  }

  protected VideoCard getVideoCard(OrderRequest orderRequest) {
    return pcOrderRepository.fetchVideoCard(orderRequest.getVideoCard())
        .orElseThrow(()-> new NoSuchElementException("Video card with ID=" + orderRequest.getVideoCard() + " was not found"));
  }

  protected Storage getStorage(OrderRequest orderRequest) {
    return pcOrderRepository.fetchStorage(orderRequest.getStorage())
        .orElseThrow(()-> new NoSuchElementException("Storage with ID=" + orderRequest.getStorage() + " was not found"));
  }

  protected Memory getMemory(OrderRequest orderRequest) {
    return pcOrderRepository.fetchMemory(orderRequest.getMemory())
        .orElseThrow(()-> new NoSuchElementException("Memory with ID=" + orderRequest.getMemory() + " was not found"));
  }

  protected Motherboard getMotherboard(OrderRequest orderRequest) {
    return pcOrderRepository.fetchMotherboard(orderRequest.getMotherboard())
        .orElseThrow(()-> new NoSuchElementException("Motherboard with ID=" + orderRequest.getMotherboard() + " was not found"));
  }

  protected CpuCooler getCpuCooler(OrderRequest orderRequest) {
    return pcOrderRepository.fetchCpuCooler(orderRequest.getCpuCooler())
        .orElseThrow(()-> new NoSuchElementException("Cpu cooler with ID=" + orderRequest.getCpuCooler() + " was not found"));
  }

  protected Cpu getCpu(OrderRequest orderRequest) {
    return pcOrderRepository.fetchCpu(orderRequest.getCpu())
        .orElseThrow(()-> new NoSuchElementException("Cpu with ID=" + orderRequest.getCpu() + " was not found"));
  }

  protected Customer getCustomer(OrderRequest orderRequest) {
    return pcOrderRepository.fetchCustomer(orderRequest.getCustomer())
        .orElseThrow(()-> new NoSuchElementException("Customer with ID=" + orderRequest.getCustomer() + " was not found"));
  }

  @Override
  public List<OrderPk> fetchOrders(Long orderPk) {
    log.info("The fetchOrders method was called with orderPk={}", orderPk);
    
    List<OrderPk> orderPks = pcOrderRepository.fetchOrders(orderPk);
    if(orderPks.isEmpty()) {
      String msg = String.format("No primary keys found with orderPk = %s ", orderPk);
      throw new NoSuchElementException(msg);
    }
    
    return orderPks;
  }

  @Override
  public Optional<OrderPk> updateOrders(Long orderPk, Long cpuPk) {
    log.info("The updateOrders method was called with orderPk={}, cpuPK={}", orderPk, cpuPk);
    return pcOrderRepository.updateOrders(orderPk, cpuPk);
  }

  @Override
  public OrderPk deleteOrders(Long orderPk) {
    // TODO Auto-generated method stub
    return pcOrderRepository.deleteOrders(orderPk);
  }







}

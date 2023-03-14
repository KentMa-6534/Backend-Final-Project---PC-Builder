/**
 * 
 */
package com.promineotech.pc.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.accessories.Category;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.memory.MemoryType;
import com.promineotech.pc.entity.motherboard.CpuSocket;
import com.promineotech.pc.entity.motherboard.FormFactor;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.pc_case.CaseType;
import com.promineotech.pc.entity.pc_case.Color;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.Modular;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import com.promineotech.pc.entity.video_card.VideoCardBrand;
import lombok.extern.slf4j.Slf4j;
/*
 * GET methods for all entities except Order
 */
@Component
@Slf4j
public class DefaultPcSalesRepository implements PcSalesRepository {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public List<Cpu> fetchCpus(CpuBrand cpuBrand, String name) {
    log.debug("Repository: cpuBrand={}, name={}", cpuBrand, name);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM pc_cpu "
        +"WHERE cpu_brand = :cpu_brand AND cpu_name = :cpu_name";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("cpu_brand", cpuBrand.toString());
    params.put("cpu_name", name);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public Cpu mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return Cpu.builder()
                .cpuPK(rs.getLong("cpu_pk"))
                .cpuBrand(CpuBrand.valueOf(rs.getString("cpu_brand")))
                .cpuName(rs.getString("cpu_name"))
                .coreCount(rs.getInt("core_count"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<CpuCooler> fetchCpuCoolers(String coolerName, boolean isWaterCooled) {
    log.debug("Repository: cpuBrand={}, name={}", coolerName, isWaterCooled);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM cpu_cooler "
        +"WHERE cooler_name = :cooler_name AND is_water_cooled = :is_water_cooled";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("cooler_name", coolerName.toString());
    params.put("is_water_cooled", isWaterCooled);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public CpuCooler mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return CpuCooler.builder()
                .cpuCoolerPK(rs.getLong("cpu_cooler_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .coolerName(rs.getString("cooler_name"))
                .isWaterCooled(rs.getBoolean("is_water_cooled"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<Motherboard> fetchMotherboard(String motherboardName, FormFactor formFactor) {
    log.debug("Repository: motherboardName={}, formFactor={}", motherboardName, formFactor);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM motherboard "
        +"WHERE motherboard_name = :motherboard_name AND form_factor = :form_factor";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("motherboard_name", motherboardName);
    params.put("form_factor", formFactor.toString());
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public Motherboard mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return Motherboard.builder()
                .motherboardPK(rs.getLong("motherboard_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .motherboardName(rs.getString("motherboard_name"))
                .cpuSocket(CpuSocket.valueOf(rs.getString("cpu_socket")))
                .formFactor(FormFactor.valueOf(rs.getString("form_factor")))
                .memorySlots(rs.getInt("memory_slots"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<Memory> fetchMemory(String memoryName, MemoryType memoryType) {
    log.debug("Repository: memoryName={}, memoryType={}", memoryName, memoryType);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM pc_memory "
        +"WHERE memory_name = :memory_name AND memory_type = :memory_type";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("memory_name", memoryName);
    params.put("memory_type", memoryType.toString());
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public Memory mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return Memory.builder()
                .memoryPK(rs.getLong("memory_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .memoryName(rs.getString("memory_name"))
                .memoryType(MemoryType.valueOf(rs.getString("memory_type")))
                .memorySpeed(rs.getString("memory_speed"))
                .memorySize(rs.getString("memory_size"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<Storage> fetchStorage(String storageName, String storageType) {
    log.debug("Repository: storageName={}, storageType={}", storageName, storageType);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM pc_storage "
        +"WHERE storage_name = :storage_name AND storage_type = :storage_type";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("storage_name", storageName);
    params.put("storage_type", storageType);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return Storage.builder()
                .storagePK(rs.getLong("storage_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .storageName(rs.getString("storage_name"))
                .storageCapacity(rs.getString("storage_capacity"))
                .storageType(rs.getString("storage_type"))
                .storageCache(rs.getBoolean("storage_cache"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<VideoCard> fetchVideoCards(VideoCardBrand videoCardBrand, String videoCardName) {
    log.debug("Repository: videoCardBrand={}, videoCardName={}", videoCardBrand, videoCardName);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM video_card "
        +"WHERE video_card_brand = :video_card_brand AND video_card_name = :video_card_name";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("video_card_brand", videoCardBrand.toString());
    params.put("video_card_name", videoCardName);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public VideoCard mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return VideoCard.builder()
                .videoCardPK(rs.getLong("video_card_pk"))
                .videoCardBrand(VideoCardBrand.valueOf(rs.getString("video_card_brand")))
                .videoCardName(rs.getString("video_card_name"))
                .videoCardMemory(rs.getString("video_card_memory"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<PcCase> fetchCases(String caseName, CaseType caseType) {
    log.debug("Repository: caseName={}, caseType={}", caseName, caseType);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM pc_case "
        +"WHERE case_name = :case_name AND case_type = :case_type";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("case_name", caseName);
    params.put("case_type", caseType.toString());
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public PcCase mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return PcCase.builder()
                .casePK(rs.getLong("case_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .caseName(rs.getString("case_name"))
                .caseType(CaseType.valueOf(rs.getString("case_type")))
                .color(Color.valueOf(rs.getString("color")))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<PowerSupply> fetchPsu(String powerSupplyName, Modular modular) {
    log.debug("Repository: powerSupplyName={}, modular={}", powerSupplyName, modular);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM power_supply "
        +"WHERE power_supply_name = :power_supply_name AND modular = :modular";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("power_supply_name", powerSupplyName);
    params.put("modular", modular.toString());
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public PowerSupply mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return PowerSupply.builder()
                .powerSupplyPK(rs.getLong("power_supply_pk"))
                .manufacturer(rs.getString("manufacturer"))
                .powerSupplyName(rs.getString("power_supply_name"))
                .wattage(rs.getInt("wattage"))
                .modular(Modular.valueOf(rs.getString("modular")))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

  @Override
  public List<Accessories> fetchAccessories(Category category, String accessoryName) {
    log.debug("Repository: category={}, accessoryName={}", category, accessoryName);
    
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM accessories "
        +"WHERE category = :category AND accessory_name = :accessory_name";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("category", category.toString());
    params.put("accessory_name", accessoryName);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public Accessories mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return Accessories.builder()
                .accessoryPK(rs.getLong("accessory_pk"))
                .category(Category.valueOf(rs.getString("category")))
                .manufacturer(rs.getString("manufacturer"))
                .accessoryName(rs.getString("accessory_name"))
                .price(new BigDecimal(rs.getString("price")))
                .build();
            //@formatter:on
          }
    });
  }

}

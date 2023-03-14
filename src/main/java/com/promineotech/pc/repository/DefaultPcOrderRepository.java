/**
 * 
 */
package com.promineotech.pc.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.accessories.Category;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.customer.Customer;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.memory.MemoryType;
import com.promineotech.pc.entity.motherboard.CpuSocket;
import com.promineotech.pc.entity.motherboard.FormFactor;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;
import com.promineotech.pc.entity.pc_case.CaseType;
import com.promineotech.pc.entity.pc_case.Color;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.Modular;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import com.promineotech.pc.entity.video_card.VideoCardBrand;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent Ma
 *
 */
@Component
@Slf4j
public class DefaultPcOrderRepository implements PcOrderRepository {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate; 
  
  //Post method to put all entities in the Orders table in database
  @Override
  public Order saveOrder(Customer customer, Cpu cpu, CpuCooler cpuCooler, Motherboard motherboard,
      Memory memory, Storage storage, VideoCard videoCard, PcCase pcCase, PowerSupply powerSupply,
      BigDecimal price, List<Accessories> accessories) {
    SqlParams params = generateInsertSql(customer, cpu, cpuCooler, motherboard, memory, storage, videoCard, pcCase, powerSupply, price);
    
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
    
    Long orderPK = keyHolder.getKey().longValue();
    saveAccessories(accessories, orderPK);
    
    //@formatter:off
    return Order.builder()
        .orderPK(orderPK)
        .customer(customer)
        .cpu(cpu)
        .cpuCooler(cpuCooler)
        .motherboard(motherboard)
        .memory(memory)
        .storage(storage)
        .videoCard(videoCard)
        .pcCase(pcCase)
        .powerSupply(powerSupply)
        .accessories(accessories)
        .price(price)
        .build();
    //@formatter:on
  }
  /**
   * @param accessories
   * @param orderPK
   * Post method to handle Optional Accessories.
   */
  private void saveAccessories(List<Accessories> accessories, Long orderPK) {
    for(Accessories accessory : accessories) {
      SqlParams params = generateInsertSql(accessory, orderPK);
      jdbcTemplate.update(params.sql, params.source);
    }
    
  }
  /**
   * @param accessory
   * @param orderPK
   * @return
   */
  private SqlParams generateInsertSql(Accessories accessory, Long orderPK) {
    SqlParams params = new SqlParams();
    
    // @formatter:off
    params.sql = ""
        + "INSERT INTO order_accessories ("
        + "accessory_fk, order_fk"
        + ") VALUES ("
        + ":accessory_fk, :order_fk"
        + ")";
    // @formatter:on
    
    params.source.addValue("accessory_fk", accessory.getAccessoryPk());
    params.source.addValue("order_fk", orderPK);
    
    return params;
  }
  /**
   * @param customer
   * @param cpu
   * @param cpuCooler
   * @param motherboard
   * @param memory
   * @param storage
   * @param videoCard
   * @param pcCase
   * @param powerSupply
   * @param price
   * @return
   */
  private SqlParams generateInsertSql(Customer customer, Cpu cpu, CpuCooler cpuCooler,
      Motherboard motherboard, Memory memory, Storage storage, VideoCard videoCard, PcCase pcCase,
      PowerSupply powerSupply, BigDecimal price) {
    //@formatter:off
    String sql = ""
        + "INSERT INTO orders ("
        + "customer_fk, cpu_fk, cpu_cooler_fk, motherboard_fk, memory_fk, storage_fk, video_card_fk, case_fk, power_supply_fk, price"
        + ") VALUES ("
        + ":customer_fk, :cpu_fk, :cpu_cooler_fk, :motherboard_fk, :memory_fk, :storage_fk, :video_card_fk, :case_fk, :power_supply_fk, :price"
        + ")";
    //@formatter:on
    
    SqlParams params = new SqlParams();
    params.sql = sql;
    params.source.addValue("customer_fk", customer.getCustomerPk());
    params.source.addValue("cpu_fk", cpu.getCpuPk());
    params.source.addValue("cpu_cooler_fk", cpuCooler.getCpuCoolerPk());
    params.source.addValue("motherboard_fk", motherboard.getMotherboardPk());
    params.source.addValue("memory_fk", memory.getMemoryPK());
    params.source.addValue("storage_fk", storage.getStoragePk());
    params.source.addValue("video_card_fk", videoCard.getvideoCardPk());
    params.source.addValue("case_fk", pcCase.getCasePk());
    params.source.addValue("power_supply_fk", powerSupply.getPowerSupplyPk());
    params.source.addValue("price", price);
    
    
    return params;
  }
  //Inserts grabs entity information from different tables and puts them in new order.
  @Override
  public List<Accessories> fetchAccessories(List<String> accessoryId) {
    if(accessoryId.isEmpty()) {
      return new LinkedList<>();
    }
    
    Map<String, Object> params = new HashMap<>();
    
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM accessories "
        + "WHERE accessory_id IN(";
    // @formatter:on
    
    for(int index = 0; index<accessoryId.size(); index++) {
       String key = "accessory_" + index;
       sql+= ":" + key + ", ";
       params.put(key, accessoryId.get(index));
    }
    
    sql = sql.substring(0, sql.length() - 2);
    sql  += ")";
    
    return jdbcTemplate.query(sql, params, new RowMapper<Accessories>() {

      @Override
      public Accessories mapRow(ResultSet rs, int rowNum) throws SQLException {
        // @formatter:off
        return Accessories.builder()
            .category(Category.valueOf(rs.getString("category")))
            .manufacturer(rs.getString("manufacturer"))
            .accessoryName(rs.getString("accessory_name"))
            .accessoryId(rs.getString("accessory_id"))
            .accessoryPK(rs.getLong("accessory_pk"))
            .price(rs.getBigDecimal("price"))
            .build();
        // @formatter:on
      }});
  }
  @Override
  public Optional<Customer> fetchCustomer(String customerId) {
    String sql = ""
        + "SELECT * "
        + "FROM customer "
        + "WHERE customer_id = :customer_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("customer_id", customerId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new CustomerResultSetExtractor()));
  }
  
  class CustomerResultSetExtractor implements ResultSetExtractor<Customer>{
    @Override
    public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return Customer.builder()
          .customerId(rs.getString("customer_id"))
          .customerPk(rs.getLong("customer_pk"))
          .customerFirstName(rs.getString("first_name"))
          .customerLastName(rs.getString("last_name"))
          .email(rs.getString("email"))
          .build();
      //@formatter:on
  }


}

  @Override
  public Optional<Cpu> fetchCpu(String cpuId) {
    String sql = ""
        + "SELECT * "
        + "FROM pc_cpu "
        + "WHERE cpu_id = :cpu_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("cpu_id", cpuId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new CpuResultSetExtractor()));
  }
  
  class CpuResultSetExtractor implements ResultSetExtractor<Cpu>{
    @Override
    public Cpu extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return Cpu.builder()
          .cpuId(rs.getString("cpu_id"))
          .cpuPK(rs.getLong("cpu_pk"))
          .cpuBrand(CpuBrand.valueOf(rs.getString("cpu_brand")))
          .cpuName(rs.getString("cpu_name"))
          .coreCount(rs.getInt("core_count"))
          .price(new BigDecimal(rs.getString("price")))
          .build();
      //@formatter:on
  }
}

  @Override
  public Optional<CpuCooler> fetchCpuCooler(String cpuCoolerId) {
    String sql = ""
        + "SELECT * "
        + "FROM cpu_cooler "
        + "WHERE cpu_cooler_id = :cpu_cooler_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("cpu_cooler_id", cpuCoolerId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new CpuCoolerResultSetExtractor()));
  }
  
  class CpuCoolerResultSetExtractor implements ResultSetExtractor<CpuCooler>{
    @Override
    public CpuCooler extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return CpuCooler.builder()
          .cpuCoolerId(rs.getString("cpu_cooler_id"))
          .cpuCoolerPK(rs.getLong("cpu_cooler_pk"))
          .manufacturer(rs.getString("manufacturer"))
          .coolerName(rs.getString("cooler_name"))
          .isWaterCooled(rs.getBoolean("is_water_cooled"))
          .price(new BigDecimal(rs.getString("price")))
          .build();
      //@formatter:on
  }
}

  @Override
  public Optional<Motherboard> fetchMotherboard(String motherboardId) {
    String sql = ""
        + "SELECT * "
        + "FROM motherboard "
        + "WHERE motherboard_id = :motherboard_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("motherboard_id", motherboardId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new MotherboardResultSetExtractor()));
  }
  class MotherboardResultSetExtractor implements ResultSetExtractor<Motherboard>{
    @Override
    public Motherboard extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return Motherboard.builder()
          .motherboardId(rs.getString("motherboard_id"))
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
}
  
  @Override
  public Optional<Memory> fetchMemory(String memoryId) {
    String sql = ""
        + "SELECT * "
        + "FROM pc_memory "
        + "WHERE memory_id = :memory_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("memory_id", memoryId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new MemoryResultSetExtractor()));
  }
  class MemoryResultSetExtractor implements ResultSetExtractor<Memory>{
    @Override
    public Memory extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return Memory.builder()
          .memoryId(rs.getString("memory_id"))
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
}

  @Override
  public Optional<Storage> fetchStorage(String storageId) {
    String sql = ""
        + "SELECT * "
        + "FROM pc_storage "
        + "WHERE storage_id = :storage_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("storage_id", storageId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new StorageResultSetExtractor()));
  }
  class StorageResultSetExtractor implements ResultSetExtractor<Storage>{
    @Override
    public Storage extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return Storage.builder()
          .storageId(rs.getString("storage_id"))
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
}

  @Override
  public Optional<VideoCard> fetchVideoCard(String videoCardId) {
        String sql = ""
        + "SELECT * "
        + "FROM video_card "
        + "WHERE video_card_id = :video_card_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("video_card_id", videoCardId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new VideoCardResultSetExtractor()));
  }
  class VideoCardResultSetExtractor implements ResultSetExtractor<VideoCard>{
    @Override
    public VideoCard extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return VideoCard.builder()
          .videoCardId(rs.getString("video_card_id"))
          .videoCardPK(rs.getLong("video_card_pk"))
          .videoCardBrand(VideoCardBrand.valueOf(rs.getString("video_card_brand")))
          .videoCardName(rs.getString("video_card_name"))
          .videoCardMemory(rs.getString("video_card_memory"))
          .price(new BigDecimal(rs.getString("price")))
          .build();
      //@formatter:on
  }
}

  @Override
  public Optional<PcCase> fetchPcCase(String caseId) {
    String sql = ""
    + "SELECT * "
    + "FROM pc_case "
    + "WHERE case_id = :case_id";

    Map<String, Object> params = new HashMap<>();
    params.put("case_id", caseId);

    return Optional.ofNullable(jdbcTemplate.query(sql, params, new CaseResultSetExtractor()));
  }
  class CaseResultSetExtractor implements ResultSetExtractor<PcCase>{
    @Override
    public PcCase extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return PcCase.builder()
          .caseId(rs.getString("case_id"))
          .casePK(rs.getLong("case_pk"))
          .manufacturer(rs.getString("manufacturer"))
          .caseName(rs.getString("case_name"))
          .caseType(CaseType.valueOf(rs.getString("case_type")))
          .color(Color.valueOf(rs.getString("color")))
          .price(new BigDecimal(rs.getString("price")))
          .build();
      //@formatter:on
  }
}

  @Override
  public Optional<PowerSupply> fetchPowerSupply(String powerSupplyId) {
    String sql = ""
    + "SELECT * "
    + "FROM power_supply "
    + "WHERE power_supply_id = :power_supply_id";

    Map<String, Object> params = new HashMap<>();
    params.put("power_supply_id", powerSupplyId);

    return Optional.ofNullable(jdbcTemplate.query(sql, params, new PowerSupplyResultSetExtractor()));
  }
  class PowerSupplyResultSetExtractor implements ResultSetExtractor<PowerSupply>{
    @Override
    public PowerSupply extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      return PowerSupply.builder()
          .powerSupplyId(rs.getString("power_supply_id"))
          .powerSupplyPK(rs.getLong("power_supply_pk"))
          .manufacturer(rs.getString("manufacturer"))
          .powerSupplyName(rs.getString("power_supply_name"))
          .wattage(rs.getInt("wattage"))
          .modular(Modular.valueOf(rs.getString("modular")))
          .price(new BigDecimal(rs.getString("price")))
          .build();
      //@formatter:on
  }
}
// Get method to read orders in database based off of primary key and returns primary keys associated with order.
  @Override
  public List<OrderPk> fetchOrders(Long orderPk) {
    //@formatter:off
    String sql = ""
        +"SELECT * "
        +"FROM orders "
        +"WHERE order_pk = :order_pk";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("order_pk", orderPk);
    
    return jdbcTemplate.query(sql, params,
        new RowMapper<>() {
          @Override
          public OrderPk mapRow(ResultSet rs, int rowNum) throws SQLException {
            //@formatter:off
            return OrderPk.builder()
                .orderPk(rs.getLong("order_pk"))
                .customerPk(rs.getLong("customer_fk"))
                .cpuPk(rs.getLong("cpu_fk"))
                .cpuCoolerPk(rs.getLong("cpu_cooler_fk"))
                .motherboardPk(rs.getLong("motherboard_fk"))
                .memoryPk(rs.getLong("memory_fk"))
                .storagePk(rs.getLong("storage_fk"))
                .videoCardPk(rs.getLong("video_card_fk"))
                .pcCasePk(rs.getLong("case_fk"))
                .powerSupplyPk(rs.getLong("power_supply_fk"))
                .price(rs.getLong("price"))
                .build();
            //@formatter:on
          }
    });
  }
/*Update method to update orders in database based off of certain primary keys and returns order's updated primary keys.
 * Currently I am only able to update the CPU primary key but hope to update more in the future.
*/
  @Override
  public Optional<OrderPk> updateOrders(Long orderPk, Long cpuPk) {
    log.info("Repository: orderPk={}, cpuPK={}", orderPk, cpuPk);
    //@formatter:off
    String sql = ""
        + "UPDATE orders SET cpu_fk = :cpu_fk "
        + "WHERE order_pk = :order_pk";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();    
    params.put("order_pk", orderPk);
    params.put("cpu_fk", cpuPk);
    
    jdbcTemplate.update(sql, params);
    return Optional.ofNullable(OrderPk.builder().cpuPk(cpuPk).build());
  }
  /*
   * Deletes orders from database based off of order primary key.
   */
  @Override
  public OrderPk deleteOrders(Long orderPk) {
    //@formatter:off
    String sql = ""
        + "DELETE FROM orders WHERE "
        + "order_pk = :order_pk";
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("order_pk", orderPk);
    
    jdbcTemplate.update(sql, params);
    return OrderPk.builder().orderPk(orderPk).build();
  }
  
}

class SqlParams{
  String sql;
  MapSqlParameterSource source = new MapSqlParameterSource();
}


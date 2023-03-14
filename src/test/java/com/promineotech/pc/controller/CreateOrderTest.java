/**
 * 
 */
package com.promineotech.pc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.pc.controller.support.CreateOrderTestSupport;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/computer_schema.sql",
    "classpath:flyway/migrations/computer_data.sql"},
    config = @SqlConfig(encoding = "utf-8"))
class CreateOrderTest extends CreateOrderTestSupport{
  
  
  @Test
  void testCreateOrderReturnsSuccess201() {
   //Given: An order as JSON
   String body =  createOrderBody();
   String uri = getBaseUriForOrders();


   HttpHeaders headers = new HttpHeaders();
   headers.setContentType(MediaType.APPLICATION_JSON);
   
   HttpEntity<String> bodyEntity= new HttpEntity<>(body, headers);
   
   System.out.println(bodyEntity);
   //When: The order is sent
   ResponseEntity<Order> response = getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
   //Then: a 201 status is returned
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
   
   //And: the returned order is correct
   assertThat(response.getBody()).isNotNull();
   
   Order order = response.getBody();
   assertThat(order.getCustomer().getCustomerId()).isEqualTo("MA_KENT");
   assertThat(order.getCpu().getCpuId()).isEqualTo("CORE_I7_13700K");
   assertThat(order.getCpuCooler().getCpuCoolerId()).isEqualTo("KRAKEN_Z73_RGB");
   assertThat(order.getMotherboard().getMotherboardId()).isEqualTo("MPG_Z790_EDGE_WIFI");
   assertThat(order.getMemory().getMemoryId()).isEqualTo("TRIDENT_Z5_RGB");
   assertThat(order.getStorage().getStorageId()).isEqualTo("970_EVO_PLUS");
   assertThat(order.getVideoCard().getVideoCardId()).isEqualTo("ASUS_TUF_GAMING_OC_RTX_4090");
   assertThat(order.getPcCase().getCaseId()).isEqualTo("O11D_XL_X");
   assertThat(order.getPowerSupply().getPowerSupplyId()).isEqualTo("RM850X_2021");
   assertThat(order.getAccessories()).hasSize(1);
  }
  
}

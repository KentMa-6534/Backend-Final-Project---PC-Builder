/**
 * 
 */
package com.promineotech.pc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doThrow;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import com.promineotech.pc.Constants;
import com.promineotech.pc.controller.support.GetCpuTestSupport;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import com.promineotech.pc.service.PcSalesService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/computer_schema.sql",
    "classpath:flyway/migrations/computer_data.sql"},
    config = @SqlConfig(encoding = "utf-8"))
class GetCpuTest extends GetCpuTestSupport {
  
  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/computer_schema.sql",
      "classpath:flyway/migrations/computer_data.sql"},
      config = @SqlConfig(encoding = "utf-8"))
  class TestsThatDoNotPolluteTheApplicationContext extends GetCpuTestSupport{
    @Test
    void testThatDatabaseConnectionWorks() {
      int numrows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "customer");
      System.out.println("num = " + numrows);
    }
   
    @Test
    void testThatCpuIsReturnedWhenAValidBrandAndNameAreSupplied() {
      //Given: a valid brand, name, and URI
      CpuBrand cpuBrand = CpuBrand.INTEL;
      String name = "Core i7-13700K";
      String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
      
      //When: a connection is made to the URI
      ResponseEntity<List<Cpu>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
      
      //Then: a success (OK - 200) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      
      //And: the actual list returned is the same as the expected list
      List<Cpu> expected = buildExpected();
      assertThat(response.getBody()).isEqualTo(expected);

    }
    
    @Test
    void testThatErrorMessageIsReturnedWhenAnUnknownNameIsSupplied() {
      //Given: an unknown name is supplied
      CpuBrand cpuBrand = CpuBrand.INTEL;
      String name = "Invalid value";
      String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
      
      //When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
      
      //Then: a not found (404) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      
      //And: an error message is returned
      Map<String, Object> error = response.getBody();
      
      AssertErrorMessageValid(error, HttpStatus.NOT_FOUND);
    }
    
    @ParameterizedTest
    @MethodSource("com.promineotech.pc.controller.GetCpuTest#parametersForInvalidInput")
    void testThatErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(String cpuBrand, String name, String reason) {
      //Given: an unknown name is supplied
      String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
      
      //When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
      
      //Then: a bad request (400) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      
      //And: an error message is returned
      Map<String, Object> error = response.getBody();
      
      AssertErrorMessageValid(error, HttpStatus.BAD_REQUEST);  
    }
    static Stream<Arguments> parametersForInvalidInput(){
      // @formatter:off
         return Stream.of(
             arguments("INTEL", "@#%@^&$%", "Name contains non-alpha-numeric characters"),
             arguments("INTEL", "C".repeat(Constants.NAME_MAX_LENGTH + 1), "Name length too long")
         );
      // @formatter:on
       }
    
  }
  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/computer_schema.sql",
      "classpath:flyway/migrations/computer_data.sql"},
      config = @SqlConfig(encoding = "utf-8"))
  class TestsThatPolluteTheApplicationContext extends GetCpuTestSupport{
    @MockBean
    private PcSalesService pcSalesService;
    
    @Test
    void testThatAnUnplannedErrorResultsInA500Status() {
      //Given: an unknown name is supplied
      CpuBrand cpuBrand = CpuBrand.INTEL;
      String name = "Core i7-13700K";
      String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
      
      doThrow(new RuntimeException("Ouch!")).when(pcSalesService).fetchCpus(cpuBrand, name);
      
      //When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
      
      //Then: an internal server error (500) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
      
      //And: an error message is returned
      Map<String, Object> error = response.getBody();
      
      AssertErrorMessageValid(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
  }
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Test
  void testThatDatabaseConnectionWorks() {
    int numrows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "customer");
    System.out.println("num = " + numrows);
  }
 
  @Test
  void testThatCpuIsReturnedWhenAValidBrandAndNameAreSupplied() {
    //Given: a valid brand, name, and URI
    CpuBrand cpuBrand = CpuBrand.INTEL;
    String name = "Core i7-13700K";
    String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
    
    //When: a connection is made to the URI
    ResponseEntity<List<Cpu>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
    
    //Then: a success (OK - 200) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    
    //And: the actual list returned is the same as the expected list
    List<Cpu> expected = buildExpected();
    assertThat(response.getBody()).isEqualTo(expected);

  }
  
  @Test
  void testThatErrorMessageIsReturnedWhenAnUnknownNameIsSupplied() {
    //Given: an unknown name is supplied
    CpuBrand cpuBrand = CpuBrand.INTEL;
    String name = "Invalid value";
    String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
    
    //When: a connection is made to the URI
    ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
    
    //Then: a not found (404) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    
    //And: an error message is returned
    Map<String, Object> error = response.getBody();
    
    AssertErrorMessageValid(error, HttpStatus.NOT_FOUND);
  }
  
  @ParameterizedTest
  @MethodSource("com.promineotech.pc.controller.GetCpuTest#parametersForInvalidInput")
  void testThatErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(String cpuBrand, String name, String reason) {
    //Given: an unknown name is supplied
    String uri = String.format("%s?cpuBrand=%s&name=%s",getBaseUriForCpu(), cpuBrand, name);
    
    //When: a connection is made to the URI
    ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
    
    //Then: a bad request (400) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    
    //And: an error message is returned
    Map<String, Object> error = response.getBody();
    
    AssertErrorMessageValid(error, HttpStatus.BAD_REQUEST);  
  }
  static Stream<Arguments> parametersForInvalidInput(){
    // @formatter:off
       return Stream.of(
           arguments("INTEL", "@#%@^&$%", "Name contains non-alpha-numeric characters"),
           arguments("INTEL", "C".repeat(Constants.NAME_MAX_LENGTH + 1), "Name length too long")
       );
    // @formatter:on
     }


}

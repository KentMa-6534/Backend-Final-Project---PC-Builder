/**
 * 
 */
package com.promineotech.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotech.ComponentScanMarker;
/*
 * Starts Spring Boot Application
 */
@SpringBootApplication(scanBasePackageClasses = {ComponentScanMarker.class})
public class PcSales {

  public static void main(String[] args) {
    SpringApplication.run(PcSales.class, args);

  }

}

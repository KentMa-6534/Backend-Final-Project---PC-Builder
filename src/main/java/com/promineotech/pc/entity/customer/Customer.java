/**
 * 
 */
package com.promineotech.pc.entity.customer;

import lombok.Data;

@Data
public class Customer {
  private Long customerPK;
  private String customerId;
  private String customerFirstName;
  private String customerLastName;
  private String email;
}

/**
 * 
 */
package com.promineotech.pc.entity.customer;

import lombok.Builder;
import lombok.Data;

//The customer class contains contact information for each customer.
@Data
@Builder
public class Customer {
  private Long customerPk;
  private String customerId;
  private String customerFirstName;
  private String customerLastName;
  private String email;
}

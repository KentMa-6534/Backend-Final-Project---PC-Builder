/**
 * 
 */
package com.promineotech.pc.entity.order;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class OrderRequest {
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String customer;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String cpu;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String cpuCooler;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String motherboard;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String memory;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String storage;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String videoCard;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String pcCase;
  
@NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String powerSupply;
  
  private List<@NotNull @Length(max = 40) @Pattern(regexp = "[\\w\\s]*")String> accessories;
}

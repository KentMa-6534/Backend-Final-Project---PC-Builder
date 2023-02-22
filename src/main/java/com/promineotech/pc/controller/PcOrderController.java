/**
 * 
 */
package com.promineotech.pc.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.pc.Constants;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/pc-builder/orders")
@OpenAPIDefinition(info = @Info(title = "PC Order Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface PcOrderController {

  @Operation(
      summary = "Creates PC Order",
      description = "Returns created PC Order",
      responses = {
          @ApiResponse(responseCode = "201",
              description = "The created PC is returned",
              content =  @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Order.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No CPUs found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "orderRequest",  
              required = true, 
              description = "The order as JSON"),
      }
      )
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Order createOrder(@Valid @RequestBody OrderRequest orderRequest);
}

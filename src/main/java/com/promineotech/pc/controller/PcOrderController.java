/**
 * 
 */
package com.promineotech.pc.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.customer.Customer;
import com.promineotech.pc.entity.order.Order;
import com.promineotech.pc.entity.order.OrderPk;
import com.promineotech.pc.entity.order.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/pc-builder/orders")
@OpenAPIDefinition(info = @Info(title = "PC Order Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface PcOrderController {
  @Operation(
      summary = "Returns PC Order Primary Keys",
      description = "Returns a list of PC Order primary keys given a valid Order PK",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A PC order is returned",
              content =  @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Order.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No orders found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      }
      )
  //Get Method for Order(Read)
  @GetMapping()
  @ResponseStatus(code = HttpStatus.OK)
  List<OrderPk> fetchOrders(@RequestParam(required = false) Long orderPk);
  
  @Operation(
      summary = "Creates PC Order",
      description = "Returns created PC Order",
      responses = {
          @ApiResponse(responseCode = "201",
              description = "The created PC is returned",
              content =  @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderPk.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No orders found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      }
      )
  //Post Method (Create)
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Order createOrder(@Valid @RequestBody OrderRequest orderRequest);
  
  @Operation(
      summary = "Updates PC Order",
      description = "Returns updated PC Order",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "The PC order is updated",
              content =  @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderPk.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "Order not found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "orderPk",
              allowEmptyValue = false,
              required = true,
              description = "The primary key of the order (i.e, '1')"),
          @Parameter(
              name = "cpuPk",
              allowEmptyValue = false,
              required = false,
              description = "The CPU primary key for the order (i.e, '1')"),
      }
      )
  // Put Method (Update)
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  Optional<OrderPk> updateOrder(
      @RequestParam(required = false)
      Long orderPk,
      @RequestParam(required = false)
      Long cpuPk
      );
  
  @Operation(
      summary = "Deletes PC Order",
      description = "Deletes PC Order",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "The PC order is deleted",
              content =  @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderPk.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "Order not found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      }
      )
  // Delete Method (Delete)
  @DeleteMapping
  @ResponseStatus(code = HttpStatus.OK)
  OrderPk deleteOrder(@Valid @RequestParam(required = true) Long orderPk);
}

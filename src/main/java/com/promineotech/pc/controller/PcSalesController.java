/**
 * 
 */
package com.promineotech.pc.controller;

import java.util.List;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.pc.Constants;
import com.promineotech.pc.entity.accessories.Accessories;
import com.promineotech.pc.entity.accessories.Category;
import com.promineotech.pc.entity.cpu.Cpu;
import com.promineotech.pc.entity.cpu.CpuBrand;
import com.promineotech.pc.entity.cpu_cooler.CpuCooler;
import com.promineotech.pc.entity.memory.Memory;
import com.promineotech.pc.entity.memory.MemoryType;
import com.promineotech.pc.entity.motherboard.FormFactor;
import com.promineotech.pc.entity.motherboard.Motherboard;
import com.promineotech.pc.entity.pc_case.CaseType;
import com.promineotech.pc.entity.pc_case.PcCase;
import com.promineotech.pc.entity.power_supply.Modular;
import com.promineotech.pc.entity.power_supply.PowerSupply;
import com.promineotech.pc.entity.storage.Storage;
import com.promineotech.pc.entity.video_card.VideoCard;
import com.promineotech.pc.entity.video_card.VideoCardBrand;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
/*
 * This controller contains GET operations for all entities in the application except Order.
 */
@Validated
@RequestMapping("/pc-builder/search")
@OpenAPIDefinition(info = @Info(title = "CPU Sales Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface PcSalesController {
  //@formatter:off
  @Operation(
      summary = "Returns a list of CPUs",
      description = "Returns a list of CPUs given an optional brand or name",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of CPUs is returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = Cpu.class))),
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
              name = "cpuBrand", 
              allowEmptyValue = false, 
              required = false, 
              description = "The brand of the CPU (i.e., 'INTEL')"),
          @Parameter(
              name = "name", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the CPU (i.e., 'Core i7-13700K')")
      }
      )
  @GetMapping("cpu")
  @ResponseStatus(code = HttpStatus.OK)
  List<Cpu> fetchCpus(
      @RequestParam(required = false) 
        CpuBrand cpuBrand,
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false)
        String name);
  //@formatter:on
  //@formatter:off
  @Operation(
      summary = "Returns a list of CPU coolers",
      description = "Returns a list of CPU coolers given an optional brand or name",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of CPU coolers is returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = CpuCooler.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No CPU coolers found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "coolerName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the CPU cooler (i.e., 'Kraken Z73 RGB')"),
          @Parameter(
              name = "isWaterCooled", 
              allowEmptyValue = false, 
              required = false, 
              description = "Determines if CPU cooler is water cooled or not (i.e., 'true')")
      }
      )
  @GetMapping("cpu_cooler")
  @ResponseStatus(code = HttpStatus.OK)
  List<CpuCooler> fetchCpuCoolers(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false) 
        String coolerName ,
      @RequestParam(required = false)
        boolean isWaterCooled);
//@formatter:on
  //@formatter:off
  @Operation(
      summary = "Returns a list of motherboards",
      description = "Returns a list of motherboards given an optional motherboard name or cpu socket",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of motherboards are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = Motherboard.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No motherboards found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "motherboardName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the motherboard (i.e., 'MPG Z790 EDGE WIFI')"),
          @Parameter(
              name = "formFactor", 
              allowEmptyValue = false, 
              required = false, 
              description = "The form factor of the motherboard (i.e., 'STANDARD_ATX')")
      }
      )
  @GetMapping("motherboard")
  @ResponseStatus(code = HttpStatus.OK)
  List<Motherboard> fetchMotherboard(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false) 
        String motherboardName ,
      @RequestParam(required = false)
        FormFactor formFactor);
//@formatter:on
  //@formatter:off
  @Operation(
      summary = "Returns a list of RAM",
      description = "Returns a list of RAM given an optional memory name or memory type",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of RAM are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = Memory.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No RAM found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "memoryName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the RAM (i.e., 'Trident Z5 RGB')"),
          @Parameter(
              name = "memoryType", 
              allowEmptyValue = false, 
              required = false, 
              description = "The type of RAM (i.e., 'DDR5')")
      }
      )
  @GetMapping("memory")
  @ResponseStatus(code = HttpStatus.OK)
  List<Memory> fetchMemory(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false) 
        String memoryName ,
      @RequestParam(required = false)
        MemoryType memoryType);
//@formatter:on
  //@formatter:off
  @Operation(
      summary = "Returns a list of storage",
      description = "Returns a list of storage given an optional storage name or storage type",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of storage devices are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = Storage.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No storage found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "storageName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the storage (i.e., '970 Evo Plus')"),
          @Parameter(
              name = "storageType", 
              allowEmptyValue = false, 
              required = false, 
              description = "The type of storage (i.e., 'NVMe')")
      }
      )
  @GetMapping("storage")
  @ResponseStatus(code = HttpStatus.OK)
  List<Storage> fetchStorage(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false) 
        String storageName ,
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false)
        String storageType);
//@formatter:on
  //@formatter:off
  @Operation(
      summary = "Returns a list of video cards",
      description = "Returns a list of video cards given an optional brand name or video card name",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of video cards are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = VideoCard.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No video cards found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "videoCardBrand", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the video card brand (i.e., 'NVIDIA')"),
          @Parameter(
              name = "videoCardName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the video card (i.e., 'ASUS TUF GAMING OC RTX 4090')")
      }
      )
  @GetMapping("video_card")
  @ResponseStatus(code = HttpStatus.OK)
  List<VideoCard> fetchVideoCards(
      @RequestParam(required = false) 
        VideoCardBrand videoCardBrand ,
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false)
        String videoCardName);
  @Operation(
      summary = "Returns a list of cases",
      description = "Returns a list of video cards given an optional case name or case type",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of cases are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = PcCase.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No cases found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "caseName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the case (i.e., 'O11D XL-X')"),
          @Parameter(
              name = "caseType", 
              allowEmptyValue = false, 
              required = false, 
              description = "The case type (i.e., 'FULL_TOWER')")
      }
      )
  @GetMapping("case")
  @ResponseStatus(code = HttpStatus.OK)
  List<PcCase> fetchCases(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-]*")
      @RequestParam(required = false) 
        String caseName ,
      @RequestParam(required = false)
        CaseType caseType);
  @Operation(
      summary = "Returns a list of PSUs",
      description = "Returns a list of PSUs given an optional power supply name or modularity",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of PSUs are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = PowerSupply.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No PSUs found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "powerSupplyName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the PSU (i.e., 'RM850x (2021)')"),
          @Parameter(
              name = "modular", 
              allowEmptyValue = false, 
              required = false, 
              description = "The modularity of the PSU (i.e., 'FULLY_MODULAR')")
      }
      )
  @GetMapping("power_supply")
  @ResponseStatus(code = HttpStatus.OK)
  List<PowerSupply> fetchPsu(
      @Length(max = Constants.NAME_MAX_LENGTH)
      @Pattern(regexp = "[\\w\\s-()]*")
      @RequestParam(required = false) 
        String powerSupplyName ,
      @RequestParam(required = false)
        Modular modular);
  @Operation(
      summary = "Returns a list of accessories",
      description = "Returns a list of accessories given an optional category or accessory name",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "A list of accessories are returned",
              content =  @Content(mediaType = "application/json",
              schema = @Schema(implementation = Accessories.class))),
          @ApiResponse(responseCode = "400", 
              description = "The request parameters are invalid",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
              description = "No accessories found with the input criteria",  
              content =  @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
              description = "An unplanned error ocurred",  
              content =  @Content(mediaType = "application/json")),
      },
      parameters = {
          @Parameter(
              name = "category", 
              allowEmptyValue = false, 
              required = false, 
              description = "The category of the accessory (i.e., 'MONITOR')"),
          @Parameter(
              name = "accessoryName", 
              allowEmptyValue = false, 
              required = false, 
              description = "The name of the accessory (i.e., 'TUF Gaming VG289Q1A 28 Monitor, 4K UHD')")
      }
      )
  @GetMapping("accessories")
  @ResponseStatus(code = HttpStatus.OK)
  List<Accessories> fetchAccessories(
      @RequestParam(required = false) 
        Category category ,
      @Length(max = 50)
      @Pattern(regexp = "[\\w\\s-(),]*")  
      @RequestParam(required = false)
        String accessoryName);
}


/**
 * 
 */
package com.promineotech.pc.controller.support;

/**
 * @author Kent Ma
 *
 */
public class CreateOrderTestSupport extends BaseTest{
  protected String createOrderBody() {
    //@formatter:off
    return "{\n"
        + "  \"customer\":\"MA_KENT\",\n"
        + "  \"pc_cpu\":\"CORE_I7_13700K\",\n"
        + "  \"cpu_cooler\":\"KRAKEN_Z73_RGB\",\n"
        + "  \"motherboard\":\"MPG_Z790_EDGE_WIFI\",\n"
        + "  \"pc_memory\":\"TRIDENT_Z5_RGB\",\n"
        + "  \"pc_storage\":\"970_EVO_PLUS\",\n"
        + "  \"video_card\":\"ASUS_TUF_GAMING_OC_RTX_4090\",\n"
        + "  \"pc_case\":\"O11D_XL_X\",\n"
        + "  \"power_supply\":\"RM850X_2021\",\n"
        + "  \"accessories\":[\n"
        + "    \"TUF_GAMING_VG289Q1A\",\n"
        + "  ]\n"
        + "}";
    //@formatter:on
  }
}

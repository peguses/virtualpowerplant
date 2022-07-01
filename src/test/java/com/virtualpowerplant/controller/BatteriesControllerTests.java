package com.virtualpowerplant.controller;

import com.virtualpowerplant.VirtualpowerplantApplicationTests;
import com.virtualpowerplant.dao.entity.Battery;
import com.virtualpowerplant.dto.BatteriesStatisticsDTO;
import com.virtualpowerplant.dto.BatteryDTO;
import com.virtualpowerplant.service.BatteryService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteriesController.class)
@ComponentScan(basePackages = "com.virtualpowerplant")
public class BatteriesControllerTests extends VirtualpowerplantApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BatteryService batteryService;

    Battery b_1 = new Battery("test_1", 6000, 500);
    Battery b_2 = new Battery("test_4", 7000, 600);
    Battery b_3 = new Battery("test_3", 8000, 700);
    Battery b_4 = new Battery("test_2", 9000, 800);
    Battery b_5 = new Battery("test_5", 10000, 900);


    BatteryDTO bt_1 = new BatteryDTO("test_1", 1000, 100);
    BatteryDTO bt_2 = new BatteryDTO("test_2", 2000, 200);
    BatteryDTO bt_3 = new BatteryDTO("test_5", 3000, 300);
    BatteryDTO bt_4 = new BatteryDTO("test_4", 4000, 400);
    BatteryDTO bt_5 = new BatteryDTO("test_3", 5000, 500);


    @Test
    public void testGetAllRecordWithStatisticsSuccess() throws Exception {
        List<BatteryDTO> records = new ArrayList<>(Arrays.asList(bt_5, bt_4, bt_3));
        BatteriesStatisticsDTO.Statistics stats  = new BatteriesStatisticsDTO.Statistics(1200, 400);
        BatteriesStatisticsDTO statisticsDTO = new BatteriesStatisticsDTO(records, stats);
        Mockito.when(batteryService.getAllInPostcodeRange(any(), any())).thenReturn(statisticsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/batteries?minPostcode=3000&maxPostcode=5000"))
                .andExpect(jsonPath("$.statistics.totalCapacity",
                        is(1200)))
                .andExpect(jsonPath("$.statistics.averageCapacity",
                        is(400.0)))
                .andExpect(jsonPath("$.batteries[0].name",
                        is("test_3")))
                .andExpect(jsonPath("$.batteries[1].name",
                        is("test_4")))
                .andExpect(jsonPath("$.batteries[2].name",
                        is("test_5")))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAllRecordExceptionWithWrongParam() throws Exception {
        List<BatteryDTO> records = new ArrayList<>(Arrays.asList(bt_5, bt_4, bt_3));
        BatteriesStatisticsDTO.Statistics stats  = new BatteriesStatisticsDTO.Statistics(1200, 400);
        BatteriesStatisticsDTO statisticsDTO = new BatteriesStatisticsDTO(records, stats);
        Mockito.when(batteryService.getAllInPostcodeRange(any(), any())).thenReturn(statisticsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/batteries?minPostcode=5000&maxPostcode=3000"))
                .andExpect(jsonPath("$.message",
                        is("minimum postcode value should be lesser than the maximum postcode value")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveAllBadRequestEmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/batteries")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(jsonPath("$.message",
                        is("saveAll.batteries: Battery list cannot be empty")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveAllBadRequestBodyNoName() throws Exception {
        String json  = "[{\"postcode\":\"6107\",\"capacity\":13500},{\"name\":\"Midland\",\"postcode\":\"6057\"," +
                "\"capacity\":50500},{\"name\":\"HayStreet\",\"postcode\":\"6000\",\"capacity\":23500}]";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.message",
                        is("saveAll.batteries[0].name: Battery name cannot be blank")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveAllBadRequestBodyNoPostcode() throws Exception {
        String json  = "[{\"name\":\"Cannington\",\"capacity\":13500},{\"name\":\"Midland\",\"postcode\":\"6057\"" +
                ",\"capacity\":50500},{\"name\":\"HayStreet\",\"postcode\":\"6000\",\"capacity\":23500}]";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.message",
                        is("saveAll.batteries[0].postcode: Postcode name cannot be null")))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testSaveAllBadRequestBodyNoCapacity() throws Exception {
        String json  = "[{\"name\":\"Cannington\",\"postcode\":\"6107\",\"capacity\":13500}," +
                "{\"name\":\"Midland\",\"postcode\":\"6057\",\"capacity\":50500}]";

        BatteryDTO bt_1 = new BatteryDTO("Cannington", 6107, 13500);
        BatteryDTO bt_2 = new BatteryDTO("Midland", 6057, 50500);

        BatteryDTO bt_3 = new BatteryDTO(UUID.randomUUID(), "Cannington", 6107, 13500);
        BatteryDTO bt_4 = new BatteryDTO(UUID.randomUUID(), "Midland", 6057, 50500);

        Mockito.when(batteryService.saveAll(Arrays.asList(bt_1, bt_2))).thenReturn(Arrays.asList(bt_3, bt_4));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.[0].name",
                        is("Cannington")))
                .andExpect(jsonPath("$.[1].name",
                        is("Midland")))
                .andExpect(status().isOk());
    }
}

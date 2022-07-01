package com.virtualpowerplant.service;

import com.virtualpowerplant.VirtualpowerplantApplicationTests;
import com.virtualpowerplant.dao.entity.Battery;
import com.virtualpowerplant.dao.entity.BatteryRepository;
import com.virtualpowerplant.dto.BatteriesStatisticsDTO;
import com.virtualpowerplant.dto.BatteryDTO;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BatteryServiceTests extends VirtualpowerplantApplicationTests {
    @Mock
    BatteryRepository batteryRepository;
    @InjectMocks
    BatteryService batteryService;

    @Test
    public void testSaveAll() {
        BatteryDTO bt_1 = new BatteryDTO("test_1", 1000, 100);
        BatteryDTO bt_2 = new BatteryDTO("test_2", 2000, 200);
        BatteryDTO bt_3 = new BatteryDTO("test_5", 3000, 300);
        BatteryDTO bt_4 = new BatteryDTO("test_4", 4000, 400);
        BatteryDTO bt_5 = new BatteryDTO("test_3", 5000, 500);
        List<BatteryDTO> records = new ArrayList<>(Arrays.asList(bt_1, bt_2, bt_3, bt_4, bt_5));


        Battery b_1 = new Battery("test_1", 1000, 100);
        Battery b_2 = new Battery("test_2", 2000, 200);
        Battery b_3 = new Battery("test_5", 3000, 300);
        Battery b_4 = new Battery("test_4", 4000, 400);
        Battery b_5 = new Battery("test_3", 5000, 500);

        List<Battery> repositoryRecords = new ArrayList<>(Arrays.asList(b_1, b_2, b_3, b_4, b_5));
        Mockito.when(batteryRepository.saveAll(any())).thenReturn(repositoryRecords);

        records = batteryService.saveAll(records);
        assertThat(records.size()).isEqualTo(5);

        records.forEach(batteryDTO -> {
            assertThat(batteryDTO.getUuid()).isNotNull();
        });
    }

    @Test
    public void testGetAllInPostcodeRange() {
        Battery b_1 = new Battery("test_1", 1000, 100);
        Battery b_2 = new Battery("test_2", 2000, 200);
        Battery b_3 = new Battery("test_3", 3000, 300);

        List<Battery> repositoryRecords = new ArrayList<>(Arrays.asList(b_1, b_2, b_3));
        Mockito.when(batteryRepository.findPostcodeBetweenNative(any(), any())).thenReturn(repositoryRecords);

        BatteriesStatisticsDTO stats = batteryService.getAllInPostcodeRange(1000, 3000);

        assertThat(stats.getStatistics().getAverageCapacity()).isEqualTo(200.0);
        assertThat(stats.getStatistics().getTotalCapacity()).isEqualTo(600);
        assertThat(stats.getBatteries().size()).isEqualTo(3);

    }

    @Test
    public void testGetAllInPostcodeRangeNoRecords() {

        Mockito.when(batteryRepository.findPostcodeBetweenNative(any(), any())).thenReturn(Collections.emptyList());
        BatteriesStatisticsDTO stats = batteryService.getAllInPostcodeRange(1000, 3000);
        assertThat(stats.getStatistics().getAverageCapacity()).isEqualTo(0);
        assertThat(stats.getStatistics().getTotalCapacity()).isEqualTo(0);
        assertThat(stats.getBatteries().size()).isEqualTo(0);
    }
}

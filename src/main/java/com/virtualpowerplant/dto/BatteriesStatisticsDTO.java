package com.virtualpowerplant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteriesStatisticsDTO {
    private List<BatteryDTO> batteries;
    private Statistics statistics;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Statistics {
        private int totalCapacity;
        private double averageCapacity;
    }
}

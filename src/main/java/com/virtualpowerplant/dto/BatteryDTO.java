package com.virtualpowerplant.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatteryDTO {

    private int id;
    private String name;
    private Integer postcode;
    private Integer capacity;

    public BatteryDTO(String name, Integer postalCode, Integer capacity) {
        this.name = name;
        this.postcode = postalCode;
        this.capacity = capacity;
    }
}

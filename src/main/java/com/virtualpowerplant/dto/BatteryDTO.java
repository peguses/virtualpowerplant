package com.virtualpowerplant.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatteryDTO {

    private UUID uuid;
    @NotBlank(message = "Battery name cannot be blank")
    private String name;
    @NotNull(message = "Postcode name cannot be null")
    private Integer postcode;
    @NotNull(message = "Capacity name cannot be null")
    private Integer capacity;

    public BatteryDTO(String name, Integer postalCode, Integer capacity) {
        this.name = name;
        this.postcode = postalCode;
        this.capacity = capacity;
    }
}

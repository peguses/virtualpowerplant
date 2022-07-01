package com.virtualpowerplant.service;

import com.virtualpowerplant.dao.entity.Battery;
import com.virtualpowerplant.dao.entity.BatteryRepository;
import com.virtualpowerplant.dto.BatteriesStatisticsDTO;
import com.virtualpowerplant.dto.BatteryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BatteryService {

    @Autowired
    BatteryRepository batteryRepository;

    public BatteriesStatisticsDTO getAllInPostcodeRange(Integer minPostcode, Integer maxPostCode) {
        List<BatteryDTO> batteryDTOS = batteryRepository.findPostcodeBetweenNative(minPostcode, maxPostCode)
                .stream().map(battery -> batteryToBatteryDTO(battery))
                .collect(Collectors.toList());
        double average  = !batteryDTOS.isEmpty() ? batteryDTOS
                .stream().mapToInt(b -> Objects.nonNull(b.getCapacity()) ? b.getCapacity(): 0 )
                .average().getAsDouble() : 0;
        int sum = !batteryDTOS.isEmpty() ? batteryDTOS.stream().mapToInt(b -> b.getCapacity()).sum() : 0;
        return new BatteriesStatisticsDTO(batteryDTOS,
                new BatteriesStatisticsDTO.Statistics(sum, average));
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<BatteryDTO> saveAll(List<BatteryDTO> batteries) {
        return batteryRepository
                .saveAll(batteries
                        .stream()
                        .map(batteryDTO -> batteryDTOToBattery(batteryDTO))
                        .collect(Collectors.toList()))
                .stream().map(battery -> batteryToBatteryDTO(battery))
                .collect(Collectors.toList());
    }

    private Battery batteryDTOToBattery(BatteryDTO batteryDTO) {
        return new Battery(batteryDTO.getName(), batteryDTO.getPostcode(), batteryDTO.getCapacity());
    }

    private BatteryDTO batteryToBatteryDTO(Battery battery) {
        return new BatteryDTO(Objects.nonNull(battery.getUuid()) ? battery.getUuid() : null,
                battery.getName(),
                battery.getPostcode(),
                battery.getCapacity());
    }
}

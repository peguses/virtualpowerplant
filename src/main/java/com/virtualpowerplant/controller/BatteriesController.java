package com.virtualpowerplant.controller;

import com.virtualpowerplant.dto.BatteriesStatisticsDTO;
import com.virtualpowerplant.dto.BatteryDTO;
import com.virtualpowerplant.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class BatteriesController {
    @Autowired
    BatteryService batteryService;
    @GetMapping(value = "/batteries")
    public BatteriesStatisticsDTO getStatistics (@RequestParam(name = "minPostcode") Integer minPostcode,
                                                       @RequestParam(name = "maxPostcode") Integer maxPostcode ) {
        return batteryService.getAllInPostcodeRange(minPostcode, maxPostcode);
    }

    @PostMapping(value = "/batteries")
    public List<BatteryDTO> saveAll(@RequestBody List<BatteryDTO> batteries) {
        return batteryService.saveAll(batteries);
    }

}

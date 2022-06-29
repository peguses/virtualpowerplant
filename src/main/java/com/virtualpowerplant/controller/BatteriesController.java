package com.virtualpowerplant.controller;

import com.virtualpowerplant.dto.BatteriesStatisticsDTO;
import com.virtualpowerplant.dto.BatteryDTO;
import com.virtualpowerplant.exception.BadRequestException;
import com.virtualpowerplant.service.BatteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@Validated
public class BatteriesController {

    Logger logger = LoggerFactory.getLogger(BatteriesController.class);
    @Autowired
    BatteryService batteryService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    @GetMapping(value = "/batteries", produces = "application/json")
    public ResponseEntity<BatteriesStatisticsDTO> getStatistics (@RequestAttribute("requestId") String requestId,
                                                                @RequestParam(name = "minPostcode") Integer minPostcode,
                                                                @RequestParam(name = "maxPostcode") Integer maxPostcode ) {
        logger.info("get statistics of batteries by postcode api request:"+requestId);
        if (minPostcode > maxPostcode ) {
            throw new BadRequestException("minimum postcode value should be lesser than the maximum postcode value");
        }
        return ResponseEntity
                .ok()
                .header("request-id", requestId)
                .body(batteryService.getAllInPostcodeRange(minPostcode, maxPostcode));
    }

    @PostMapping(value = "/batteries", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<BatteryDTO>> saveAll(@RequestAttribute("requestId") String requestId,
                                    @Valid @RequestBody
                                    @NotEmpty(message = "Battery list cannot be empty")
                                    List<@Valid BatteryDTO> batteries) {
        logger.info("save all batteries api request:"+requestId);
        return ResponseEntity
                .ok()
                .header("request-id", requestId)
                .body( batteryService.saveAll(batteries));
    }

}

package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.DriverDTO;
import com.panchenko.LogisticsApp.model.Driver;
import com.panchenko.LogisticsApp.service.DriverService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/api/drivers"))
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<DriverDTO> driverDTOList = driverService.getAll().stream()
                .map(this.driverService::convertToDriverDTO)
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/drivers");
        map.put("status code", HttpStatus.OK);
        map.put("body", driverDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        DriverDTO driverDTO = driverService.convertToDriverDTO(driverService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/drivers/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", driverDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DriverDTO driverDTO, BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        Driver driver = driverService.create(driverService.convertToDriver(driverDTO));

        DriverDTO driverDTOResponse = driverService.convertToDriverDTO(driver);
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/drivers");
        map.put("status code", HttpStatus.OK);
        map.put("body", driverDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid DriverDTO driverDTO,
                                             BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        Driver updatedDriver = driverService.readById(id);

        driverService.update(updatedDriver, driverDTO);

        DriverDTO driverDTOResponse = driverService.convertToDriverDTO(updatedDriver);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/drivers/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", driverDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        driverService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

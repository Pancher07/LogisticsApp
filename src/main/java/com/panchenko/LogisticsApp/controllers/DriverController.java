package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.DriverDTO;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotCreatedException;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotUpdatedException;
import com.panchenko.LogisticsApp.model.Driver;
import com.panchenko.LogisticsApp.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/api/drivers"))
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverDTO> getAll() {
        return driverService.getAll().stream()
                .map(this.driverService::convertToDriverDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DriverDTO getById(@PathVariable long id) {
        return driverService.convertToDriverDTO(driverService.readById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DriverDTO driverDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new DriverNotCreatedException(errorMessage.toString());
        }
        driverService.create(driverService.convertToDriver(driverDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody @Valid DriverDTO driverDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new DriverNotUpdatedException(errorMessage.toString());
        }
        Driver updatedDriver = driverService.readById(id);

        if (driverDTO.getName() != null) {
            updatedDriver.setName(driverDTO.getName());
        }
        if (driverDTO.getMiddleName() != null) {
            updatedDriver.setMiddleName(driverDTO.getMiddleName());
        }
        if (driverDTO.getSurname() != null) {
            updatedDriver.setSurname(driverDTO.getSurname());
        }
        if (driverDTO.getPhone() != null) {
            updatedDriver.setPhone(driverDTO.getPhone());
        }
        if (driverDTO.getLastTimeWorked() != null) {
            updatedDriver.setLastTimeWorked(driverDTO.getLastTimeWorked());
        }

        driverService.update(updatedDriver);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        driverService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

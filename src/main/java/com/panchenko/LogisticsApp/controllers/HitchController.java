package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ContractorResponse;
import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.dto.HitchResponse;
import com.panchenko.LogisticsApp.exception.HitchException.HitchNotCreatedException;
import com.panchenko.LogisticsApp.exception.HitchException.HitchNotUpdatedException;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.service.HitchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hitches")
public class HitchController {
    private final HitchService hitchService;

    public HitchController(HitchService hitchService) {
        this.hitchService = hitchService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<HitchResponse> hitchResponseList = hitchService.getAll().stream()
                .map(HitchResponse::new).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches");
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        HitchResponse hitchResponse = new HitchResponse(hitchService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchResponse);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid HitchDTO hitchDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new HitchNotCreatedException(errorMessage.toString());
        }
        Hitch hitch = hitchService.create(hitchService.convertToHitch(hitchDTO));

        HitchResponse hitchResponse = new HitchResponse(hitch);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", hitchResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid HitchDTO hitchDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new HitchNotUpdatedException(errorMessage.toString());
        }
        Hitch updatedHitch = hitchService.readById(id);

        if (hitchDTO.getLocation() != null) {
            updatedHitch.setLocation(hitchDTO.getLocation());
        }
        if (hitchDTO.getComment() != null) {
            updatedHitch.setComment(hitchDTO.getComment());
        }
        if (hitchDTO.getVehicleStatus() != null) {
            updatedHitch.setVehicleStatus(hitchDTO.getVehicleStatus());
        }
        if (hitchDTO.getTruckTractor() != null) {
            updatedHitch.setTruckTractor(hitchDTO.getTruckTractor());
        }
        if (hitchDTO.getTrailer() != null) {
            updatedHitch.setTrailer(hitchDTO.getTrailer());
        }
        if (hitchDTO.getDriver() != null) {
            updatedHitch.setDriver(hitchDTO.getDriver());
        }
        if (hitchDTO.getProject() != null) {
            updatedHitch.setProject(hitchDTO.getProject());
        }
        if (hitchDTO.getLogistician() != null) {
            updatedHitch.setLogistician(hitchDTO.getLogistician());
        }

        hitchService.update(updatedHitch);

        HitchResponse hitchResponse = new HitchResponse(updatedHitch);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        hitchService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

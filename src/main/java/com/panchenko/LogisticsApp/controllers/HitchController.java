package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.service.*;
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
    private final TrailerService trailerService;
    private final DriverService driverService;
    private final ProjectService projectService;
    private final LogisticianService logisticianService;
    private final TruckTractorService truckTractorService;

    public HitchController(HitchService hitchService, TrailerService trailerService, DriverService driverService,
                           ProjectService projectService, LogisticianService logisticianService,
                           TruckTractorService truckTractorService) {
        this.hitchService = hitchService;
        this.trailerService = trailerService;
        this.driverService = driverService;
        this.projectService = projectService;
        this.logisticianService = logisticianService;
        this.truckTractorService = truckTractorService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<HitchDTO> hitchDTOList = hitchService.getAll().stream()
                .map(hitchService::convertToHitchDTO).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches");
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        HitchDTO hitchDTO = hitchService.convertToHitchDTO(hitchService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchDTO);
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
            throw new EntityNotCreatedException(errorMessage.toString());
        }
        Hitch hitch = hitchService.create(hitchService.convertToHitch(hitchDTO));

        HitchDTO hitchDTOResponse = hitchService.convertToHitchDTO(hitch);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", hitchDTOResponse);
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
            throw new EntityNotUpdatedException(errorMessage.toString());
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
        if (hitchDTO.getTruckTractorId() != null) {
            updatedHitch.setTruckTractor(truckTractorService.readById(hitchDTO.getTruckTractorId()));
        }
        if (hitchDTO.getTrailerId() != null) {
            updatedHitch.setTrailer(trailerService.readById(hitchDTO.getTrailerId()));
        }
        if (hitchDTO.getDriverId() != null) {
            updatedHitch.setDriver(driverService.readById(hitchDTO.getDriverId()));
        }
        if (hitchDTO.getProjectId() != null) {
            updatedHitch.setProject(projectService.readById(hitchDTO.getProjectId()));
        }
        if (hitchDTO.getLogisticianId() != null) {
            updatedHitch.setLogistician(logisticianService.readById(hitchDTO.getLogisticianId()));
        }

        hitchService.update(updatedHitch);

        HitchDTO hitchDTOResponse = hitchService.convertToHitchDTO(updatedHitch);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/hitches/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", hitchDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        hitchService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

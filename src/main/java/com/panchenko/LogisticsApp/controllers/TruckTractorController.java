package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TruckTractorDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.TruckTractor;
import com.panchenko.LogisticsApp.service.TruckTractorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/truck-tractors")
public class TruckTractorController {
    private final TruckTractorService truckTractorService;

    public TruckTractorController(TruckTractorService truckTractorService) {
        this.truckTractorService = truckTractorService;
    }

    @GetMapping
    public List<TruckTractorDTO> getAll() {
        return truckTractorService.getAll().stream()
                .map(this.truckTractorService::convertToTruckTractorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TruckTractorDTO getById(@PathVariable long id) {
        return truckTractorService.convertToTruckTractorDTO(truckTractorService.readById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TruckTractorDTO truckTractorDTO,
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
        truckTractorService.create(truckTractorService.convertToTruckTractor(truckTractorDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody @Valid TruckTractorDTO truckTractorDTO,
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
        TruckTractor updatedTruckTractor = truckTractorService.readById(id);

        if (truckTractorDTO.getPlateNumber() != null) {
            updatedTruckTractor.setPlateNumber(truckTractorDTO.getPlateNumber());
        }
        if (truckTractorDTO.getModel() != null) {
            updatedTruckTractor.setModel(truckTractorDTO.getModel());
        }
        if (truckTractorDTO.getPump() != null) {
            updatedTruckTractor.setPump(truckTractorDTO.getPump());
        }
        truckTractorService.update(updatedTruckTractor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        truckTractorService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

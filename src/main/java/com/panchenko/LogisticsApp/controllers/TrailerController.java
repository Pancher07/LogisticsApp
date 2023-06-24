package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TrailerDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.Trailer;
import com.panchenko.LogisticsApp.service.TrailerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trailers")
public class TrailerController {
    private final TrailerService trailerService;

    public TrailerController(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    @GetMapping
    public List<TrailerDTO> getAll() {
        return trailerService.getAll().stream()
                .map(this.trailerService::convertToTrailerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TrailerDTO getById(@PathVariable long id) {
        return trailerService.convertToTrailerDTO(trailerService.readById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TrailerDTO trailerDTO,
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
        trailerService.create(trailerService.convertToTrailer(trailerDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody @Valid TrailerDTO trailerDTO,
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
        Trailer updatedTrailer = trailerService.readById(id);

        if (trailerDTO.getPlateNumber() != null) {
            updatedTrailer.setPlateNumber(trailerDTO.getPlateNumber());
        }
        if (trailerDTO.getModel() != null) {
            updatedTrailer.setModel(trailerDTO.getModel());
        }
        if (trailerDTO.getVolume() != 0.0) {
            updatedTrailer.setVolume(trailerDTO.getVolume());
        }
        if (trailerDTO.getCalibration() != null) {
            updatedTrailer.setCalibration(trailerDTO.getCalibration());
        }
        if (trailerDTO.getPetroleumType() != null) {
            updatedTrailer.setPetroleumType(trailerDTO.getPetroleumType());
        }
        trailerService.update(updatedTrailer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        trailerService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

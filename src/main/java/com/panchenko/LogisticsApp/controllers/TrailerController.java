package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TrailerDTO;
import com.panchenko.LogisticsApp.model.Trailer;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import com.panchenko.LogisticsApp.service.TrailerService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trailers")
public class TrailerController {
    private final TrailerService trailerService;

    public TrailerController(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<TrailerDTO> trailerDTOList = trailerService.getAll().stream()
                .map(trailerService::convertToTrailerDTO).toList();
        return ResponseEntity.ok(trailerDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TrailerDTO trailerDTO = trailerService.convertToTrailerDTO(trailerService.readById(id));
        return ResponseEntity.ok(trailerDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TrailerDTO trailerDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        Trailer trailer = trailerService.create(trailerService.convertToTrailer(trailerDTO));
        TrailerDTO trailerDTOResponse = trailerService.convertToTrailerDTO(trailer);
        return ResponseEntity.ok(trailerDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TrailerDTO trailerDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        Trailer updatedTrailer = trailerService.readById(id);
        trailerService.update(updatedTrailer, trailerDTO);
        TrailerDTO trailerDTOResponse = trailerService.convertToTrailerDTO(updatedTrailer);
        return ResponseEntity.ok(trailerDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        trailerService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

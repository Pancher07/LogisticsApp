package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TrailerDTO;
import com.panchenko.LogisticsApp.model.Trailer;
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
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/trailers");
        map.put("status code", HttpStatus.OK);
        map.put("body", trailerDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TrailerDTO trailerDTO = trailerService.convertToTrailerDTO(trailerService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/trailers/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", trailerDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TrailerDTO trailerDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        Trailer trailer = trailerService.create(trailerService.convertToTrailer(trailerDTO));

        TrailerDTO trailerDTOResponse = trailerService.convertToTrailerDTO(trailer);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/trailers");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", trailerDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TrailerDTO trailerDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        Trailer updatedTrailer = trailerService.readById(id);

        trailerService.update(updatedTrailer, trailerDTO);

        TrailerDTO trailerDTOResponse = trailerService.convertToTrailerDTO(updatedTrailer);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/trailers/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", trailerDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        trailerService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

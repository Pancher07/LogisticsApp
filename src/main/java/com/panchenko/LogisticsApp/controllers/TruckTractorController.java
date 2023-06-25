package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TruckTractorDTO;
import com.panchenko.LogisticsApp.model.TruckTractor;
import com.panchenko.LogisticsApp.service.TruckTractorService;
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
@RequestMapping("/api/truck-tractors")
public class TruckTractorController {
    private final TruckTractorService truckTractorService;

    public TruckTractorController(TruckTractorService truckTractorService) {
        this.truckTractorService = truckTractorService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<TruckTractorDTO> truckTractorDTOList = truckTractorService.getAll().stream()
                .map(truckTractorService::convertToTruckTractorDTO).toList();
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/truck-tractors");
        map.put("status code", HttpStatus.OK);
        map.put("body", truckTractorDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TruckTractorDTO truckTractorDTO = truckTractorService.convertToTruckTractorDTO(truckTractorService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/truck-tractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", truckTractorDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TruckTractorDTO truckTractorDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        TruckTractor truckTractor = truckTractorService
                .create(truckTractorService.convertToTruckTractor(truckTractorDTO));

        TruckTractorDTO truckTractorDTOResponse = truckTractorService.convertToTruckTractorDTO(truckTractor);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/truck-tractors");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", truckTractorDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TruckTractorDTO truckTractorDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        TruckTractor updatedTruckTractor = truckTractorService.readById(id);

        truckTractorService.update(updatedTruckTractor, truckTractorDTO);

        TruckTractorDTO truckTractorDTOResponse = truckTractorService.convertToTruckTractorDTO(updatedTruckTractor);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/truck-tractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", truckTractorDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        truckTractorService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

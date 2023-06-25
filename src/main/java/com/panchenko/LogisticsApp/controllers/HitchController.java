package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.service.HitchService;
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
@RequestMapping("/api/hitches")
public class HitchController {
    private final HitchService hitchService;

    public HitchController(HitchService hitchService) {
        this.hitchService = hitchService;
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
        CheckErrors.checkErrorsForCreate(bindingResult);
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
        CheckErrors.checkErrorsForUpdate(bindingResult);

        Hitch updatedHitch = hitchService.readById(id);

        hitchService.update(updatedHitch, hitchDTO);

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

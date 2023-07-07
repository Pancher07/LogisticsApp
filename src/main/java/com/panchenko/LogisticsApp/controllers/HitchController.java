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

import java.util.List;

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
        return ResponseEntity.ok(hitchDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        HitchDTO hitchDTO = hitchService.convertToHitchDTO(hitchService.readById(id));
        return ResponseEntity.ok(hitchDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid HitchDTO hitchDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        Hitch hitch = hitchService.create(hitchService.convertToHitch(hitchDTO));
        HitchDTO hitchDTOResponse = hitchService.convertToHitchDTO(hitch);
        return ResponseEntity.ok(hitchDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid HitchDTO hitchDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        Hitch updatedHitch = hitchService.readById(id);
        hitchService.update(updatedHitch, hitchDTO);
        HitchDTO hitchDTOResponse = hitchService.convertToHitchDTO(updatedHitch);
        return ResponseEntity.ok(hitchDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        hitchService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

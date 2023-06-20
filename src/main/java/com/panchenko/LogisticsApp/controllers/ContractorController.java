package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.dto.ContractorResponse;
import com.panchenko.LogisticsApp.exception.ContractorException.ContractorNotCreatedException;
import com.panchenko.LogisticsApp.exception.ContractorException.ContractorNotUpdatedException;
import com.panchenko.LogisticsApp.model.Contractor;
import com.panchenko.LogisticsApp.service.ContractorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contractors")
public class ContractorController {
    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ContractorResponse> contractorResponseList = contractorService.getAll().stream()
                .map(ContractorResponse::new)
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors");
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ContractorResponse contractorResponse = new ContractorResponse(contractorService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorResponse);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ContractorDTO contractorDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ContractorNotCreatedException(errorMessage.toString());
        }
        Contractor contractor = contractorService.create(contractorService.convertToContractor(contractorDTO));
        ContractorResponse contractorResponse = new ContractorResponse(contractor);

        Map<String, Object> map = new HashMap<>();

        map.put("status code", HttpStatus.CREATED);
        map.put("header", "URL: /api/contractors/");
        map.put("body", contractorResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid ContractorDTO contractorDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ContractorNotUpdatedException(errorMessage.toString());
        }
        Contractor updatedContractor = contractorService.readById(id);

        if (contractorDTO.getName() != null) {
            updatedContractor.setName(contractorDTO.getName());
        }
        if (contractorDTO.getManager() != null) {
            updatedContractor.setManager(contractorDTO.getManager());
        }

        contractorService.update(updatedContractor);

        ContractorResponse contractorResponse = new ContractorResponse(updatedContractor);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorResponse);

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        contractorService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

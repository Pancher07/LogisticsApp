package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.Contractor;
import com.panchenko.LogisticsApp.service.ContractorService;
import com.panchenko.LogisticsApp.service.ManagerService;
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
@RequestMapping("/api/contractors")
public class ContractorController {
    private final ContractorService contractorService;
    private final ManagerService managerService;

    public ContractorController(ContractorService contractorService, ManagerService managerService) {
        this.contractorService = contractorService;
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ContractorDTO> contractorDTOList = contractorService.getAll().stream()
                .map(this.contractorService::convertToContractorDTO).toList();
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors");
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ContractorDTO contractorDTO = contractorService.convertToContractorDTO(contractorService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorDTO);
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
            throw new EntityNotCreatedException(errorMessage.toString());
        }
        Contractor contractor = contractorService.create(contractorService.convertToContractor(contractorDTO));

        ContractorDTO contractorDTOResponse = contractorService.convertToContractorDTO(contractor);

        Map<String, Object> map = new HashMap<>();

        map.put("status code", HttpStatus.CREATED);
        map.put("header", "URL: /api/contractors/");
        map.put("body", contractorDTOResponse);
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
            throw new EntityNotUpdatedException(errorMessage.toString());
        }
        Contractor updatedContractor = contractorService.readById(id);

        if (contractorDTO.getName() != null) {
            updatedContractor.setName(contractorDTO.getName());
        }
        if (contractorDTO.getManagerId() != null) {
            updatedContractor.setManager(managerService.readById(contractorDTO.getManagerId()));
        }

        contractorService.update(updatedContractor);

        ContractorDTO contractorDTOResponse = contractorService.convertToContractorDTO(updatedContractor);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/contractors/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", contractorDTOResponse);

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        contractorService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

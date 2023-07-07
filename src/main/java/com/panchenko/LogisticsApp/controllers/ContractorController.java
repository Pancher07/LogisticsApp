package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.model.Contractor;
import com.panchenko.LogisticsApp.service.ContractorService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractors")
public class ContractorController {
    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ContractorDTO> contractorDTOList = contractorService.getAll().stream()
                .map(this.contractorService::convertToContractorDTO).toList();
        return ResponseEntity.ok(contractorDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ContractorDTO contractorDTO = contractorService.convertToContractorDTO(contractorService.readById(id));
        return ResponseEntity.ok(contractorDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ContractorDTO contractorDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        Contractor contractor = contractorService.create(contractorService.convertToContractor(contractorDTO));
        ContractorDTO contractorDTOResponse = contractorService.convertToContractorDTO(contractor);
        return ResponseEntity.ok(contractorDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid ContractorDTO contractorDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        Contractor updatedContractor = contractorService.readById(id);
        contractorService.update(updatedContractor, contractorDTO);
        ContractorDTO contractorDTOResponse = contractorService.convertToContractorDTO(updatedContractor);
        return ResponseEntity.ok(contractorDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        contractorService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

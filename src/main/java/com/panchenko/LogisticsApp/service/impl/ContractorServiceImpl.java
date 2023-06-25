package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Contractor;
import com.panchenko.LogisticsApp.repository.ContractorRepository;
import com.panchenko.LogisticsApp.service.ContractorService;
import com.panchenko.LogisticsApp.service.ManagerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorServiceImpl implements ContractorService {
    private final ContractorRepository contractorRepository;
    private final ModelMapper modelMapper;
    private final ManagerService managerService;

    public ContractorServiceImpl(ContractorRepository contractorRepository, ModelMapper modelMapper, ManagerService managerService) {
        this.contractorRepository = contractorRepository;
        this.modelMapper = modelMapper;
        this.managerService = managerService;
    }

    @Override
    public Contractor create(Contractor contractor) {
        if (contractor == null) {
            throw new NullEntityReferenceException("Contractor cannot be 'null'");
        }
        return contractorRepository.save(contractor);
    }

    @Override
    public Contractor readById(long id) {
        return contractorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Contractor with id " + id + " not found"));
    }

    @Override
    public Contractor update(Contractor updatedContractor, ContractorDTO contractorDTO) {
        if (updatedContractor == null) {
            throw new NullEntityReferenceException("Contractor cannot be 'null'");
        }
        if (contractorDTO.getName() != null) {
            updatedContractor.setName(contractorDTO.getName());
        }
        if (contractorDTO.getManagerId() != null) {
            updatedContractor.setManager(managerService.readById(contractorDTO.getManagerId()));
        }
        return contractorRepository.save(updatedContractor);
    }

    @Override
    public void delete(long id) {
        Contractor contractor = readById(id);
        contractorRepository.delete(contractor);
    }

    @Override
    public List<Contractor> getAll() {
        return contractorRepository.findAll();
    }

    @Override
    public Contractor convertToContractor(ContractorDTO contractorDTO) {
        return modelMapper.map(contractorDTO, Contractor.class);
    }

    @Override
    public ContractorDTO convertToContractorDTO(Contractor contractor) {
        return modelMapper.map(contractor, ContractorDTO.class);
    }
}

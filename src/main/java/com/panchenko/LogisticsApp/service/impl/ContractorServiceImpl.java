package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ContractorDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Contractor;
import com.panchenko.LogisticsApp.repository.ContractorRepository;
import com.panchenko.LogisticsApp.service.ContractorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorServiceImpl implements ContractorService {
    private final ContractorRepository contractorRepository;
    private final ModelMapper modelMapper;

    public ContractorServiceImpl(ContractorRepository contractorRepository, ModelMapper modelMapper) {
        this.contractorRepository = contractorRepository;
        this.modelMapper = modelMapper;
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
    public Contractor update(Contractor contractor) {
        if (contractor == null) {
            throw new NullEntityReferenceException("Contractor cannot be 'null'");
        }
        readById(contractor.getId());
        return contractorRepository.save(contractor);
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

package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.TruckTractorDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.TruckTractor;
import com.panchenko.LogisticsApp.repository.TruckTractorRepository;
import com.panchenko.LogisticsApp.service.TruckTractorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckTractorServiceImpl implements TruckTractorService {
    private final TruckTractorRepository truckTractorRepository;
    private final ModelMapper modelMapper;

    public TruckTractorServiceImpl(TruckTractorRepository truckTractorRepository, ModelMapper modelMapper) {
        this.truckTractorRepository = truckTractorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TruckTractor create(TruckTractor truckTractor) {
        if (truckTractor == null) {
            throw new NullEntityReferenceException("Truck tractor cannot be 'null'");
        }
        return truckTractorRepository.save(truckTractor);
    }

    @Override
    public TruckTractor readById(long id) {
        return truckTractorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Truck tractor with id " + id + " not found"));
    }

    @Override
    public TruckTractor update(TruckTractor truckTractor) {
        if (truckTractor == null) {
            throw new NullEntityReferenceException("Truck tractor cannot be 'null'");
        }
        readById(truckTractor.getId());
        return truckTractorRepository.save(truckTractor);
    }

    @Override
    public void delete(long id) {
        TruckTractor truckTractor = readById(id);
        truckTractorRepository.delete(truckTractor);
    }

    @Override
    public List<TruckTractor> getAll() {
        return truckTractorRepository.findAll();
    }

    @Override
    public TruckTractor convertToTruckTractor(TruckTractorDTO truckTractorDTO) {
        return modelMapper.map(truckTractorDTO, TruckTractor.class);
    }

    @Override
    public TruckTractorDTO convertToTruckTractorDTO(TruckTractor truckTractor) {
        return modelMapper.map(truckTractor, TruckTractorDTO.class);
    }
}

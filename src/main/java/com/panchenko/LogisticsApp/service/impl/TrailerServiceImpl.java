package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.TrailerDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Trailer;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import com.panchenko.LogisticsApp.repository.TrailerRepository;
import com.panchenko.LogisticsApp.service.TrailerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailerServiceImpl implements TrailerService {
    private final TrailerRepository trailerRepository;
    private final ModelMapper modelMapper;

    public TrailerServiceImpl(TrailerRepository trailerRepository, ModelMapper modelMapper) {
        this.trailerRepository = trailerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Trailer create(Trailer trailer) {
        if (trailer == null) {
            throw new NullPointerException("Trailer cannot be 'null'");
        }
        return trailerRepository.save(trailer);
    }

    @Override
    public Trailer readById(long id) {
        return trailerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trailer with id " + id + " not found"));
    }

    @Override
    public Trailer update(Trailer updatedTrailer, TrailerDTO trailerDTO) {
        if (updatedTrailer == null) {
            throw new NullEntityReferenceException("Trailer cannot be 'null'");
        }
        if (trailerDTO.getPlateNumber() != null) {
            updatedTrailer.setPlateNumber(trailerDTO.getPlateNumber());
        }
        if (trailerDTO.getModel() != null) {
            updatedTrailer.setModel(trailerDTO.getModel());
        }
        if (trailerDTO.getVolume() != 0.0) {
            updatedTrailer.setVolume(trailerDTO.getVolume());
        }
        if (trailerDTO.getCalibration() != null) {
            updatedTrailer.setCalibration(trailerDTO.getCalibration());
        }
        if (trailerDTO.getPetroleumType() != null) {
            updatedTrailer.setPetroleumType(trailerDTO.getPetroleumType());
        }
        return trailerRepository.save(updatedTrailer);
    }

    @Override
    public void delete(long id) {
        Trailer trailer = readById(id);
        trailerRepository.delete(trailer);
    }

    @Override
    public List<Trailer> getAll() {
        return trailerRepository.findAll();
    }

    @Override
    public Trailer convertToTrailer(TrailerDTO trailerDTO) {
        return modelMapper.map(trailerDTO, Trailer.class);
    }

    @Override
    public TrailerDTO convertToTrailerDTO(Trailer trailer) {
        return modelMapper.map(trailer, TrailerDTO.class);
    }
}

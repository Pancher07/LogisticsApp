package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.repository.HitchRepository;
import com.panchenko.LogisticsApp.service.HitchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HitchServiceImpl implements HitchService {
    private final HitchRepository hitchRepository;
    private final ModelMapper modelMapper;

    public HitchServiceImpl(HitchRepository hitchRepository, ModelMapper modelMapper) {
        this.hitchRepository = hitchRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Override
    public Hitch create(Hitch hitch) {
        if (hitch == null) {
            throw new NullEntityReferenceException("Hitch cannot be 'null'");
        }
        return hitchRepository.save(hitch);
    }

    @Override
    public Hitch readById(long id) {
        return hitchRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Hitch with id " + id + " not found"));
    }

    @Override
    public Hitch update(Hitch hitch) {
        if (hitch == null) {
            throw new NullEntityReferenceException("Hitch cannot be 'null'");
        }
        readById(hitch.getId());
        return hitchRepository.save(hitch);
    }

    @Override
    public void delete(long id) {
        Hitch hitch = readById(id);
        hitchRepository.delete(hitch);
    }

    @Override
    public List<Hitch> getAll() {
        return hitchRepository.findAll();
    }

    @Override
    public Hitch convertToHitch(HitchDTO hitchDTO) {
        return modelMapper.map(hitchDTO, Hitch.class);
    }

    @Override
    public HitchDTO convertToHitchDTO(Hitch hitch) {
        return modelMapper.map(hitch, HitchDTO.class);
    }
}

package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import com.panchenko.LogisticsApp.repository.HitchRepository;
import com.panchenko.LogisticsApp.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HitchServiceImpl implements HitchService {
    private final HitchRepository hitchRepository;
    private final ModelMapper modelMapper;
    private final TrailerService trailerService;
    private final DriverService driverService;
    private final ProjectService projectService;
    private final LogisticianService logisticianService;
    private final TruckTractorService truckTractorService;

    public HitchServiceImpl(HitchRepository hitchRepository, ModelMapper modelMapper, TrailerService trailerService,
                            DriverService driverService, ProjectService projectService,
                            LogisticianService logisticianService, TruckTractorService truckTractorService) {
        this.hitchRepository = hitchRepository;
        this.modelMapper = modelMapper;
        this.trailerService = trailerService;
        this.driverService = driverService;
        this.projectService = projectService;
        this.logisticianService = logisticianService;
        this.truckTractorService = truckTractorService;
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
    public Hitch update(Hitch updatedHitch, HitchDTO hitchDTO) {
        if (updatedHitch == null) {
            throw new NullEntityReferenceException("Hitch cannot be 'null'");
        }
        if (hitchDTO.getLocation() != null) {
            updatedHitch.setLocation(hitchDTO.getLocation());
        }
        if (hitchDTO.getComment() != null) {
            updatedHitch.setComment(hitchDTO.getComment());
        }
        if (hitchDTO.getVehicleStatus() != null) {
            updatedHitch.setVehicleStatus(hitchDTO.getVehicleStatus());
        }
        if (hitchDTO.getTruckTractorId() != null) {
            updatedHitch.setTruckTractor(truckTractorService.readById(hitchDTO.getTruckTractorId()));
        }
        if (hitchDTO.getTrailerId() != null) {
            updatedHitch.setTrailer(trailerService.readById(hitchDTO.getTrailerId()));
        }
        if (hitchDTO.getDriverId() != null) {
            updatedHitch.setDriver(driverService.readById(hitchDTO.getDriverId()));
        }
        if (hitchDTO.getProjectId() != null) {
            updatedHitch.setProject(projectService.readById(hitchDTO.getProjectId()));
        }
        if (hitchDTO.getLogisticianId() != null) {
            updatedHitch.setLogistician(logisticianService.readById(hitchDTO.getLogisticianId()));
        }
        return hitchRepository.save(updatedHitch);
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

    @Override
    public List<Hitch> getAllByVehicleStatus(VehicleStatus vehicleStatus) {
        return hitchRepository.findAllByVehicleStatus(vehicleStatus).get();
    }

    @Override
    public List<Hitch> getAllByLoadedWithProduct(TypeOfLightProduct typeOfLightProduct) {
        return hitchRepository.findAllByLoadedWithProduct(typeOfLightProduct).get();
    }
}

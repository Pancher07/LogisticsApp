package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.LoadingOrderDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.LoadingOrder;
import com.panchenko.LogisticsApp.repository.LoadingOrderRepository;
import com.panchenko.LogisticsApp.service.LoadingOrderService;
import com.panchenko.LogisticsApp.service.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoadingOrderServiceImpl implements LoadingOrderService {
    private final LoadingOrderRepository loadingOrderRepository;
    private final ModelMapper modelMapper;
    private final TaskListService taskListService;

    public LoadingOrderServiceImpl(LoadingOrderRepository loadingOrderRepository, ModelMapper modelMapper,
                                   TaskListService taskListService) {
        this.loadingOrderRepository = loadingOrderRepository;
        this.modelMapper = modelMapper;
        this.taskListService = taskListService;
    }

    @Override
    public LoadingOrder create(LoadingOrder loadingOrder) {
        if (loadingOrder == null) {
            throw new NullEntityReferenceException("Loading order cannot be 'null'");
        }
        enrichLoadingOrder(loadingOrder);
        return loadingOrderRepository.save(loadingOrder);
    }

    @Override
    public LoadingOrder readById(long id) {
        return loadingOrderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Loading order with id " + id + " not found"));
    }

    @Override
    public LoadingOrder update(LoadingOrder updatedLoadingOrder, LoadingOrderDTO loadingOrderDTO) {
        if (updatedLoadingOrder == null) {
            throw new NullEntityReferenceException("Loading order cannot be 'null'");
        }
        if (loadingOrderDTO.getLoadingPoint() != null) {
            updatedLoadingOrder.setLoadingPoint(loadingOrderDTO.getLoadingPoint());
        }
        if (loadingOrderDTO.getPetroleumType() != null) {
            updatedLoadingOrder.setPetroleumType(loadingOrderDTO.getPetroleumType());
        }
        if (loadingOrderDTO.getCountOfVehicle() != null) {
            updatedLoadingOrder.setCountOfVehicle(loadingOrderDTO.getCountOfVehicle());
        }
        if (loadingOrderDTO.getLoadingDateTime() != null) {
            updatedLoadingOrder.setLoadingDateTime(loadingOrderDTO.getLoadingDateTime());
        }
        if (loadingOrderDTO.getOrderStatus() != null) {
            updatedLoadingOrder.setOrderStatus(loadingOrderDTO.getOrderStatus());
        }
        return loadingOrderRepository.save(updatedLoadingOrder);
    }

    @Override
    public void delete(long id) {
        LoadingOrder loadingOrder = readById(id);
        loadingOrderRepository.delete(loadingOrder);
    }

    @Override
    public List<LoadingOrder> getAll() {
        return loadingOrderRepository.findAll();
    }

    @Override
    public LoadingOrder convertToLoadingOrder(LoadingOrderDTO loadingOrderDTO) {
        return modelMapper.map(loadingOrderDTO, LoadingOrder.class);
    }

    @Override
    public LoadingOrderDTO convertToLoadingOrderDTO(LoadingOrder loadingOrder) {
        return modelMapper.map(loadingOrder, LoadingOrderDTO.class);
    }

    private void enrichLoadingOrder(LoadingOrder loadingOrder) {
        loadingOrder.setCreatedAt(LocalDateTime.now());
    }
}

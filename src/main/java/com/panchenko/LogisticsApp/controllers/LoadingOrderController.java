package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.LoadingOrderDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.LoadingOrder;
import com.panchenko.LogisticsApp.service.LoadingOrderService;
import com.panchenko.LogisticsApp.service.TaskListService;
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
@RequestMapping("/api/loading-orders")
public class LoadingOrderController {
    private final LoadingOrderService loadingOrderService;
    private final TaskListService taskListService;

    public LoadingOrderController(LoadingOrderService loadingOrderService, TaskListService taskListService) {
        this.loadingOrderService = loadingOrderService;
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<LoadingOrderDTO> loadingOrderDTOList = loadingOrderService.getAll().stream()
                .map(loadingOrderService::convertToLoadingOrderDTO).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders");
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        LoadingOrderDTO loadingOrderDTO = loadingOrderService.convertToLoadingOrderDTO(loadingOrderService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid LoadingOrderDTO loadingOrderDTO,
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
        LoadingOrder loadingOrder = loadingOrderService.create(loadingOrderService.convertToLoadingOrder(loadingOrderDTO));

        LoadingOrderDTO loadingOrderDTOResponse = loadingOrderService.convertToLoadingOrderDTO(loadingOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", loadingOrderDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid LoadingOrderDTO loadingOrderDTO,
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
        LoadingOrder updatedLoadingOrder = loadingOrderService.readById(id);

        if (loadingOrderDTO.getLoadingPoint() != null) {
            updatedLoadingOrder.setLoadingPoint(loadingOrderDTO.getLoadingPoint());
        }
        if (loadingOrderDTO.getPetroleumType() != null) {
            updatedLoadingOrder.setPetroleumType(loadingOrderDTO.getPetroleumType());
        }
        if (loadingOrderDTO.getCountOfVehicle() != 0) {
            updatedLoadingOrder.setCountOfVehicle(loadingOrderDTO.getCountOfVehicle());
        }
        if (loadingOrderDTO.getLoadingDateTime() != null) {
            updatedLoadingOrder.setLoadingDateTime(loadingOrderDTO.getLoadingDateTime());
        }
        if (loadingOrderDTO.getOrderStatus() != null) {
            updatedLoadingOrder.setOrderStatus(loadingOrderDTO.getOrderStatus());
        }
        if (loadingOrderDTO.getTaskListId() != null) {
            updatedLoadingOrder.setTaskList(taskListService.readById(loadingOrderDTO.getTaskListId()));
        }

        loadingOrderService.update(updatedLoadingOrder);

        LoadingOrderDTO loadingOrderDTOResponse = loadingOrderService.convertToLoadingOrderDTO(updatedLoadingOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        loadingOrderService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

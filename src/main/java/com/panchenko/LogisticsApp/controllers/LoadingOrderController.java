package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.LoadingOrderDTO;
import com.panchenko.LogisticsApp.dto.LoadingOrderResponse;
import com.panchenko.LogisticsApp.exception.LoadingOrderException.LoadingOrderNotCreatedException;
import com.panchenko.LogisticsApp.exception.LoadingOrderException.LoadingOrderNotUpdatedException;
import com.panchenko.LogisticsApp.model.LoadingOrder;
import com.panchenko.LogisticsApp.service.LoadingOrderService;
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

    public LoadingOrderController(LoadingOrderService loadingOrderService) {
        this.loadingOrderService = loadingOrderService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<LoadingOrderResponse> loadingOrderResponseList = loadingOrderService.getAll().stream()
                .map(LoadingOrderResponse::new).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders");
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        LoadingOrderResponse loadingOrderResponse = new LoadingOrderResponse(loadingOrderService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderResponse);
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
            throw new LoadingOrderNotCreatedException(errorMessage.toString());
        }
        LoadingOrder loadingOrder = loadingOrderService.create(loadingOrderService.convertToLoadingOrder(loadingOrderDTO));

        LoadingOrderResponse loadingOrderResponse = new LoadingOrderResponse(loadingOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", loadingOrderResponse);
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
            throw new LoadingOrderNotUpdatedException(errorMessage.toString());
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
        if (loadingOrderDTO.getTaskList() != null) {
            updatedLoadingOrder.setTaskList(loadingOrderDTO.getTaskList());
        }

        loadingOrderService.update(updatedLoadingOrder);

        LoadingOrderResponse loadingOrderResponse = new LoadingOrderResponse(updatedLoadingOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/loading-orders/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", loadingOrderResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        loadingOrderService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

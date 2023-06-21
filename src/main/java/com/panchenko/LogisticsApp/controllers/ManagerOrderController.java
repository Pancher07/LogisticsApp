package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.exception.ManagerOrderException.ManagerOrderNotCreatedException;
import com.panchenko.LogisticsApp.exception.ManagerOrderException.ManagerOrderNotUpdatedException;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.dto.ManagerOrderResponse;
import com.panchenko.LogisticsApp.repository.ManagerRepository;
import com.panchenko.LogisticsApp.service.ManagerOrderService;
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
@RequestMapping("/api/manager-orders")
public class ManagerOrderController {
    private final ManagerOrderService managerOrderService;
    private final ManagerRepository managerRepository;

    public ManagerOrderController(ManagerOrderService managerOrderService, ManagerRepository managerRepository) {
        this.managerOrderService = managerOrderService;
        this.managerRepository = managerRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ManagerOrderResponse> managerOrderResponseList = managerOrderService.getAll().stream()
                .map(ManagerOrderResponse::new).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders");
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{manager_id}")
    public ResponseEntity<?> getAllByManager(@PathVariable("manager_id") long managerId) {
        List<ManagerOrderResponse> managerOrderResponseList = managerOrderService.getByManagerId(managerId).stream()
                .map(ManagerOrderResponse::new).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{manager_id}/{order_id}")
    public ResponseEntity<?> getById(@PathVariable("manager_id") long managerId,
                                     @PathVariable("order_id") long orderId) {
        ManagerOrderResponse managerOrderResponse = new ManagerOrderResponse(managerOrderService.readById(orderId));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + orderId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderResponse);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{manager_id}")
    public ResponseEntity<?> create(@PathVariable("manager_id") long managerId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ManagerOrderNotCreatedException(errorMessage.toString());
        }
        ManagerOrder managerOrder = managerOrderService.create(managerOrderService.convertToManagerOrder(managerOrderDTO));
        managerOrder.setManager(managerRepository.findById(managerId));
        ManagerOrderResponse managerOrderResponse = new ManagerOrderResponse(managerOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId);
        map.put("status code", HttpStatus.CREATED);
        map.put("body", managerOrderResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{manager_id}/{order_id}")
    public ResponseEntity<?> update(@PathVariable("manager_id") long managerId, @PathVariable("order_id") long orderId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ManagerOrderNotUpdatedException(errorMessage.toString());
        }
        ManagerOrder updatedManagerOrder = managerOrderService.readById(orderId);

        if (managerOrderDTO.getTypeOfLightProduct() != null) {
            updatedManagerOrder.setTypeOfLightProduct(managerOrderDTO.getTypeOfLightProduct());
        }
        if (managerOrderDTO.getVolume() != 0) {
            updatedManagerOrder.setVolume(managerOrderDTO.getVolume());
        }
        if (managerOrderDTO.getPump() != null) {
            updatedManagerOrder.setPump(managerOrderDTO.getPump());
        }
        if (managerOrderDTO.getCalibration() != null) {
            updatedManagerOrder.setCalibration(managerOrderDTO.getCalibration());
        }
        if (managerOrderDTO.getContact() != null) {
            updatedManagerOrder.setContact(managerOrderDTO.getContact());
        }
        if (managerOrderDTO.getUploadingDateTime() != null) {
            updatedManagerOrder.setUploadingDateTime(managerOrderDTO.getUploadingDateTime());
        }
        if (managerOrderDTO.getOrderStatus() != null) {
            updatedManagerOrder.setOrderStatus(managerOrderDTO.getOrderStatus());
        }
        if (managerOrderDTO.getContractor() != null) {
            updatedManagerOrder.setContractor(managerOrderDTO.getContractor());
        }
        if (managerOrderDTO.getTaskList() != null) {
            updatedManagerOrder.setTaskList(managerOrderDTO.getTaskList());
        }
        if (managerOrderDTO.getHitch() != null) {
            updatedManagerOrder.setHitch(managerOrderDTO.getHitch());
        }

        managerOrderService.update(updatedManagerOrder);

        ManagerOrderResponse managerOrderResponse = new ManagerOrderResponse(updatedManagerOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId + "/" + orderId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{manager_id}/{order_id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("order_id") long orderId) {
        managerOrderService.delete(orderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

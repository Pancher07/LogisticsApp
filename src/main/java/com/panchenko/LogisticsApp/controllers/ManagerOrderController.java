package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.service.ManagerOrderService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manager-orders")
public class ManagerOrderController {
    private final ManagerOrderService managerOrderService;

    public ManagerOrderController(ManagerOrderService managerOrderService) {
        this.managerOrderService = managerOrderService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ManagerOrderDTO> managerOrderDTOList = managerOrderService.getAll().stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders");
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{manager_id}")
    public ResponseEntity<?> getAllByManager(@PathVariable("manager_id") long managerId) {
        List<ManagerOrderDTO> managerOrderDTOList = managerOrderService.getByManagerId(managerId).stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{manager_id}/{order_id}")
    public ResponseEntity<?> getById(@PathVariable("manager_id") long managerId,
                                     @PathVariable("order_id") long orderId) {
        ManagerOrderDTO managerOrderDTO = managerOrderService
                .convertToManagerOrderDTO(managerOrderService.readById(orderId));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + orderId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{manager_id}")
    public ResponseEntity<?> create(@PathVariable("manager_id") long managerId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        ManagerOrder managerOrder = managerOrderService
                .create(managerOrderService.convertToManagerOrder(managerOrderDTO), managerId);

        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(managerOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId);
        map.put("status code", HttpStatus.CREATED);
        map.put("body", managerOrderDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{manager_id}/{order_id}")
    public ResponseEntity<?> update(@PathVariable("manager_id") long managerId, @PathVariable("order_id") long orderId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        ManagerOrder updatedManagerOrder = managerOrderService.readById(orderId);

        managerOrderService.update(updatedManagerOrder, managerOrderDTO);

        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(updatedManagerOrder);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/manager-orders/" + managerId + "/" + orderId);
        map.put("status code", HttpStatus.OK);
        map.put("body", managerOrderDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{manager_id}/{order_id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("order_id") long orderId) {
        managerOrderService.delete(orderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

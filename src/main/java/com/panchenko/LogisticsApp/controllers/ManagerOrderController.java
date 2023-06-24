package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.service.*;
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
    private final ManagerService managerService;
    private final ContractorService contractorService;
    private final TaskListService taskListService;
    private final HitchService hitchService;

    public ManagerOrderController(ManagerOrderService managerOrderService, ManagerService managerService,
                                  ContractorService contractorService, TaskListService taskListService,
                                  HitchService hitchService) {
        this.managerOrderService = managerOrderService;
        this.managerService = managerService;
        this.contractorService = contractorService;
        this.taskListService = taskListService;
        this.hitchService = hitchService;
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
        ManagerOrder managerOrder = managerOrderService.create(managerOrderService.convertToManagerOrder(managerOrderDTO));
        managerOrder.setManager(managerService.readById(managerId));
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
        if (managerOrderDTO.getContractorId() != null) {
            updatedManagerOrder.setContractor(contractorService.readById(managerOrderDTO.getContractorId()));
        }
        if (managerOrderDTO.getTaskListId() != null) {
            updatedManagerOrder.setTaskList(taskListService.readById(managerOrderDTO.getTaskListId()));
        }
        if (managerOrderDTO.getHitchId() != null) {
            updatedManagerOrder.setHitch(hitchService.readById(managerOrderDTO.getHitchId()));
        }

        managerOrderService.update(updatedManagerOrder);

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

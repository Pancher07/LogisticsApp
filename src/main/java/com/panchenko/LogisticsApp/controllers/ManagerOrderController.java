package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.dto.SelectNextDTO;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;
import com.panchenko.LogisticsApp.service.HitchService;
import com.panchenko.LogisticsApp.service.ManagerOrderService;
import com.panchenko.LogisticsApp.service.ManagerService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager-orders")
public class ManagerOrderController {
    private final ManagerOrderService managerOrderService;
    private final HitchService hitchService;
    private final ManagerService managerService;

    public ManagerOrderController(ManagerOrderService managerOrderService, HitchService hitchService,
                                  ManagerService managerService) {
        this.managerOrderService = managerOrderService;
        this.hitchService = hitchService;
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ManagerOrderDTO> managerOrderDTOList = managerOrderService.getAll().stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();
        return ResponseEntity.ok(managerOrderDTOList);
    }

    @GetMapping("/new")
    public ResponseEntity<?> getAllNewManagersOrders() {
        List<ManagerOrderDTO> newManagerOrdersDTOList = managerOrderService
                .getManagerOrdersByStatus(TaskListAndOrderStatus.NEW)
                .stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();
        return ResponseEntity.ok(newManagerOrdersDTOList);
    }

    @GetMapping("/manager/{manager-id}")
    public ResponseEntity<?> getAllByManager(@PathVariable("manager-id") long managerId) {
        List<ManagerOrderDTO> managerOrderDTOList = managerOrderService
                .getByManager(managerService.readById(managerId)).stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();
        return ResponseEntity.ok(managerOrderDTOList);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<?> getById(@PathVariable("order-id") long orderId) {
        ManagerOrderDTO managerOrderDTO = managerOrderService
                .convertToManagerOrderDTO(managerOrderService.readById(orderId));
        return ResponseEntity.ok(managerOrderDTO);
    }

    @PostMapping("/manager/{manager-id}")
    public ResponseEntity<?> create(@PathVariable("manager-id") long managerId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        ManagerOrder managerOrder = managerOrderService
                .create(managerOrderService.convertToManagerOrder(managerOrderDTO), managerId);
        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(managerOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }

    @PutMapping("/{order-id}")
    public ResponseEntity<?> update(@PathVariable("order-id") long orderId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        ManagerOrder updatedManagerOrder = managerOrderService.readById(orderId);
        managerOrderService.update(updatedManagerOrder, managerOrderDTO);
        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(updatedManagerOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("order-id") long orderId) {
        managerOrderService.delete(orderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{manager-order-id}/select-hitch")
    public ResponseEntity<?> selectHitch(@PathVariable("manager-order-id") long managerOrderId) {
        Hitch selectedHitch = managerOrderService
                .selectHitch(managerOrderService.readById(managerOrderId));
        HitchDTO selectedHitchDTOResponse = hitchService.convertToHitchDTO(selectedHitch);
        return ResponseEntity.ok(selectedHitchDTOResponse);
    }

    @GetMapping("/{manager-order-id}/select-hitch/next")
    public ResponseEntity<?> selectNextHitch(@PathVariable("manager-order-id") long managerOrderId,
                                             @RequestBody List<Long> skippedHitchesId,
                                             @RequestParam("skipped-hitch") long skippedHitchId) {
        SelectNextDTO selectNextDTO = managerOrderService
                .selectNextHitch(managerOrderService.readById(managerOrderId),
                        skippedHitchesId, hitchService.readById(skippedHitchId));

        return ResponseEntity.ok(selectNextDTO);
    }

    @PutMapping("/{manager-order-id}/approve-hitch")
    public ResponseEntity<?> approveHitch(@PathVariable("manager-order-id") long managerOrderId,
                                          @RequestParam("hitch-id") long hitchId) {
        ManagerOrder managerOrder = managerOrderService.readById(managerOrderId);
        ManagerOrder updatedOrder = managerOrderService.
                approveHitch(managerOrder, hitchService.readById(hitchId));

        ManagerOrderDTO managerOrderDTOResponse = managerOrderService
                .convertToManagerOrderDTO(updatedOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }

    @GetMapping("/{manager-order-id}/delete-hitch")
    public ResponseEntity<?> deleteHitch(@PathVariable("manager-order-id") long managerOrderId,
                                         @RequestParam("hitch-id") long hitchId) {
        ManagerOrder managerOrder = managerOrderService.readById(managerOrderId);
        ManagerOrder updatedOrder = managerOrderService.
                deleteHitch(managerOrder, hitchService.readById(hitchId));

        ManagerOrderDTO managerOrderDTOResponse = managerOrderService
                .convertToManagerOrderDTO(updatedOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }
}

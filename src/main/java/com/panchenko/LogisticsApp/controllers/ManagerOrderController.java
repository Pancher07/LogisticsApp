package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.dto.ManagerDTO;
import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
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
                .getManagerOrdersByStatus(TaskListAndOrderStatus.OPENED)
                .stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();
        return ResponseEntity.ok(newManagerOrdersDTOList);
    }

    @GetMapping("/manager")
    public ResponseEntity<?> getAllByManager(@RequestBody @Valid ManagerDTO managerDTO) {
        List<ManagerOrderDTO> managerOrderDTOList = managerOrderService
                .getByManager(managerService.convertToManager(managerDTO)).stream()
                .map(managerOrderService::convertToManagerOrderDTO).toList();
        return ResponseEntity.ok(managerOrderDTOList);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> getById(@PathVariable("order_id") long orderId) {
        ManagerOrderDTO managerOrderDTO = managerOrderService
                .convertToManagerOrderDTO(managerOrderService.readById(orderId));
        return ResponseEntity.ok(managerOrderDTO);
    }

    @PostMapping("/manager/{manager_id}")
    public ResponseEntity<?> create(@PathVariable("manager_id") long managerId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        ManagerOrder managerOrder = managerOrderService
                .create(managerOrderService.convertToManagerOrder(managerOrderDTO), managerId);
        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(managerOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<?> update(@PathVariable("manager_id") long managerId, @PathVariable("order_id") long orderId,
                                    @RequestBody @Valid ManagerOrderDTO managerOrderDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        ManagerOrder updatedManagerOrder = managerOrderService.readById(orderId);
        managerOrderService.update(updatedManagerOrder, managerOrderDTO);
        ManagerOrderDTO managerOrderDTOResponse = managerOrderService.convertToManagerOrderDTO(updatedManagerOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("order_id") long orderId) {
        managerOrderService.delete(orderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/select-hitch")
    public ResponseEntity<?> selectHitch(@RequestBody ManagerOrderDTO managerOrderDTO) {
        Hitch selectedHitch = managerOrderService
                .selectHitch(managerOrderService.convertToManagerOrder(managerOrderDTO));
        HitchDTO selectedHitchDTOResponse = hitchService.convertToHitchDTO(selectedHitch);
        return ResponseEntity.ok(selectedHitchDTOResponse);
    }

    @PutMapping("/approve-hitch")
    public ResponseEntity<?> approveHitch(@RequestBody ManagerOrderDTO managerOrderDTO) {
        ManagerOrder updatedOrder = managerOrderService.
                approveHitch(managerOrderService.convertToManagerOrder(managerOrderDTO).getId(),
                        hitchService.readById(managerOrderDTO.getHitchId()));

        ManagerOrderDTO managerOrderDTOResponse = managerOrderService
                .convertToManagerOrderDTO(updatedOrder);
        return ResponseEntity.ok(managerOrderDTOResponse);
    }


      /*@PostMapping("/select-hitch/next")
    public ResponseEntity<?> selectNextHitch(@RequestBody ManagerOrderDTO managerOrderDTO) {

        Hitch selectedHitch = managerOrderService
                .selectNextHitch(managerOrderService.convertToManagerOrder(managerOrderDTO),
                        );

        HitchDTO selectedHitchDTOResponse = hitchService.convertToHitchDTO(selectedHitch);

        return ResponseEntity.ok(selectedHitchDTOResponse);
    }*/
}

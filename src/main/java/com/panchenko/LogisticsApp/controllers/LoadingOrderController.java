package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.LoadingOrderDTO;
import com.panchenko.LogisticsApp.model.LoadingOrder;
import com.panchenko.LogisticsApp.service.LoadingOrderService;
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
@RequestMapping("/api/loading-orders")
public class LoadingOrderController {
    private final LoadingOrderService loadingOrderService;

    public LoadingOrderController(LoadingOrderService loadingOrderService) {
        this.loadingOrderService = loadingOrderService;
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
        CheckErrors.checkErrorsForCreate(bindingResult);

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
        CheckErrors.checkErrorsForUpdate(bindingResult);

        LoadingOrder updatedLoadingOrder = loadingOrderService.readById(id);

        loadingOrderService.update(updatedLoadingOrder, loadingOrderDTO);

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

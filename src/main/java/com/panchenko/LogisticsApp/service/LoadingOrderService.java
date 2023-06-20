package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.LoadingOrderDTO;
import com.panchenko.LogisticsApp.model.LoadingOrder;

import java.util.List;

public interface LoadingOrderService {
    LoadingOrder create(LoadingOrder loadingOrder);

    LoadingOrder readById(long id);

    LoadingOrder update(LoadingOrder loadingOrder);

    void delete(long id);

    List<LoadingOrder> getAll();

    LoadingOrder convertToLoadingOrder(LoadingOrderDTO loadingOrderDTO);

    LoadingOrderDTO convertToLoadingOrderDTO(LoadingOrder loadingOrder);
}

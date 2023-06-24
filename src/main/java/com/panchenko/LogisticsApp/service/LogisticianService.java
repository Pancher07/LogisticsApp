package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.model.Logistician;

import java.util.List;

public interface LogisticianService {
    Logistician readById(long id);
    List<Logistician> getAll();
}

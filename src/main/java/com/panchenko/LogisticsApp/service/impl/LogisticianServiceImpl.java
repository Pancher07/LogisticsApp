package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.model.Logistician;
import com.panchenko.LogisticsApp.repository.LogisticianRepository;
import com.panchenko.LogisticsApp.service.LogisticianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LogisticianServiceImpl implements LogisticianService {
    private final LogisticianRepository logisticianRepository;

    public LogisticianServiceImpl(LogisticianRepository logisticianRepository) {
        this.logisticianRepository = logisticianRepository;
    }

    @Override
    public Logistician readById(long id) {
        return logisticianRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Logistician with id " + id + " not found"));
    }

    @Override
    public List<Logistician> getAll() {
        return logisticianRepository.findAll();
    }
}

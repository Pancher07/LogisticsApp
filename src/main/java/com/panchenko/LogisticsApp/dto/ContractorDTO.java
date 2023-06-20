package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.Manager;
import lombok.Data;

@Data
public class ContractorDTO {
    private String name;
    private Manager manager;
}

package com.panchenko.LogisticsApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContractorDTO {
    //@NotEmpty(message = "поле не може бути пустим")
    private String name;
    private Long managerId;
}

package com.panchenko.LogisticsApp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.panchenko.LogisticsApp.model.Contractor;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContractorResponse {
    String name;
    long managerId;

    public ContractorResponse(Contractor contractor) {
        this.name = contractor.getName();
        this.managerId = contractor.getManager().getId();
    }
}

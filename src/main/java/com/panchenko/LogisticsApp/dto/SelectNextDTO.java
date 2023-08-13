package com.panchenko.LogisticsApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectNextDTO {
    Long selectedHitchId;
    List<Long> skippedHitchesId;
    String message;

    public SelectNextDTO(Long selectedHitchId, List<Long> skippedHitchesId, String message) {
        this.selectedHitchId = selectedHitchId;
        this.skippedHitchesId = skippedHitchesId;
        this.message = message;
    }
}

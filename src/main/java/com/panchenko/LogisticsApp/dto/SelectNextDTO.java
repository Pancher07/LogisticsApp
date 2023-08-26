package com.panchenko.LogisticsApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectNextDTO {
    Long selectedHitchId;
    List<Long> skippedHitches;
    String message;

    public SelectNextDTO(Long selectedHitchId, List<Long> skippedHitches, String message) {
        this.selectedHitchId = selectedHitchId;
        this.skippedHitches = skippedHitches;
        this.message = message;
    }
}

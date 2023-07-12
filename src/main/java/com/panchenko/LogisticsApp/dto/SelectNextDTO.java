package com.panchenko.LogisticsApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectNextDTO {
    Long selectedHitchId;
    List<Long> skippedHitchesId;

    public SelectNextDTO(Long selectedHitchId, List<Long> skippedHitchesId) {
        this.selectedHitchId = selectedHitchId;
        this.skippedHitchesId = skippedHitchesId;
    }
}

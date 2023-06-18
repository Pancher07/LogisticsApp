package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import com.panchenko.LogisticsApp.model.enumeration.ProjectCountry;
import lombok.Data;

@Data
public class ProjectDTO {

    private PetroleumType petroleumType;

    private ProjectCountry projectCountry;
}

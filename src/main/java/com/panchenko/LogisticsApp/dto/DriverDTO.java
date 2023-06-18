package com.panchenko.LogisticsApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverDTO {

    private String name;

    private String middleName;

    private String surname;

    private String phone;

    private LocalDateTime lastTimeWorked;
}

package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trailers")
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "calibration", nullable = false)
    private PresenceOfPumpOrCalibration calibration;

    @Column(name = "petroleum_type", nullable = false)
    private PetroleumType petroleumType;

    @OneToOne
    @JoinColumn(name = "hitch_id", referencedColumnName = "id")
    private Hitch hitch;
}

package com.panchenko.LogisticsApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "truck_tractors")
public class TruckTractor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "pump", nullable = false)
    private boolean pump;

    @OneToOne
    @JoinColumn(name = "hitch_id", referencedColumnName = "id")
    private Hitch hitch;

}

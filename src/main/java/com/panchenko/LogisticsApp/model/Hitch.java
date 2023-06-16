package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hitches")
public class Hitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "comment")
    private String comment;

    @Column(name = "vehicle_status", nullable = false)
    private VehicleStatus vehicleStatus;

    @OneToOne(mappedBy = "hitch")
    private TruckTractor truckTractor;

    @OneToOne(mappedBy = "hitch")
    private Trailer trailer;

    @OneToOne(mappedBy = "hitch")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "logistician_id", referencedColumnName = "id")
    private Logistician logistician;

    @OneToOne(mappedBy = "hitch")
    private ManagerOrder managerOrder;

    @ManyToOne
    @JoinColumn(name = "loading_order_id", referencedColumnName = "id")
    private LoadingOrder loadingOrder;
}

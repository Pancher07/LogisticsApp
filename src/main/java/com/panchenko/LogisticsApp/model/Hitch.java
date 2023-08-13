package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "loaded_with_product")
    private TypeOfLightProduct loadedWithProduct;

    @OneToOne
    @JoinColumn(name = "truckTractor_id", referencedColumnName = "id", nullable = false)
    private TruckTractor truckTractor;

    @OneToOne
    @JoinColumn(name = "trailer_id", referencedColumnName = "id")
    private Trailer trailer;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "logistician_id", referencedColumnName = "id")
    private Logistician logistician;

    @OneToOne(mappedBy = "hitch", cascade = CascadeType.ALL)
    private ManagerOrder managerOrder;

    @ManyToMany(mappedBy = "hitches")
    private List<LoadingOrder> loadingOrders;
}

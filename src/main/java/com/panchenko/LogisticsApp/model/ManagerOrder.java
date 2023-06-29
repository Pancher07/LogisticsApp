package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manager_orders")
public class ManagerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_product", nullable = false)
    private TypeOfLightProduct typeOfLightProduct;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "pump", nullable = false)
    private PresenceOfPumpOrCalibration pump;

    @Column(name = "calibration", nullable = false)
    private PresenceOfPumpOrCalibration calibration;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "uploading_date_time", nullable = false)
    private LocalDateTime uploadingDateTime;

    @Column(name = "status", nullable = false)
    private TaskListAndOrderStatus orderStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "task_list_id", referencedColumnName = "id")
    private TaskList taskList;

    @OneToOne
    @JoinColumn(name = "hitch_id", referencedColumnName = "id")
    private Hitch hitch;
}

package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enums.OrderStatus;
import com.panchenko.LogisticsApp.model.enums.PetroleumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loading_orders")
public class LoadingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loading_point", nullable = false)
    private String loadingPoint;

    @Column(name = "petroleum_type", nullable = false)
    private PetroleumType petroleumType;

    @Column(name = "count_of_vehicle", nullable = false)
    private int countOfVehicle;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "loading_date_time", nullable = false)
    private LocalDateTime loadingDateTime;
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "tasks_list_id", referencedColumnName = "id")
    private TasksList tasksList;

    @OneToMany(mappedBy = "loadingOrder")
    private List<Hitch> hitches;
}

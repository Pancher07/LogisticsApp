package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, max = 30, message = "поле не може бути пустим")
    //@NotNull(message = "поле не може бути пустим")
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
    @JoinColumn(name = "task_list_id", referencedColumnName = "id")
    private TaskList taskList;

    @ManyToMany
    @JoinTable(
            name = "loading_orders_hitches",
            joinColumns = @JoinColumn(name = "loading_order_id"),
            inverseJoinColumns = @JoinColumn(name = "hitch_id"))
    private List<Hitch> hitches;
}

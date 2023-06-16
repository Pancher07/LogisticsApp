package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.TasksListStatus;
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
@Table(name = "tasks_lists")
public class TasksList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private TasksListStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "logistician_id", referencedColumnName = "id")
    private Logistician logistician;

    @OneToMany(mappedBy = "tasksList")
    private List<LoadingOrder> loadingOrders;

    @OneToMany(mappedBy = "tasksList")
    private List<ManagerOrder> managerOrders;
}

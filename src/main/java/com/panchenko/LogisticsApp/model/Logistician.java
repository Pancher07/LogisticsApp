package com.panchenko.LogisticsApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logisticians")
public class Logistician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "head_logistician", nullable = false)
    private boolean headLogistician;

    @OneToMany(mappedBy = "logistician")
    private List<Hitch> hitches;

    @OneToMany(mappedBy = "logistician")
    private List<TasksList> tasksLists;
}

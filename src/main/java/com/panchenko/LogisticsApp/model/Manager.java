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
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "managers")
    private List<Project> projects;

    @OneToMany(mappedBy = "manager")
    private List<Contractor> contractors;

    @OneToMany(mappedBy = "manager")
    private List<ManagerOrder> managerOrders;
}

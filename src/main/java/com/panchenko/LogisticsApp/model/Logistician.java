package com.panchenko.LogisticsApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "logisticians")
public class Logistician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "logistician")
    private List<Hitch> hitches;

    @OneToMany(mappedBy = "logistician")
    private List<TasksList> tasksLists;

    public Logistician(User user) {
        this.user = user;
    }
}

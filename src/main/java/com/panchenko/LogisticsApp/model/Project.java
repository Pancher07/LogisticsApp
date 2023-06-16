package com.panchenko.LogisticsApp.model;

import com.panchenko.LogisticsApp.model.enumeration.ProjectCountry;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_type", nullable = false)
    private PetroleumType petroleumType;

    @Column(name = "project_country", nullable = false)
    private ProjectCountry projectCountry;

    @ManyToMany
    @JoinTable(name = "project_manager",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id"))
    private List<Manager> managers;

    @OneToMany(mappedBy = "project")
    private List<Hitch> hitches;
}

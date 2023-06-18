package com.panchenko.LogisticsApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "last_time_worked")
    private LocalDateTime lastTimeWorked;

    @OneToOne
    @JoinColumn(name = "hitch_id", referencedColumnName = "id")
    private Hitch hitch;
}

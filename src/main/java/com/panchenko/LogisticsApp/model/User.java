package com.panchenko.LogisticsApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* @Pattern(regexp = "[A-Z][a-z]+",
             message = "Must start with a capital letter followed by one or more lowercase letters")*/
    @Column(name = "name", nullable = false)
    //@NotEmpty(message = "Поле не може бути пустим, впишіть Ваше ім'я")
    @Size(min = 2, max = 30, message = "Ім'я повино містити 2 - 30 символів")
    private String name;

    @Column(name = "surname", nullable = false)
    //@NotEmpty(message = "Поле не може бути пустим, впишіть Ваше прізвище")
    @Size(min = 2, max = 30, message = "Прізвище має містити 2 - 30 символів")
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    //@NotEmpty(message = "Поле не може бути пустим, впишіть Вам Email")
    private String email;

    @Column(name = "phone", nullable = false)
    //@NotEmpty(message = "Поле не може бути пустим, впишіть Вам номер телефону")
    private String phone;

    @Column(name = "login", nullable = false, unique = true)
    //@NotEmpty(message = "Поле не може бути пустим, впишіть Ваш логін")
    private String login;

    @Column(name = "password", nullable = false)
    //@NotEmpty(message = "Поле не може бути пустим, напишіть Ваш пароль")
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "user_active", nullable = false)
    private boolean userActive;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Logistician logistician;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Manager manager;

}

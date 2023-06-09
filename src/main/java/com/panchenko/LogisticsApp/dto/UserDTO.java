package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "Поле не може бути пустим, впишіть Ваше ім'я")
    @Size(min = 2, max = 30, message = "Ім'я повино містити 2 - 30 символів")
    private String name;

    @NotEmpty(message = "Поле не може бути пустим, впишіть Ваше прізвище")
    @Size(min = 2, max = 30, message = "Прізвище має містити 2 - 30 символів")
    private String surname;

    @Email
    @NotEmpty(message = "Поле не може бути пустим, впишіть Вам Email")
    private String email;

    @NotEmpty(message = "Поле не може бути пустим, впишіть Вам номер телефону")
    private String phone;

    @NotEmpty(message = "Поле не може бути пустим, впишіть Ваш логін")
    private String login;

    @NotEmpty(message = "Поле не може бути пустим, напишіть Ваш пароль")
    private String password;

    private Role role;
}

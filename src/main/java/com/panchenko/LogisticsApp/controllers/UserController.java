package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.UserDTO;
import com.panchenko.LogisticsApp.exception.UserException.UserNotCreatedException;
import com.panchenko.LogisticsApp.exception.UserException.UserNotUpdatedException;
import com.panchenko.LogisticsApp.model.User;
import com.panchenko.LogisticsApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(this.userService::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable long id) {
        return userService.convertToUserDTO(userService.readById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMessage.toString());
        }
        userService.create(userService.convertToUser(userDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotUpdatedException(errorMessage.toString());
        }
        User updatedUser = userService.readById(id);

        if (userDTO.getName() != null) {
            updatedUser.setName(userDTO.getName());
        }
        if (userDTO.getSurname() != null) {
            updatedUser.setSurname(userDTO.getSurname());
        }
        if (userDTO.getEmail() != null) {
            updatedUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null) {
            updatedUser.setPhone(userDTO.getPhone());
        }
        if (userDTO.getLogin() != null) {
            updatedUser.setLogin(userDTO.getLogin());
        }
        if (userDTO.getPassword() != null) {
            updatedUser.setPassword(userDTO.getPassword());
        }

        userService.update(updatedUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

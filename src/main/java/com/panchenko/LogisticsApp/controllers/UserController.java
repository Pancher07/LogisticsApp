package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.UserDTO;
import com.panchenko.LogisticsApp.model.User;
import com.panchenko.LogisticsApp.service.UserService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserDTO> userDTOList = userService.getAll().stream()
                .map(userService::convertToUserDTO).toList();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        UserDTO userDTO = userService.convertToUserDTO(userService.readById(id));
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        User user = userService.create(userService.convertToUser(userDTO));
        UserDTO userDTOResponse = userService.convertToUserDTO(user);
        return ResponseEntity.ok(userDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid UserDTO userDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        User updatedUser = userService.readById(id);
        userService.update(updatedUser, userDTO);
        UserDTO userDTOResponse = userService.convertToUserDTO(updatedUser);
        return ResponseEntity.ok(userDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

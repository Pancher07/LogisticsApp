package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.UserDTO;
import com.panchenko.LogisticsApp.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User readById(long id);

    User update(User user, UserDTO userDTO);

    void delete(long id);

    List<User> getAll();

    User convertToUser(UserDTO userDTO);

    UserDTO convertToUserDTO(User user);
}

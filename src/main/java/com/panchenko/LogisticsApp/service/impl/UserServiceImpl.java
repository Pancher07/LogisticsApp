package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.UserDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Logistician;
import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.User;
import com.panchenko.LogisticsApp.repository.LogisticianRepository;
import com.panchenko.LogisticsApp.repository.ManagerRepository;
import com.panchenko.LogisticsApp.repository.UserRepository;
import com.panchenko.LogisticsApp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LogisticianRepository logisticianRepository;
    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LogisticianRepository logisticianRepository,
                           ManagerRepository managerRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.logisticianRepository = logisticianRepository;
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User create(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        enrichUser(user);
        User savedUser = userRepository.save(user);
        switch (user.getRole()) {
            case ROLE_LOGISTICIAN, ROLE_LEAD_LOGISTICIAN -> logisticianRepository.save(new Logistician(savedUser));
            case ROLE_MANAGER -> managerRepository.save(new Manager(savedUser));
        }
        return savedUser;
    }

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private void enrichUser(User user) {
        user.setUserActive(true);
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        readById(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}

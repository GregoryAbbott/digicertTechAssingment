package com.digicert.tech.assesment.userservice.services;

import com.digicert.tech.assesment.userservice.entity.User;
import com.digicert.tech.assesment.userservice.repository.UserRepository;
import com.digicert.tech.assesment.userservice.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(u -> modelMapper.map(u, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);

        /* if we would like to change the return type to UserResponse, then:
           return modelMapper.map(userRepository.save(user), UserRepository.class);
         */
    }

    @Override
    public boolean updateUser(long id, User user) {
        boolean userFound = false;

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userFound = true;
            User userToUpdate = optionalUser.get();

            //using if statements to check if we must update each field
            if (!userToUpdate.getFirstName().equals(user.getFirstName())) {
                userToUpdate.setFirstName(user.getFirstName());
            }
            if (!userToUpdate.getLastName().equals(user.getLastName())) {
                userToUpdate.setLastName(user.getLastName());
            }
            if (!userToUpdate.getEmail().equals(user.getEmail())) {
                userToUpdate.setEmail(user.getEmail());
            }

            //save the new user
            userRepository.save(userToUpdate);
        }
        return userFound;
    }

    @Override
    public boolean deleteUser(long id) {
        boolean deletedUser = false;

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            deletedUser = true;
        }
        return deletedUser;
    }
}

package com.digicert.tech.assesment.userservice.services;

import com.digicert.tech.assesment.userservice.entity.User;
import com.digicert.tech.assesment.userservice.response.UserResponse;

import java.util.List;

public interface UserService {

    //interface for the service so that multiple implementations can be possible
    List<UserResponse> getAllUsers();

    UserResponse getUserById(long id);

    void addUser(User user);

    boolean updateUser(long id, User user);

    boolean deleteUser(long id);

}

package com.digicert.tech.assesment.userservice.configuration;

import com.digicert.tech.assesment.userservice.repository.UserRepository;
import com.digicert.tech.assesment.userservice.services.UserService;
import com.digicert.tech.assesment.userservice.services.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    /* we can also configure the beans through xml if we want */

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private UserServiceImpl userService;

    @Bean
    public UserService userServiceBean() {
        //change the bean with different implementations of UserService
        userService = new UserServiceImpl(userRepository, modelMapper);
        return userService;
    }

}

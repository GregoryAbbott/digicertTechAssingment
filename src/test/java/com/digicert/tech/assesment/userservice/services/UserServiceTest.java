package com.digicert.tech.assesment.userservice.services;

import com.digicert.tech.assesment.userservice.entity.User;
import com.digicert.tech.assesment.userservice.repository.UserRepository;
import com.digicert.tech.assesment.userservice.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserService userService;

    @BeforeEach
    void setup() {
        this.userService = new UserServiceImpl(this.userRepository, this.modelMapper);
    }

    @Test
    void getAllUsers() {
        userService.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void getUserById() {
        UserResponse userResponse = UserResponse.builder()
                        .id(1L)
                        .firstName("Gregory")
                        .lastName("Abbott")
                        .email("gregoryabbott90@gmail.com")
                        .build();

        Mockito.when(userService.getUserById(1L)).thenReturn(userResponse);
        UserResponse test = userService.getUserById(1L);
        Assertions.assertEquals(userResponse, test);
    }

    @Test
    void addUser() {

        User user = User.builder()
                        .firstName("Gregory")
                        .lastName("Abbott")
                        .email("gregoryabbott90@gmail.com")
                        .build();

        userService.addUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser() {

        User user = User.builder()
                .firstName("Gregory")
                .lastName("Abbott")
                .email("gregoryabbott90@gmail.com")
                .build();

        userService.addUser(user);

        user.setFirstName("Test1");
        user.setLastName("Test1");
        user.setEmail("test@test.com");

        userService.updateUser(1, user);
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser() {

        User user = User.builder()
                .firstName("Gregory")
                .lastName("Abbott")
                .email("gregoryabbott90@gmail.com")
                .build();

        userService.addUser(user);

        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}

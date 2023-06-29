package com.digicert.tech.assesment.userservice.controller;

import com.digicert.tech.assesment.userservice.entity.User;
import com.digicert.tech.assesment.userservice.repository.UserRepository;
import com.digicert.tech.assesment.userservice.response.UserResponse;
import com.digicert.tech.assesment.userservice.services.UserService;
import com.digicert.tech.assesment.userservice.services.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest extends AbstractTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    UserService userService;


    @BeforeEach
    void setup() throws Exception {
        userService = new UserServiceImpl(this.userRepository, this.modelMapper);

        User user = User.builder()
                .id(1L)
                .firstName("Gregory")
                .lastName("Abbott")
                .email("gregoryabbott90@gmail.com")
                .build();

        userService.addUser(user);
    }

    @AfterEach
    void cleanUp() throws Exception {
        userService.deleteUser(1L);
    }

    @Test
    public void getAllUsers() throws Exception {
        String uri = "/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        UserResponse[] userResponses = super.mapFromJson(content, UserResponse[].class);
        Assertions.assertTrue(userResponses.length > 0);
    }

    @Test
    public void getUserById() throws Exception {
        String uri = "/users/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        UserResponse userResponse = super.mapFromJson(content, UserResponse.class);
        Assert.assertNotNull(userResponse);
        Assert.assertEquals(1L, userResponse.getId());
        Assert.assertEquals("Gregory", userResponse.getFirstName());
        Assert.assertEquals("Abbott", userResponse.getLastName());
        Assert.assertEquals("gregoryabbott90@gmail.com", userResponse.getEmail());
    }

    @Test
    public void addUser() throws Exception {
        String uri = "/users";

        User user = User.builder()
                .firstName("Gregory2")
                .lastName("Abbott2")
                .email("gregoryabbott902@gmail.com")
                .build();

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.CREATED.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "User created");
    }

    @Test
    public void updateUserFound() throws Exception {
        String uri = "/users/1";

        User user = User.builder()
                .firstName("Gregory-Change")
                .lastName("Abbott-Change")
                .build();

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("User updated", content);

    }

    @Test
    public void updateNotFound() throws Exception {
        String uri = "/users/2";

        User user = User.builder()
                .firstName("Gregory")
                .lastName("Abbott")
                .build();

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("User not found", content);
    }

    @Test
    public void deleteUser() throws Exception {
        String uri = "/users/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "User deleted");
    }

}

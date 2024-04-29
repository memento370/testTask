package com.example.TestTask;

import com.example.TestTask.Entity.UserEntity;
import com.example.TestTask.Exceptions.UserAlreadyExistException;
import com.example.TestTask.Exceptions.UserInvalidException;
import com.example.TestTask.Exceptions.UserNotFoundException;
import com.example.TestTask.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser_ValidUser_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setFirstName("name");
        user.setLastName("lastName");
        user.setMiddleName("middleName");
        user.setEmail("test@test.test");
        user.setAddress("address");
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));

        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUser_UserFound_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);


        when(userService.getUser(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUser_UserNotFound_Error() throws Exception {
        when(userService.getUser(1L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testUpdateFirstName_ValidUser_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test1");

        when(userService.updateFirstName(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/firstName")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateFirstName_UserNotFound_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test1");

        when(userService.updateFirstName(any())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/firstName")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateFirstName_InvalidUser_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName(null);

        when(userService.updateFirstName(any())).thenThrow(new UserInvalidException("Invalid user"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/firstName")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateFullName_ValidUser_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test3");
        user.setMiddleName("test3");
        user.setLastName("test3");

        when(userService.updateFullName(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/fullname")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateFullName_UserNotFound_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test3");
        user.setMiddleName("test3");
        user.setLastName("test3");

        when(userService.updateFullName(any())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/fullname")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateFullName_InvalidUser_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test3");
        user.setMiddleName(null);
        user.setLastName("test3");

        when(userService.updateFullName(any())).thenThrow(new UserInvalidException("Invalid user"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/fullname")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateAll_ValidUser_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test4");
        user.setMiddleName("test4");
        user.setLastName("test4");
        user.setEmail("test4@test.test");
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        user.setAddress("test4");

        when(userService.updateAll(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/all")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateAll_UserNotFound_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test4");
        user.setMiddleName("test4");
        user.setLastName("test4");
        user.setEmail("test4@test.test");
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        user.setAddress("test4");

        when(userService.updateAll(any())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/all")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateAll_InvalidUser_Error() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("test4");
        user.setMiddleName(null);
        user.setLastName("test4");
        user.setEmail("test4@test.test");
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        user.setAddress("test4");

        when(userService.updateAll(any())).thenThrow(new UserInvalidException("Invalid user"));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/all")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    public void testDeleteUser_UserDeleted_Success() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUser_UserNotFound_Error() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testGetUsersByBirthdayRange_ValidDates_Success() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2000-01-01");
        Date endDate = dateFormat.parse("2024-01-01");

        List<UserEntity> users = new ArrayList<>();

        when(userService.getUsersByBirthdayRange(startDate, endDate)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/usersByBirthdayRange")
                        .param("startDate", "2000-01-01")
                        .param("endDate", "2024-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUsersByBirthdayRange_InvalidDates_Error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/usersByBirthdayRange")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2000-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
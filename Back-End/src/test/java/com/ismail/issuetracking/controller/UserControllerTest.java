// src/test/java/com/ismail/issuetracking/controller/UserControllerTest.java
package com.ismail.issuetracking.controller;

import com.ismail.issuetracking.dto.UserDTO;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.service.UserService;
import com.ismail.issuetracking.service.impl.MyUserDetailsService;
import com.ismail.issuetracking.config.jwt.JwtRequestFilter;
import com.ismail.issuetracking.config.jwt.JwtAuthenticationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean private UserService userService;
    @MockBean private MyUserDetailsService userDetailsService;
    @MockBean private JwtRequestFilter jwtRequestFilter;
    @MockBean private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void addUser_returns200AndMessage() throws Exception {
        User newUser = new User();
        newUser.setUserName("ana");
        newUser.setPassword("123");
        newUser.setEmail("ana@example.com");
        // stub correcto: recibe UserDTO, devuelve entidad User
        when(userService.add(any(UserDTO.class))).thenReturn(newUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"ana\",\"password\":\"123\",\"email\":\"ana@example.com\"}"))
           .andExpect(status().isOk());
    }
}

// src/test/java/com/ismail/issuetracking/controller/AuthenticationControllerTest.java
package com.ismail.issuetracking.controller;

import com.ismail.issuetracking.config.jwt.JwtAuthenticationEntryPoint;
import com.ismail.issuetracking.config.jwt.JwtRequestFilter;
import com.ismail.issuetracking.config.jwt.JwtTokenUtil;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.service.UserService;
import com.ismail.issuetracking.service.impl.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private JwtTokenUtil jwtTokenUtil;
    @MockBean private MyUserDetailsService userDetailsService;
    @MockBean private UserService userService;
    @MockBean private JwtRequestFilter jwtRequestFilter;
    @MockBean private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void authenticate_success_returnsTokenInResponseMessage() throws Exception {
        String username = "john";
        String password = "pass";
        String token    = "token123";

        UserDetails ud = withUsername(username)
            .password(password)
            .authorities(Collections.emptyList())
            .build();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(ud);
        when(jwtTokenUtil.generateToken(ud)).thenReturn(token);
        User persistedUser = new User();
        persistedUser.setUserName(username);
        persistedUser.setPassword(password);
        when(userService.findByUserName(username)).thenReturn(persistedUser);

        mvc.perform(post("/authenticate")
                .param("userName", username)
                .param("password", password))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.response.jwtResponse.token").value(token));
    }
}

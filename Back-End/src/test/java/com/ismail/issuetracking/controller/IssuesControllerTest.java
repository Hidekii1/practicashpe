// src/test/java/com/ismail/issuetracking/controller/IssuesControllerTest.java
package com.ismail.issuetracking.controller;

import com.ismail.issuetracking.dto.IssueDTO;
import com.ismail.issuetracking.entity.Issues;
import com.ismail.issuetracking.service.IssuesService;
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

@WebMvcTest(IssuesController.class)
@AutoConfigureMockMvc(addFilters = false)
class IssuesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean private IssuesService issuesService;
    @MockBean private MyUserDetailsService userDetailsService;
    @MockBean private JwtRequestFilter jwtRequestFilter;
    @MockBean private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void addIssue_returns200() throws Exception {
        // stub correcto: recibe IssueDTO, devuelve entidad Issues
        when(issuesService.add(any(IssueDTO.class))).thenReturn(new Issues());

        mvc.perform(post("/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test issue\",\"description\":\"Descripción\"}"))
           .andExpect(status().isOk());
    }
}

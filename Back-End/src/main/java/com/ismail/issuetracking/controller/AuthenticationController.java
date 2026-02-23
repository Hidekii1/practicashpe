package com.ismail.issuetracking.controller;

import com.ismail.issuetracking.config.jwt.JwtTokenUtil;
import com.ismail.issuetracking.dto.LoginDTO;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.exception.IssueTrackingException;
import com.ismail.issuetracking.model.JwtResponse;
import com.ismail.issuetracking.model.LoginResponse;
import com.ismail.issuetracking.model.ResponseMessage;
import com.ismail.issuetracking.service.UserService;
import com.ismail.issuetracking.service.impl.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            MyUserDetailsService userDetailsService,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(LoginDTO loginDTO) throws Exception {

        ResponseMessage responseMessage = ResponseMessage.getInstance();
        try {
            authenticate(loginDTO.getUserName(), loginDTO.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUserName());
            final String token = jwtTokenUtil.generateToken(userDetails);

            User user = userService.findByUserName(loginDTO.getUserName());
            JwtResponse jwtResponse = new JwtResponse(token);
            responseMessage.setResponse(new LoginResponse(user, jwtResponse));
        } catch (IssueTrackingException e) {
            responseMessage.setSuccess(false);
            responseMessage.setErrMsg(e.getMessage());
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setErrMsg(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new IssueTrackingException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new IssueTrackingException("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/errorLogin")
    public ResponseEntity<?> success(LoginDTO loginDTO) {
        ResponseMessage responseMessage = ResponseMessage.getInstance();
        try {
            authenticate(loginDTO.getUserName(), loginDTO.getPassword());
        } catch (ExpiredJwtException e) {
            responseMessage.setSuccess(false);
            responseMessage.setErrMsg(e.getMessage());
        } catch (Exception e) {
            responseMessage.setErrMsg(e.getMessage());
            responseMessage.setSuccess(false);
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
    }
}

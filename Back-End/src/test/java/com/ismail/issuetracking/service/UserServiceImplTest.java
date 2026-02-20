// src/test/java/com/ismail/issuetracking/service/UserServiceImplTest.java
package com.ismail.issuetracking.service;

import com.ismail.issuetracking.dao.PositionRepository;
import com.ismail.issuetracking.dao.RoleRepository;
import com.ismail.issuetracking.dao.UserRepository;
import com.ismail.issuetracking.dto.ChangePasswordDTO;
import com.ismail.issuetracking.dto.UserDTO;
import com.ismail.issuetracking.entity.Position;
import com.ismail.issuetracking.entity.Role;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.exception.IssueTrackingException;
import com.ismail.issuetracking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private PositionRepository positionRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private BCryptPasswordEncoder encoder;
    @InjectMocks private UserServiceImpl service;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUserName("john");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("pass");
        userDTO.setPositionId(1L);
        userDTO.setRoleId(1L);
    }

    @Test
    void add_duplicateUsername_throws() {
        when(userRepository.findByUserName("john")).thenReturn(new User());
        assertThrows(IssueTrackingException.class, () -> service.add(userDTO));
    }

    @Test
    void add_success_encodesPassword() {
        when(userRepository.findByUserName("john")).thenReturn(null);
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        Position pos = new Position(); pos.setId(1L);
        Role role = new Role(); role.setId(1L);
        when(positionRepository.findById(1L)).thenReturn(Optional.of(pos));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(encoder.encode("pass")).thenReturn("encoded");

        User saved = new User();
        when(userRepository.save(any(User.class))).thenReturn(saved);
        User result = service.add(userDTO);
        assertSame(saved, result);
    }

    @Test
    void changePassword_wrongOld_throws() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setUserId(1L);
        dto.setOldPassword("old");
        dto.setNewPassword("new");
        dto.setConfirmPassword("new");
        User user = new User(); user.setPassword("encodedOld");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(encoder.matches("old", "encodedOld")).thenReturn(false);
        assertThrows(IssueTrackingException.class, () -> service.changePassword(dto));
    }
}

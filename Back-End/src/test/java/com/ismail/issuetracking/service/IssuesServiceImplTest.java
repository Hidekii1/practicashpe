// src/test/java/com/ismail/issuetracking/service/IssuesServiceImplTest.java
package com.ismail.issuetracking.service;

import com.ismail.issuetracking.dao.IssuesRepository;
import com.ismail.issuetracking.dao.StatusRepository;
import com.ismail.issuetracking.dao.TypeRepository;
import com.ismail.issuetracking.dao.UserRepository;
import com.ismail.issuetracking.dto.IssueDTO;
import com.ismail.issuetracking.entity.Issues;
import com.ismail.issuetracking.entity.Status;
import com.ismail.issuetracking.entity.Type;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.exception.IssueTrackingException;
import com.ismail.issuetracking.service.impl.IssuesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssuesServiceImplTest {
    @Mock private IssuesRepository issuesRepository;
    @Mock private UserRepository userRepository;
    @Mock private StatusRepository statusRepository;
    @Mock private TypeRepository typeRepository;
    @InjectMocks private IssuesServiceImpl service;

    private IssueDTO dto;
    private User owner, assignee;
    private Type type;
    private Status defaultStatus;

    @BeforeEach
    void setUp() {
        dto = new IssueDTO();
        dto.setOwner(1L);
        dto.setAssignTo(2L);
        dto.setType(3L);
        owner = new User(); owner.setId(1L);
        assignee = new User(); assignee.setId(2L);
        type = new Type(); type.setId(3L);
        defaultStatus = new Status(); defaultStatus.setId(1L);
    }

    @Test
    void add_setsFieldsAndSaves() {
        when(userRepository.getOne(1L)).thenReturn(owner);
        when(userRepository.getOne(2L)).thenReturn(assignee);
        when(typeRepository.getOne(3L)).thenReturn(type);
        when(statusRepository.getOne(1L)).thenReturn(defaultStatus);
        Issues saved = new Issues();
        when(issuesRepository.save(any(Issues.class))).thenReturn(saved);

        Issues result = service.add(dto);

        assertSame(saved, result);
        verify(issuesRepository).save(any(Issues.class));
    }

    @Test
    void delete_nonExisting_throwsException() {
        when(issuesRepository.existsById(99L)).thenReturn(false);
        assertThrows(IssueTrackingException.class, () -> service.delete(99L));
    }

    @Test
    void issuesFilter_statusCase2_filtersByAssigned() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(assignee));
        when(statusRepository.findById(2L)).thenReturn(Optional.of(defaultStatus));
        List<Issues> list = Collections.singletonList(new Issues());
        when(issuesRepository.findByAssignToAndStatus(assignee, defaultStatus)).thenReturn(list);

        List<Issues> result = service.issuesFilter(2L, 6);
        assertEquals(list, result);
    }
}

package com.ismail.issuetracking.service.impl;

import com.ismail.issuetracking.dao.*;
import com.ismail.issuetracking.dto.IssueDTO;
import com.ismail.issuetracking.entity.Issues;
import com.ismail.issuetracking.entity.Status;
import com.ismail.issuetracking.entity.Type;
import com.ismail.issuetracking.entity.User;
import com.ismail.issuetracking.exception.IssueTrackingException;
import com.ismail.issuetracking.service.IssuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssuesServiceImpl implements IssuesService {

    private static final Logger logger = LoggerFactory.getLogger(IssuesServiceImpl.class);

    private final IssuesRepository issuesRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;

    public IssuesServiceImpl(IssuesRepository issuesRepository,
            UserRepository userRepository,
            StatusRepository statusRepository,
            TypeRepository typeRepository) {
        this.issuesRepository = issuesRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public Issues add(IssueDTO issueDTO) {
        Issues issues = issueDTO.toIssues();
        issues.setUser(userRepository.getOne(issueDTO.getOwner()));
        issues.setAssignTo(userRepository.getOne(issueDTO.getAssignTo()));
        issues.setType(typeRepository.getOne(issueDTO.getType()));
        issues.setStatus(statusRepository.getOne(1l));
        return issuesRepository.save(issues);
    }

    @Override
    public Issues edit(IssueDTO issueDTO) {
        Issues issues = issueDTO.toIssues();
        issues.setUser(userRepository.getOne(issueDTO.getOwner()));
        issues.setAssignTo(userRepository.getOne(issueDTO.getAssignTo()));
        issues.setType(typeRepository.getOne(issueDTO.getType()));
        issues.setStatus(statusRepository.getOne(issueDTO.getStatus()));
        return issuesRepository.save(issues);
    }

    @Override
    public boolean delete(Long id) {
        if (!issuesRepository.existsById(id)) {
            throw new IssueTrackingException("ISSUE_NOT_FOUND");
        } else {
            issuesRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public List<Issues> findAll() {
        return issuesRepository.findAll();
    }

    @Override
    public Issues find(Long id) {
        return issuesRepository.findById(id)
                .orElseThrow(() -> new IssueTrackingException("ISSUE_NOT_FOUND"));
    }

    @Override
    public List<Issues> findByUser(Long id) {
        logger.info(">>>>>>> from findByUser");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IssueTrackingException("USER_NOT_FOUND"));
        return issuesRepository.findByUser(user);
    }

    @Override
    public List<Issues> findByAssigned(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IssueTrackingException("USER_NOT_FOUND"));
        return issuesRepository.findByAssignTo(user);
    }

    @Override
    public List<Type> findAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public List<Status> findAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public List<Issues> issuesFilter(Long id, int filterId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IssueTrackingException("USER_NOT_FOUND"));
        List<Issues> issuesList = new ArrayList<>();
        switch (filterId) {
            case 1:
                issuesList = issuesRepository.findByUser(user);
                break;
            case 2:
                issuesList = issuesRepository.findByAssignTo(user);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                Status status = new Status();
                if (filterId == 3) {
                    status = statusRepository.findById(1L)
                            .orElseThrow(() -> new IssueTrackingException("STATUS_NOT_FOUND"));
                } else if (filterId == 4) {
                    status = statusRepository.findById(3L)
                            .orElseThrow(() -> new IssueTrackingException("STATUS_NOT_FOUND"));
                } else if (filterId == 5) {
                    status = statusRepository.findById(4L)
                            .orElseThrow(() -> new IssueTrackingException("STATUS_NOT_FOUND"));
                } else if (filterId == 6) {
                    status = statusRepository.findById(2L)
                            .orElseThrow(() -> new IssueTrackingException("STATUS_NOT_FOUND"));
                }
                issuesList = issuesRepository.findByAssignToAndStatus(user, status);
                break;
            default:
                break;
        }

        return issuesList;
    }
}

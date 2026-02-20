// src/test/java/com/ismail/issuetracking/repository/RepositoriesDataJpaTest.java
package com.ismail.issuetracking.repository;

import com.ismail.issuetracking.dao.IssuesRepository;
import com.ismail.issuetracking.dao.StatusRepository;
import com.ismail.issuetracking.dao.TypeRepository;
import com.ismail.issuetracking.dao.UserRepository;
import com.ismail.issuetracking.entity.Issues;
import com.ismail.issuetracking.entity.Status;
import com.ismail.issuetracking.entity.Type;
import com.ismail.issuetracking.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // Usa configuración de H2 en application-h2.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-h2.properties")
class RepositoriesDataJpaTest {

    @Autowired private IssuesRepository issuesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TypeRepository typeRepository;
    @Autowired private StatusRepository statusRepository;

    @Test
    void testIssuesRepository_saveAndFind() {
        Issues issue = new Issues();
        issue.setTitle("Test Issue");
        Issues saved = issuesRepository.save(issue);
        assertThat(issuesRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void testUserRepository_saveAndFind() {
        User user = new User();
        user.setUserName("test");
        User saved = userRepository.save(user);
        assertThat(userRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void testTypeRepository_saveAndFind() {
        Type type = new Type();
        type.setNeme("DEV");
        Type saved = typeRepository.save(type);
        assertThat(typeRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void testStatusRepository_saveAndFind() {
        Status status = new Status();
        status.setName("OPEN");
        Status saved = statusRepository.save(status);
        assertThat(statusRepository.findById(saved.getId())).isPresent();
    }
}

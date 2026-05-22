package com.example.base.repository;

import com.example.base.domain.entity.Role;
import com.example.base.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  UserRepository userRepository;

  @Test
  void findByUsername_shouldReturnUser() {
    User user = User.builder()
        .username("repository-test")
        .email("repository-test@example.com")
        .password("encoded-password")
        .firstName("Repository")
        .lastName("Test")
        .role(Role.USER)
        .build();

    entityManager.persistAndFlush(user);

    assertThat(userRepository.findByUsername("repository-test"))
        .isPresent()
        .get()
        .extracting(User::getEmail)
        .isEqualTo("repository-test@example.com");
  }

  @Test
  void existsUserByEmail_whenEmailExists_shouldReturnTrue() {
    User user = User.builder()
        .username("email-test")
        .email("email-test@example.com")
        .password("encoded-password")
        .role(Role.ADMIN)
        .build();

    entityManager.persistAndFlush(user);

    assertThat(userRepository.existsUserByEmail("email-test@example.com")).isTrue();
    assertThat(userRepository.existsUserByEmail("missing@example.com")).isFalse();
  }
}

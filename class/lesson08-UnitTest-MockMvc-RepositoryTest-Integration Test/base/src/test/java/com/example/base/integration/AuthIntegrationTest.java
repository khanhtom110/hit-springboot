package com.example.base.integration;

import com.example.base.domain.dto.request.LoginRequestDto;
import com.example.base.domain.entity.Role;
import com.example.base.domain.entity.User;
import com.example.base.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    userRepository.save(User.builder()
        .username("admin-test")
        .email("admin-test@example.com")
        .password(passwordEncoder.encode("AdminTest123"))
        .firstName("Admin")
        .lastName("Test")
        .role(Role.ADMIN)
        .build());
  }

  @Test
  void login_withSeededAdmin_shouldReturnJwtTokens() throws Exception {
    LoginRequestDto request = new LoginRequestDto("admin-test", "AdminTest123");

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("SUCCESS"))
        .andExpect(jsonPath("$.data.accessToken").value(not(blankOrNullString())))
        .andExpect(jsonPath("$.data.refreshToken").value(not(blankOrNullString())));
  }
}

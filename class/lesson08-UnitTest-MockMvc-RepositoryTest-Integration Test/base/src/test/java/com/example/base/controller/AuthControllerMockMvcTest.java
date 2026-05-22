package com.example.base.controller;

import com.example.base.domain.dto.request.LoginRequestDto;
import com.example.base.domain.dto.response.LoginResponseDto;
import com.example.base.exception.GlobalExceptionHandler;
import com.example.base.security.JwtAuthenticationFilter;
import com.example.base.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class AuthControllerMockMvcTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockitoBean
  AuthService authService;

  @MockitoBean
  JwtAuthenticationFilter jwtAuthenticationFilter;

  @Test
  void login_whenRequestValid_shouldReturnToken() throws Exception {
    LoginResponseDto response = LoginResponseDto.builder()
        .status(HttpStatus.OK)
        .message("Đăng nhập thành công")
        .id("user-1")
        .accessToken("access-token")
        .refreshToken("refresh-token")
        .build();

    when(authService.authentication(any(LoginRequestDto.class))).thenReturn(response);

    LoginRequestDto request = new LoginRequestDto("lesson8", "Password123");

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("SUCCESS"))
        .andExpect(jsonPath("$.data.accessToken").value("access-token"))
        .andExpect(jsonPath("$.data.refreshToken").value("refresh-token"));
  }

  @Test
  void login_whenUsernameBlank_shouldReturnBadRequest() throws Exception {
    LoginRequestDto request = new LoginRequestDto("", "Password123");

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.message").value("invalid.general.not-blank"));
  }
}

package com.example.base.service;

import com.example.base.domain.dto.request.CreateUserRequestDto;
import com.example.base.domain.dto.response.UserResponseDto;
import com.example.base.domain.entity.Role;
import com.example.base.domain.entity.User;
import com.example.base.domain.mapper.UserMapper;
import com.example.base.exception.VsException;
import com.example.base.repository.UserRepository;
import com.example.base.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  UserMapper userMapper;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void createUser_shouldEncodePasswordAndSaveUser() {
    CreateUserRequestDto request = new CreateUserRequestDto(
        "lesson8",
        "lesson8@example.com",
        "Password123",
        "Lesson",
        "Eight",
        Role.USER);

    User mappedUser = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(request.getPassword())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .role(request.getRole())
        .build();

    User savedUser = User.builder()
        .id("user-1")
        .username(request.getUsername())
        .email(request.getEmail())
        .password("encoded-password")
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .role(request.getRole())
        .build();

    UserResponseDto expectedResponse = new UserResponseDto(
        "user-1",
        request.getUsername(),
        request.getEmail(),
        request.getFirstName(),
        request.getLastName(),
        request.getRole());

    when(userRepository.existsUserByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsUserByUsername(request.getUsername())).thenReturn(false);
    when(userMapper.createUserRequestDtoToUser(request)).thenReturn(mappedUser);
    when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
    when(userRepository.save(mappedUser)).thenReturn(savedUser);
    when(userMapper.userToUserResponseDto(savedUser)).thenReturn(expectedResponse);

    UserResponseDto actual = userService.createUser(request);

    assertThat(actual).isEqualTo(expectedResponse);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    assertThat(userCaptor.getValue().getPassword()).isEqualTo("encoded-password");
  }

  @Test
  void createUser_whenEmailExists_shouldThrowException() {
    CreateUserRequestDto request = new CreateUserRequestDto(
        "lesson8",
        "lesson8@example.com",
        "Password123",
        "Lesson",
        "Eight",
        Role.USER);

    when(userRepository.existsUserByEmail(request.getEmail())).thenReturn(true);

    assertThatThrownBy(() -> userService.createUser(request))
        .isInstanceOf(VsException.class);

    verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any());
    verify(passwordEncoder, never()).encode(org.mockito.ArgumentMatchers.anyString());
  }
}

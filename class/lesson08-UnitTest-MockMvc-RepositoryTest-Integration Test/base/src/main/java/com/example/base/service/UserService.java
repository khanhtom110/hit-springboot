package com.example.base.service;

import com.example.base.domain.dto.request.CreateUserRequestDto;
import com.example.base.domain.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

  UserResponseDto getMyProfile(String userId);

  UserResponseDto getUserById(String userId);

  UserResponseDto createUser(CreateUserRequestDto request);

  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

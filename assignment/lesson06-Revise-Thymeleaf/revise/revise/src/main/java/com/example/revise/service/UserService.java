package com.example.revise.service;

import com.example.revise.dto.response.ApiResponse;
import com.example.revise.dto.response.CursorPageResponse;
import com.example.revise.dto.response.UserListResponse;
import com.example.revise.entity.User;
import com.example.revise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public CursorPageResponse<UserListResponse> findAllKey(String cursor, int size, String search) {
        Pageable limit = PageRequest.of(0, size + 1);

        List<User> users;
        if (search != null && !search.isBlank()) {
            users = cursor != null ? userRepository.findNextPageWithSearch(cursor, search, limit)
                    : userRepository.findFirstPageWithSearch(search, limit);
        }
        return null;
    }
}

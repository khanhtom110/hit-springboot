package com.example.fullflow.service;

import com.example.fullflow.dto.request.CreateUserRequest;
import com.example.fullflow.exception.DuplicateResourceException;
import com.example.fullflow.exception.ResourceNotFoundException;
import com.example.fullflow.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> findAll() {
        return users;
    }

    public User findById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public boolean existsById(Long id){
        return users.stream()
                .anyMatch(user -> user.getId().equals(id));
    }

    public int getIndex(Long id){
        for(int i=0;i<users.size();i++){
            if(users.get(i).equals(id)){
                return i;
            }
        }
        return -1;
    }

    public User create(CreateUserRequest request) {
        // Kiểm tra email trùng
        boolean emailExists = users.stream()
                .anyMatch(u -> u.getEmail().equals(request.getEmail()));
        if (emailExists) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        User user = new User();
        user.setId(nextId++);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        users.add(user);
        return user;
    }

    public void delete(Long id) {
        User user = findById(id);
        users.remove(user);
    }


    //update
    public User update(Long id, CreateUserRequest request) {
        boolean userExist=existsById(id);
        if(!userExist){
            throw new ResourceNotFoundException("User","Id",id);
        }

        int index=getIndex(id);
        if(index==-1){
            throw new ResourceNotFoundException("User","Id",id);
        }

        User user=new User();
        user.setId(nextId++);
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        users.set(index,user);
        return user;
    }
}

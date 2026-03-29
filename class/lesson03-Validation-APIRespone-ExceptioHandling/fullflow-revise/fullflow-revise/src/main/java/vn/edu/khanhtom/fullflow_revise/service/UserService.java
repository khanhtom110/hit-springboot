package vn.edu.khanhtom.fullflow_revise.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vn.edu.khanhtom.fullflow_revise.dto.request.UserCreationRequest;
import vn.edu.khanhtom.fullflow_revise.dto.respone.ApiResponse;
import vn.edu.khanhtom.fullflow_revise.exception.DuplicateResourceException;
import vn.edu.khanhtom.fullflow_revise.exception.ResourceNotFoundException;
import vn.edu.khanhtom.fullflow_revise.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users=new ArrayList<>();
    private Long nextId=1L;

    public List<User> findAll(){
        return users;
    }

    public User getById(Long id){
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
    }

    public User create(UserCreationRequest request) {
    //Kiem tra email trung
        boolean emailExists=users.stream()
                .anyMatch(user -> user.getEmail().equals(request.getEmail()));
        if(emailExists){
            throw new DuplicateResourceException("Email","email",request.getEmail());
        }

        User user=new User();
        user.setId(nextId++);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());

        users.add(user);
        return user;
    }

    public int findById(Long id){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }

    public User update(Long id, UserCreationRequest request){
        //kiem tra ton tai
        boolean idExists=users.stream()
                .anyMatch(user -> user.getId().equals(id));
        if(!idExists){
            throw new ResourceNotFoundException("User","id",id);
        }

        int index= findById(id);
        if(index==-1){
            throw new ResourceNotFoundException("User","id",id);
        }

        User user=new User();
        user.setId(nextId++);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());

        users.set(index,user);
        return user;
    }

    public void delete(Long id){
        int index= findById(id);
        if(index==-1){
            throw new ResourceNotFoundException("User","id",id);
        }
        users.remove(index);
    }
}

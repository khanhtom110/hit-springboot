package vn.edu.khanhtom.StudentManagement.service;

import org.springframework.stereotype.Service;
import vn.edu.khanhtom.StudentManagement.dto.request.CreateStudentRequest;
import vn.edu.khanhtom.StudentManagement.model.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<Student> students=new ArrayList<>();
    private Long nextId=1L;

    public Student create(CreateStudentRequest request){
        //Check trung studentCode
        boolean codeExists=students.stream()
                .anyMatch(student -> student.getStudentCode().equals(request.getStudentCode()));
    }
}

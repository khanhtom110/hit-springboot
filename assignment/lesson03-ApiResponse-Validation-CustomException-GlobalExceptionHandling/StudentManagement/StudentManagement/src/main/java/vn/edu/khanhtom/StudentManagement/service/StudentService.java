package vn.edu.khanhtom.StudentManagement.service;

import org.springframework.stereotype.Service;
import vn.edu.khanhtom.StudentManagement.dto.request.CreateStudentRequest;
import vn.edu.khanhtom.StudentManagement.dto.request.UpdateStudentRequest;
import vn.edu.khanhtom.StudentManagement.exception.DuplicateResourceException;
import vn.edu.khanhtom.StudentManagement.exception.ResourceNotFoundException;
import vn.edu.khanhtom.StudentManagement.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long,Student> students=new HashMap<>();
    private Long nextId=1L;

    public Map<Long, Student> getAllStudents(){
        return students;
    }

    public Student getStudentById(Long id){
        Student student= students.get(id);
        if(student==null){
            throw new ResourceNotFoundException("Sinh viên","id",id);
        }
        return student;
    }

    public Student createStudent(CreateStudentRequest request){
        //Check trung studentCode
        boolean codeExists=students.values().stream()
                .anyMatch(student -> student.getStudentCode().equals(request.getStudentCode()));
        if(codeExists){
            throw new DuplicateResourceException("Sinh viên","mã sinh viên",request.getStudentCode());
        }

        //Check trung email
        boolean emailExists=students.values().stream()
                .anyMatch(student -> student.getEmail().equals(request.getEmail()));
        if(emailExists){
            throw new DuplicateResourceException("Sinh viên","email",request.getEmail());
        }

        Student student=new Student();
        student.setId(nextId++);
        student.setStudentCode(request.getStudentCode());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGpa(request.getGpa());
        student.setMajor(request.getMajor());
        student.setYear(request.getYear());

        students.put(student.getId(),student);
        return student;
    }

    public Student updateStudent(Long id, UpdateStudentRequest request){
        Student student=getStudentById(id);

        //Check trung email cua student moi
        boolean emailExists=students.values().stream()
                .anyMatch(s -> s.getEmail().equals(request.getEmail()) && !s.getId().equals(id));
        if(emailExists){
            throw new DuplicateResourceException("Sinh viên","email",request.getEmail());
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGpa(request.getGpa());
        student.setMajor(request.getMajor());
        student.setYear(request.getYear());

        return student;
    }

    public void deleteStudent(Long id){
        boolean exists=students.containsKey(id);
        if(!exists){
            throw new ResourceNotFoundException("Sinh viên","id",id);
        }

        students.remove(id);
    }

    public Map<Long, Student> getStudentsByMajor(String major){
        return students.entrySet().stream()
                        .filter(entry -> entry.getValue().getMajor().equals(major))
                .collect(Collectors.toMap(
                        longStudentEntry -> longStudentEntry.getKey(),
                        longStudentEntry -> longStudentEntry.getValue()
                ));
    }

    public Map<Long, Student> getHonorStudents(){
        return students.entrySet().stream()
                .filter(s->s.getValue().getGpa()!=null && s.getValue().getGpa()>=3.6)
                .collect(Collectors.toMap(
                        longStudentEntry -> longStudentEntry.getKey(),
                        longStudentEntry -> longStudentEntry.getValue()
                ));
    }
}

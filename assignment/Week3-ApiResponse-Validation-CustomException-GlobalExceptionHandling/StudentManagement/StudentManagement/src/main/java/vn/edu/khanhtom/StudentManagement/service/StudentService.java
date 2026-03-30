package vn.edu.khanhtom.StudentManagement.service;

import org.springframework.stereotype.Service;
import vn.edu.khanhtom.StudentManagement.dto.request.CreateStudentRequest;
import vn.edu.khanhtom.StudentManagement.dto.request.UpdateStudentRequest;
import vn.edu.khanhtom.StudentManagement.exception.DuplicateResourceException;
import vn.edu.khanhtom.StudentManagement.exception.ResourceNotFoundException;
import vn.edu.khanhtom.StudentManagement.model.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<Student> students=new ArrayList<>();
    private Long nextId=1L;

    public List<Student> getAllStudents(){
        return students;
    }

    public Student getStudentById(Long id){
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sinh viên","id",id));
    }

    public Student createStudent(CreateStudentRequest request){
        //Check trung studentCode
        boolean codeExists=students.stream()
                .anyMatch(student -> student.getStudentCode().equals(request.getStudentCode()));
        if(codeExists){
            throw new DuplicateResourceException("Sinh viên","mã sinh viên",request.getStudentCode());
        }

        //Check trung email
        boolean emailExists=students.stream()
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

        students.add(student);
        return student;
    }

    public Student updateStudent(Long id, UpdateStudentRequest request){
        Student student=getStudentById(id);

        int index= getIndexById(id);
        if(index==-1){
            throw new ResourceNotFoundException("Sinh viên","id",id);
        }

        //Check trung email cua student moi
        boolean emailExists=students.stream()
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

    private int getIndexById(Long id) {
        for(int i=0;i<students.size();i++){
            if(students.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public void deleteStudent(Long id){
        int index= getIndexById(id);
        if(index==-1){
            throw new ResourceNotFoundException("Sinh viên","id",id);
        }

        students.remove(index);
    }

    public List<Student> getStudentsByMajor(String major){
        return students.stream()
                .filter(s -> s.getMajor().equals(major))
                .toList();
    }

    public List<Student> getHonorStudents(){
        return students.stream()
                .filter(s->s.getGpa()!=null && s.getGpa()>=3.6)
                .toList();
    }
}

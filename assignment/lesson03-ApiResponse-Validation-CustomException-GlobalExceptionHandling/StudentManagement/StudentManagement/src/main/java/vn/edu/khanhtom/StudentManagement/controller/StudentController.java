package vn.edu.khanhtom.StudentManagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.khanhtom.StudentManagement.dto.request.CreateStudentRequest;
import vn.edu.khanhtom.StudentManagement.dto.request.UpdateStudentRequest;
import vn.edu.khanhtom.StudentManagement.dto.response.ApiResponse;
import vn.edu.khanhtom.StudentManagement.model.Student;
import vn.edu.khanhtom.StudentManagement.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<Long,Student>>> getAll(){
        return ResponseEntity.ok(ApiResponse.success(studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(studentService.getStudentById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> create(@Valid @RequestBody CreateStudentRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(studentService.createStudent(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> update(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateStudentRequest request){
        return ResponseEntity.ok(ApiResponse.success(studentService.updateStudent(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<ApiResponse<Map<Long,Student>>> getByMajor(@PathVariable String major){
        return ResponseEntity.ok(ApiResponse.success(studentService.getStudentsByMajor(major)));
    }

    @GetMapping("/honors")
    public ResponseEntity<ApiResponse<Map<Long,Student>>> getHonors(){
        return ResponseEntity.ok(ApiResponse.success(studentService.getHonorStudents()));
    }
}

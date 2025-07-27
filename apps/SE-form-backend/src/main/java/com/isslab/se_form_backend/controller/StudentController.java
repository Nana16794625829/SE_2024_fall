package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.model.StudentUpdate;
import com.isslab.se_form_backend.service.AbstractStudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {

    private final AbstractStudentService studentService;

    public StudentController(AbstractStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/")
    public ResponseEntity<?> createSingleStudent(@RequestBody Student studentInfo){
        studentService.createSingleStudent(studentInfo);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createMultiStudents(@RequestParam("file") MultipartFile file){
        studentService.createMultiStudents(file);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @PutMapping("/")
    public ResponseEntity<?> updateStudentById(@RequestBody StudentUpdate studentInfo){
        studentService.updateStudentById(studentInfo);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteStudentById(@RequestBody Map<String, String> body){
        String studentId = body.get("studentId");
        studentService.deleteStudentById(studentId);

        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}

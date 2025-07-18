package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.StudentEntity;
import com.isslab.se_form_backend.helper.CsvReader;
import com.isslab.se_form_backend.model.ClassType;
import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.model.StudentUpdate;
import com.isslab.se_form_backend.repository.StudentRepository;
import com.isslab.se_form_backend.service.AbstractStudentService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class StudentService extends AbstractStudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Set<String> getAllStudentIds() {
        return studentRepository.getAllStudentIds();
    }

    @Override
    public boolean isReviewer(String studentId) {
        Student student = getStudentById(studentId);
        return student.getClassType() == ClassType.DAY;
    }

    @Override
    public boolean isPresenter(String studentId) {
        Student student = getStudentById(studentId);
        return student.getClassType() == ClassType.ON_SERVICE;
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentRepository.getStudentByStudentId(studentId);
    }

    @Override
    public void createSingleStudent(Student studentInfo) {
        String studentId = studentInfo.getStudentId();
        String name = studentInfo.getName();
        String email = studentInfo.getEmail();
        ClassType classType = studentInfo.getClassType();

        StudentEntity studentEntity = new StudentEntity(studentId, name, email, classType);
        studentRepository.save(studentEntity);
    }

    @Override
    public void createMultiStudents(MultipartFile file) {
        List<StudentEntity> studentList = CsvReader.loadStudentsFromCsv(file);
        studentRepository.saveAll(studentList);
    }

    @Override
    public void updateStudentById(StudentUpdate studentInfo) {
        String studentId = studentInfo.getStudentId();
        Student student = studentRepository.getStudentByStudentId(studentId);

        String name = studentInfo.getName();
        String email = studentInfo.getEmail();
        ClassType classType = student.getClassType();

        StudentEntity studentEntity = new StudentEntity(studentId, name, email, classType);

        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudentById(String studentId) {
        studentRepository.deleteByStudentId(studentId);
    }
}

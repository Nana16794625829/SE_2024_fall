package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.model.StudentUpdate;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractStudentService {

    public abstract boolean isReviewer(String studentId);
    public abstract boolean isPresenter(String studentId);
    public abstract Student getStudentById(String studentId);
    public abstract void createSingleStudent(Student studentInfo);
    public abstract void createMultiStudents(MultipartFile file);
    public abstract void updateStudentById(StudentUpdate studentInfo);
    public abstract void deleteStudentById(String studentId);
}

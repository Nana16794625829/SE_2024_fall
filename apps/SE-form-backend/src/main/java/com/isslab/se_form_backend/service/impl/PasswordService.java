package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.StudentEntity;
import com.isslab.se_form_backend.helper.exception.UserNotFoundException;
import com.isslab.se_form_backend.model.ForgetPasswordResponse;
import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.repository.StudentRepository;
import com.isslab.se_form_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;

    @Value("${app.frontEnd.base-url}")
    private String baseUrl;

    public PasswordService(PasswordEncoder passwordEncoder, StudentRepository studentRepository, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
    }

    public boolean checkPassword(String studentId, String password) {
        String existedPassword = studentRepository.getPasswordByStudentId(studentId);

        return passwordEncoder.matches(password, existedPassword);
    }

    public void changePassword(String studentId, String newPassword) {
        Student user = studentRepository.getStudentByStudentId(studentId)
                .orElseThrow(UserNotFoundException::new);
        String encodePassword = passwordEncoder.encode(newPassword);

        StudentEntity studentEntity = new StudentEntity(user.getStudentId(), user.getName(), user.getEmail(), user.getClassType(), encodePassword);

        studentRepository.save(studentEntity);
    }

    public void resetPassword(String token, String newPassword) {
        if (!jwtUtil.validateResetPasswordToken(token))
            throw new IllegalArgumentException("Token invalid");

        String username = jwtUtil.extractUsername(token);
        Student user = studentRepository.getStudentByStudentId(username)
                .orElseThrow(UserNotFoundException::new);
        String encodePassword = passwordEncoder.encode(newPassword);
        StudentEntity studentEntity = new StudentEntity(user.getStudentId(), user.getName(), user.getEmail(), user.getClassType(), encodePassword);

        studentRepository.save(studentEntity);

    }

    public ForgetPasswordResponse getResetEmailInfo(String studentId) {
        Student student = studentRepository.getStudentByStudentId(studentId)
                .orElseThrow(UserNotFoundException::new);

        String email = student.getEmail();
        String token = jwtUtil.generateResetPasswordToken(studentId);
        String resetUrl = baseUrl + "api/reset?token=" + token;

        return ForgetPasswordResponse.builder().email(email).resetUrl(resetUrl).build();
    }
}

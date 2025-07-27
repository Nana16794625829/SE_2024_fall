package com.isslab.se_form_backend.security.service;

import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StudentUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;

    public StudentUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.getStudentByStudentId(username);

        return User.builder()
                .username(student.getStudentId())
                .password(student.getPassword())
                .roles("USER")
                .build();
    }
}

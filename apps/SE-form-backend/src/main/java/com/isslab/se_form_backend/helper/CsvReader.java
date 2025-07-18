package com.isslab.se_form_backend.helper;

import com.isslab.se_form_backend.entity.StudentEntity;
import com.isslab.se_form_backend.model.ClassType;
import com.isslab.se_form_backend.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<StudentEntity> loadStudentsFromCsv(MultipartFile csvFile) {
        List<StudentEntity> StudentEntities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            reader.readLine(); // 讀掉 header，不用處理

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String studentId = parts[1];
                String email = parts[2];
                ClassType classType = ClassType.valueOf(parts[3]);

                StudentEntity student = new StudentEntity(studentId, name, email, classType);

                StudentEntities.add(student);
            }
            return StudentEntities;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

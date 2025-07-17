package com.isslab.se_form_backend;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.helper.FormSubmissionImporter;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.repository.FormSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SeFormBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeFormBackendApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(
//            FormScoreCsvImporter scoreImporter,
//            FormSubmissionImporter submissionImporter,
//            FormScoreRecordRepository scoreRepo,
//            FormSubmissionRepository submissionRepo
//    ) {
//        return args -> {
//            importIfEmpty(submissionRepo, submissionImporter, scoreImporter);
//        };
//    }
//
//    private void importIfEmpty(FormSubmissionRepository submissionRepo, FormSubmissionImporter submissionImporter, FormScoreCsvImporter scoreImporter) {
//        if (submissionRepo.count() == 0) {
//            try (InputStream inputStream = new FileInputStream("src/main/resources/mock/formSubmissionSample.csv")) {
//                List<FormSubmissionEntity> submissions = submissionImporter.importSubmissionCsv(inputStream);
//
//                try (InputStream scoreStream = new FileInputStream("src/main/resources/mock/sample.csv")) {
//                    scoreImporter.importScoreRecordCsv(scoreStream, submissions);
//                }
//
//                System.out.println("匯入完成");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
package com.isslab.se_form_backend;

import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication
public class SeFormBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeFormBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FormScoreCsvImporter importer, FormScoreRecordRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                try (InputStream inputStream = new FileInputStream("src/main/resources/mock/sample.csv")) {
                    importer.importCsv(inputStream);
                    System.out.println("資料匯入完成");
                }
            } else {
                System.out.println("已有資料，跳過匯入");
            }
        };
    }
}
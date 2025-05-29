package com.isslab.se_form_backend.helper;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FormScoreCsvImporter {

    private final FormScoreRecordRepository repository;

    public FormScoreCsvImporter(FormScoreRecordRepository repository) {
        this.repository = repository;
    }

    public void importCsv(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord record : csvParser) {
                FormScoreRecordEntity entity = new FormScoreRecordEntity();
                entity.setFormId(1L);
                entity.setPresenterId("113522001");
                entity.setReviewerId(record.get(0));
                entity.setScore(record.get(1));

                repository.save(entity);
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV 匯入失敗", e);
        }
    }
}


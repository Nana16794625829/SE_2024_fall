package com.isslab.se_form_backend.helper;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MockFormSubmission {

    public static List<FormSubmissionEntity> loadFromCsv(String resourcePath) {
        List<FormSubmissionEntity> submissions = new ArrayList<>();

        try (InputStream inputStream = MockFormSubmission.class.getResourceAsStream(resourcePath);
             Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                Long formId = Math.abs(new Random().nextLong());
                String submitterId = csvRecord.get(0);
                submissions.add(new FormSubmissionEntity(formId, submitterId, "1", "2025/06/06", "mock data"));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("讀取 mock 資料失敗", e);
        }

        return submissions;
    }
}

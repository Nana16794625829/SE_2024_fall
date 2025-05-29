package com.isslab.se_form_backend.helper;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GradeMockDataLoader {

    public static List<FormScoreRecordEntity> loadFromCsv(String resourcePath) {
        List<FormScoreRecordEntity> records = new ArrayList<>();

        try (InputStream inputStream = GradeMockDataLoader.class.getResourceAsStream(resourcePath);
             Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                String studentId = csvRecord.get(0);
                String score = csvRecord.get(1);
                records.add(new FormScoreRecordEntity(1L, 1L, score, studentId, "113500001"));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("讀取 mock 資料失敗", e);
        }

        return records;
    }
}

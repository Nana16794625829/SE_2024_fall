package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.Grade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
* 此 Service 不需要使用 DB
* */
@Slf4j
public class GradesToCSVService {
    private final String csvDir;

    public GradesToCSVService(String csvDir){
        this.csvDir = csvDir;
    }

    public void createGradeCSV(List<Grade> grades, String fileName, String week) {
        //csvDir="src/main/resources/output/"
        String filePath = csvDir + week  + "/" + fileName + ".csv";
        createDirectoriesIfNotExist(filePath);
        writeGradesToCsv(filePath, grades);
    }

    private void writeGradesToCsv(String filePath, List<Grade> grades) {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ){
            for (Grade grade : grades) {
                writer.write(grade.getStudentId() + "," + grade.getGrade());
                writer.newLine();
            }

            csvPrinter.flush();  // 確保數據已經寫入
            log.info("Data successfully written to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectoriesIfNotExist(String filePath) {
        Path directoryPath = Paths.get(filePath).getParent();  // 取得文件的父路徑
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);  // 創建所需的目錄
                log.info("Directories created: " + directoryPath);
            } else {
                log.info("Directories already exist: " + directoryPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

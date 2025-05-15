//package com.isslab.se_form_backend;
//
//import com.isslab.se_form_backend.model.Grade;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//class SeFormSubmissionBackendApplicationTests {
//
//    Grade grade1 = Grade.builder().studentId("113000001").grade(86.41).build();
//    Grade grade2 = Grade.builder().studentId("113000002").grade(93.22).build();
//    Grade grade3 = Grade.builder().studentId("113000003").grade(93.22).build();
//    Grade grade4 = Grade.builder().studentId("113000004").grade(75).build();
//    Grade grade5 = Grade.builder().studentId("113000005").grade(60).build();
//
//    List<Grade> undergraduates = List.of(grade1, grade2, grade3, grade4, grade5);
//
//    Grade grade6 = Grade.builder().studentId("113500001").grade(80).build();
//    Grade grade7 = Grade.builder().studentId("113500002").grade(78.41).build();
//    Grade grade8 = Grade.builder().studentId("113500003").grade(88.5).build();
//
//    List<Grade> onService = List.of(grade6, grade7, grade8);
//
//    @Test
//    void gradesToCSVServiceTest() throws Exception {
//        GradesToCSVService gradesToCSVService = new GradesToCSVService("");
//        gradesToCSVService.createGradeCSV(undergraduates, "undergraduates", "week3");
//        gradesToCSVService.createGradeCSV(onService, "onService", "week2");
//    }
//
//}

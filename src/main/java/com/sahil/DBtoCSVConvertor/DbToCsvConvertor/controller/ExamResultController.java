package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.controller;

import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.runner.DbToCSVBatchProcessingRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamResultController {
    @Autowired
    DbToCSVBatchProcessingRunner csv;

    @GetMapping("/hel")
    public String sayHello() {
        return "Hello!!!";
    }

    @GetMapping("/stu")
    public void callJob() {
        try {
            csv.executeJob();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

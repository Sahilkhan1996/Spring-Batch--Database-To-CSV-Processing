package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.processor;

import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.model.ExamResult;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

    @Override
    public ExamResult process(ExamResult item) throws Exception {
        if (item.getPercentage() >= 90 / .0F) return item;
        else return null;

    }
}

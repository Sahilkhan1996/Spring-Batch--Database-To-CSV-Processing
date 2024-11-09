package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.processor;

import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.model.ExamResult;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

    @Override
    public ExamResult process(ExamResult item) throws Exception {
        System.out.println("Item ID::"+item.getId());
        System.out.println("Item Obj"+item.toString());
        if (item.getSemester()>=8) {
            return item;
        } else {
            return null;
        }

    }
}

package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult {

    private Integer id;
    private Date dob;
    private Integer semester;
    private Double percentage;

    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", dob=" + dob +
                ", semester=" + semester +
                ", percentage=" + percentage +
                '}';
    }
}

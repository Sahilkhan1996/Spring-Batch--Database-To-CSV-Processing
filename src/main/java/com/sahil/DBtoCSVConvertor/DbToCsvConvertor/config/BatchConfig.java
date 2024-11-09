package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.config;

import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.listener.JobMonitoringListener;
import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.model.ExamResult;
import com.sahil.DBtoCSVConvertor.DbToCsvConvertor.processor.ExamResultItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jbFactory;
    @Autowired
    private StepBuilderFactory sbFactory;
    @Autowired
    private JobMonitoringListener listener;
    @Autowired
    private ExamResultItemProcessor processor;

    @Autowired
    private DataSource ds;
    @Bean
    public JobMonitoringListener createlistener(){
       return new JobMonitoringListener();
    }

    @Bean
    public JdbcCursorItemReader<ExamResult> createReader() {
        //create Reader class Object
        JdbcCursorItemReader<ExamResult> reader = new JdbcCursorItemReader<>();
        //set datasource
        reader.setDataSource(ds);
        //specify sql query to get the records
        reader.setSql("select ID,DOB,SEMESTER,PERCENTAGE FROM EXAM_RESULT");
        //specify row mapper to convert each record of rs to model class object
        reader.setRowMapper((rs, rowNum) -> {
            return new ExamResult(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4));
        });


        return reader;
    }

    @Bean
    public FlatFileItemWriter<ExamResult> createWriter() {
        FlatFileItemWriter<ExamResult> writer = new FlatFileItemWriter<ExamResult>();
        //specify the location of destination
        writer.setResource(new FileSystemResource("E:\\csvs\\DBTOCSVFILE"));
        //create Field Extractor Obj
        BeanWrapperFieldExtractor<ExamResult> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"id", "dob", "semester", "percentage"});
        //create Line Aggregator that builds the line having model class obj data
        DelimitedLineAggregator<ExamResult> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(extractor);
        //set line aggregator to writer obj
        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    //step creation
    @Bean(name="step1")
    public Step createStep1(){
        return sbFactory.get("step1")
                .<ExamResult,ExamResult>chunk(100)
                .reader(createReader())
                .writer(createWriter())
                .processor(createProcessor())
                .build();
    }


    @Bean
    public ItemProcessor<ExamResult,ExamResult> createProcessor(){
        return new ExamResultItemProcessor();
    }

    @Bean(name = "job1")
    public Job createJob(){
        return jbFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(createStep1())
                .build();
    }


}

package com.sahil.DBtoCSVConvertor.DbToCsvConvertor.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbToCSVBatchProcessingRunner {
    @Autowired
    private JobLauncher launcher;
    @Autowired
    private Job job;

    public void executeJob() throws Exception {
        JobParameters params=new JobParametersBuilder()
                .addLong("sysTime",System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution=launcher.run(job,params);
        System.out.println("Job Completion status:::"+execution.getStatus());
    }
}

package com.bysj;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.bysj.*.mapper")
@EnableTransactionManagement
@ServletComponentScan
@EnableScheduling
public class SpringbootGraduationApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(SpringbootGraduationApplication.class, args);
	}
}

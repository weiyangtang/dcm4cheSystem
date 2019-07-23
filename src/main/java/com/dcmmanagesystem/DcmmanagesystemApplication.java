package com.dcmmanagesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dcmmanagesystem.dao")
public class DcmmanagesystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcmmanagesystemApplication.class, args);
    }

}

package com.fanggeek.mm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(value = "com.fanggeek.mm")
@EnableScheduling
public class MongoMonitorWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoMonitorWebApplication.class, args);
    }

}

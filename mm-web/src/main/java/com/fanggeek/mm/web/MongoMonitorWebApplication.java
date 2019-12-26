package com.fanggeek.mm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(value = "com.fanggeek.mm")
@EnableScheduling
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoRepositoriesAutoConfiguration .class, MongoDataAutoConfiguration.class})
public class MongoMonitorWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoMonitorWebApplication.class, args);
    }

}

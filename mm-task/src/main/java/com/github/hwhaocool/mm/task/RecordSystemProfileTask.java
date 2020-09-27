package com.github.hwhaocool.mm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.hwhaocool.mm.service.Profile2SlowService;

@Component
public class RecordSystemProfileTask {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordSystemProfileTask.class);

    @Autowired
    private Profile2SlowService profile2SlowService;
    
    @Scheduled(cron = "0 0/1 * * * ?")
    public void recordTask() {
        
        LOGGER.info("RecordSystemProfileTask recordTask start");
        
        profile2SlowService.recordAndSave2Other();  
        
        LOGGER.info("RecordSystemProfileTask recordTask end");
    }
}

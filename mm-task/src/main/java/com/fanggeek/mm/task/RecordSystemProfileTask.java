package com.fanggeek.mm.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.fanggeek.mm.db.dao.ProfileDAO;

@Component
public class RecordSystemProfileTask {

    @Autowired
    private ProfileDAO profileDAO;
    
    @ApolloConfig
    private Config config;
    
    @Scheduled(cron = "0 0/1 * * * ?")
    public void recordTask() {
        
//        profileDAO.
        
//        config = ApolloUtils.getConfig();
    }
}

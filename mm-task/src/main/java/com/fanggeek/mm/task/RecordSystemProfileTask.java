package com.fanggeek.mm.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.fanggeek.mm.dao.model.doc.SystemProfileDocument;
import com.fanggeek.mm.db.dao.ProfileDAO;
import com.mongodb.BasicDBObject;

@Component
public class RecordSystemProfileTask {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordSystemProfileTask.class);

    @Autowired
    private ProfileDAO profileDAO;
    
    @ApolloConfig
    private Config config;
    
    @Scheduled(cron = "0 0/1 * * * ?")
    public void recordTask() {
        
        LOGGER.info("RecordSystemProfileTask recordTask");
        
        List<BasicDBObject> listByOid = profileDAO.getListByOid(null, 1);
        
        LOGGER.info("result is {}", listByOid);
        
//        List<SystemProfileDocument> listByOid = profileDAO.getListByOid(null, 20);
        
//        config = ApolloUtils.getConfig();
    }
}

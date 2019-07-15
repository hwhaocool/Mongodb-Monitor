package com.fanggeek.mm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;
import com.fanggeek.mm.db.dao.ProfileDAO;
import com.mongodb.BasicDBObject;

@Service
public class SlowOpService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SlowOpService.class);

    @Autowired
    private ProfileDAO profileDAO;
    
    public void test() {
        LOGGER.info("RecordSystemProfileTask recordTask");
        
        List<BasicDBObject> list = profileDAO.getList(1);
        
//        List<BasicDBObject> listByOid = profileDAO.getListByOid(null, 1);
        
        LOGGER.info("result is {}", list);
    }
    
    private SlowOpRecordDocument genDoc(BasicDBObject basicDBObject) {
        SlowOpRecordDocument document = new SlowOpRecordDocument();
        
        return document;
    }
}

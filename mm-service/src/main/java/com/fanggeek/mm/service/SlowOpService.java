package com.fanggeek.mm.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanggeek.common.json.JSON2Helper;
import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;
import com.fanggeek.mm.db.dao.ProfileDAO;
import com.fanggeek.mm.db.dao.SlowOpRecordDAO;
import com.mongodb.BasicDBObject;

@Service
public class SlowOpService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SlowOpService.class);

    @Autowired
    private ProfileDAO profileDAO;
    
    @Autowired
    private SlowOpRecordDAO slowOpRecordDAO;
    
    public void query() {
        
    }
    
}

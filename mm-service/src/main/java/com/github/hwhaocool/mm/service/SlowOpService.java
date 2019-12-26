package com.github.hwhaocool.mm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanggeek.mm.db.dao.ProfileDAO;
import com.fanggeek.mm.db.dao.SlowOpRecordDAO;

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

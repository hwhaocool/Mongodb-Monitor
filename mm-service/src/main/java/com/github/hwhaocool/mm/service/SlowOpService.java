package com.github.hwhaocool.mm.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.db.dao.SlowOpRecordDAO;

@Service
public class SlowOpService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SlowOpService.class);

    @Autowired
    private SlowOpRecordDAO slowOpRecordDAO;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public String queryById(String id) {
        if (StringUtils.isBlank(id)) {
            return "id is required";
        }
        
        SlowOpRecordDocument byId = slowOpRecordDAO.getById(id);
        
        if (null == byId) {
            return "id not exist";
        }
        
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(byId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return "error";
    }
    
}

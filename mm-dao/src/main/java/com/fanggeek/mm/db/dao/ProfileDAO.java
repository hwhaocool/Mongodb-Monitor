package com.fanggeek.mm.db.dao;

import java.util.List;

import org.jongo.Find;
import org.jongo.MongoCursor;

import com.fanggeek.mm.common.constants.Constants;
import com.fanggeek.mm.dao.model.doc.SystemProfileDocument;
import com.fanggeek.mm.db.MongoDAOSupport;
import com.mongodb.BasicDBObject;

public class ProfileDAO extends MongoDAOSupport<BasicDBObject> {

    @Override
    protected String collectionName() {
        return Constants.DBName.SYSTEM_PROFILE;
    }

    @Override
    public void createIndexs() {
        
    }
    
//    @Override
//    protected List<T> getList(String query, String sort, int limit) {
//        
//        Find find = getCollection().find(query).sort(sort).skip(0).limit(limit);
//        
//        
//        MongoCursor<T> xxx= (MongoCursor<T>) find.as(BasicDBObject.class);
//        
//        
//        List<T> list = toList(xxx);
//        
//        
//        return list;
//    }

}

package com.github.hwhaocool.mm.db.dao;

import java.util.List;

import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.github.hwhaocool.mm.common.constants.Constants;
import com.github.hwhaocool.mm.db.MongoDAOSupport;
import com.mongodb.BasicDBObject;

@Repository
public class ProfileDAO extends MongoDAOSupport<BasicDBObject> {
    
    @Autowired
    @Qualifier("monitorMajorJongo")
    private Jongo jongo;
    
    protected Jongo getJongo() {
        return jongo;
    }

    protected String collectionName() {
        return Constants.DBName.SYSTEM_PROFILE;
    }

    @Override
    public void createIndexs() {
        
    }
    
    public List<BasicDBObject> getList(int limit) {
        Find find = getCollection().find().limit(limit);
        
        MongoCursor<BasicDBObject> xxx= (MongoCursor<BasicDBObject>) find.as(BasicDBObject.class);
        
        List<BasicDBObject> list = toList(xxx);
        
        return list;
    }
    
    @Override
    public List<BasicDBObject> getList(String query, String sort, int limit) {
        
        Find find = getCollection().find(query).sort(sort).skip(0).limit(limit);
        
        
        MongoCursor<BasicDBObject> xxx= (MongoCursor<BasicDBObject>) find.as(BasicDBObject.class);
        
        
        List<BasicDBObject> list = toList(xxx);
        
        return list;
    }

    

}

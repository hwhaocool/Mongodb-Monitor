package com.fanggeek.mm.db.dao;

import com.fanggeek.mm.common.constants.Constants;
import com.fanggeek.mm.dao.model.doc.SystemProfileDocument;
import com.fanggeek.mm.db.MongoDAOSupport;

public class ProfileDAO extends MongoDAOSupport<SystemProfileDocument> {

    @Override
    protected String collectionName() {
        return Constants.DBName.SYSTEM_PROFILE;
    }

    @Override
    public void createIndexs() {
        
    }

}

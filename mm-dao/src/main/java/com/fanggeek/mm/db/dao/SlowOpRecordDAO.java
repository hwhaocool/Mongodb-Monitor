package com.fanggeek.mm.db.dao;

import org.springframework.stereotype.Repository;

import com.fanggeek.mm.common.constants.Constants;
import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;
import com.fanggeek.mm.db.MongoDAOSupport;

@Repository
public class SlowOpRecordDAO extends MongoDAOSupport<SlowOpRecordDocument>{

    @Override
    protected String collectionName() {
        return Constants.DBName.SLOW_OP_RECORD;
    }

    @Override
    public void createIndexs() {
        createIndex("sha1", IndexType.UNIQUE);
    }

}

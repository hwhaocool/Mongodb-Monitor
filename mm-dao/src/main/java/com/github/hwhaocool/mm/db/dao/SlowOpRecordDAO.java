package com.github.hwhaocool.mm.db.dao;

import java.util.List;

import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.github.hwhaocool.mm.common.constants.Constants;
import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.db.MongoDAOSupport;

/**
 * <br> 慢查询 保存
 *
 * @author YellowTail
 * @since 2019-12-25
 */
@Repository
public class SlowOpRecordDAO extends MongoDAOSupport<SlowOpRecordDocument>{
    
    @Autowired
    @Qualifier("saveJongo")
    private Jongo jongo;
    
    protected Jongo getJongo() {
        return jongo;
    }
    

    @Override
    protected String collectionName() {
        return Constants.DBName.SLOW_OP_RECORD;
    }

    @Override
    public void createIndexs() {
        createIndex("sha1", IndexType.UNIQUE);
    }
    
    /**
     * <br> sha1 是否存在
     *
     * @param sha1
     * @return
     * @author YellowTail
     * @since 2019-07-16
     */
    public boolean isSha1Exist(String sha1) {
        long countByQuery = countByQuery("{sha1: #}", sha1);
        if (0L != countByQuery) {
            return true;
        }
        
        return false;
    }
    
    public List<SlowOpRecordDocument> sha1Match(List<String> sha1List) {
        return getListWithDocument("{sha1: {$in: #}}", sha1List, "{sha1: 1}");
    }

    

}

package com.github.hwhaocool.mm.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.bson.types.ObjectId;
import org.jongo.Aggregate.ResultsIterator;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.hwhaocool.mm.common.utils.GenericsUtils;
import com.mongodb.BasicDBObject;

@Component
public abstract class MongoDAOSupport<T extends Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDAOSupport.class);
    
    /**
     * 查询时间 阈值，30秒
     */
    private static final long QUERY_TIME_LIMIT = 50 * 1000;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public MongoDAOSupport() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }
    
    protected abstract String collectionName();
    
    protected abstract Jongo getJongo();
    
    public abstract void createIndexs();
    
    protected void createIndex(String field, IndexType indexType) {
        if (IndexType.UNIQUE.equals(indexType)) {
            createUniqueIndex(Index.of(field, IndexType.ASCENDING));
        } else {
            this.createIndex(Index.of(field, indexType));
        }
    }
    
    protected void createIndex(Index index) {
        BasicDBObject ops = new BasicDBObject("background", true);
        getCollection().getDBCollection().createIndex(index.getIndexs(), ops);
    }
    
    private void createUniqueIndex(Index index) {
        BasicDBObject ops = new BasicDBObject("unique", true);
        ops.put("background", true);
        
        getCollection().getDBCollection().createIndex(index.getIndexs(), ops);
    }

    protected MongoCollection getCollection() {
        return getJongo().getCollection(collectionName());
    }
    
    public void save(T t) {
        getCollection().insert(t);
    }
    
    public void saves(Collection<T> ts) {
        getCollection().insert(ts.toArray());
    }

    public T getById(String id) {
        if (null == id) {
            return null;
        }
        if (! ObjectId.isValid(id)) {
            //不是标准的ObjectId
            return null;
        }
        return getCollection().findOne(new ObjectId(id)).as(entityClass);
    }
    

    /**
     * <br>得到数量
     *
     * @param query
     * @param parameters query的参数
     * @return
     * @author YellowTail
     * @since 2018-10-31
     */
    public long countByQuery(String query, Object... parameters) {
        return getCollection().count(query, parameters);
    }
    
    public List<T> getListWithDocument(String query, Object queryParameters, String projection) {
        return toList((MongoCursor<T>) getCollection().find(query, queryParameters)
                .projection(projection)
                .as(entityClass));
    }
    
    protected List<T> getList(String query, String sort, int limit) {
        
        Find find = getCollection().find(query).sort(sort).skip(0).limit(limit);
        
        MongoCursor<T> xxx= (MongoCursor<T>) find.as(entityClass);
        
        List<T> list = toList(xxx);
        
        return list;
    }

    public static <T> List<T> toList(MongoCursor<T> cursor){
        List<T> datas = new ArrayList<>();
        
        if(cursor == null){
            return datas;
        }
        
        long start = System.currentTimeMillis();
        
        ObjectId objectId = new ObjectId();
        
        //第一次执行  cursor.hasNext()  时，很慢，需要1秒，后续就比较快
        while (cursor.hasNext()) {
            long cost = System.currentTimeMillis() - start;
            if (cost > QUERY_TIME_LIMIT) {
                //超出阈值了，直接退出
                LOGGER.error("error cursorhasNext cost {} objectId {}", cost, objectId);
                break;
            }
            
            T next = cursor.next();
            
            datas.add(next);
        }
        
        //游标 用完了关掉
        try {
            cursor.close();
        } catch (IOException e) {
        }
        
        return datas;
    }
    
    protected <T, R> List<R> toList(ResultsIterator<T> cursor, Function<T, R> transfer){
        List<R> datas = new ArrayList<R>();
        cursor.forEach(t->{
            R r = transfer.apply(t);
            datas.add(r);
        });
        return datas;
    }
    
    
    public static enum IndexType {
        ASCENDING(1),
        DESCENDING(-1),
        GEO2D("2d"),
        GEOHAYSTACK("geoHaystack"),
        GEOSPHERE("2dsphere"),
        HASHED("hashed"),
        TEXT("text"),
        UNIQUE("unique"),
        ;
        private Object val ;
        
        private IndexType(Object val) {
            this.val = val;
        }
        
        public Object getVal() {
            return val;
        }
    }
    
    public static class Index {
        BasicDBObject indexs;
        private Index() {
            indexs = new BasicDBObject();
        }
        
        public static Index of(String field, IndexType IndexType) {
            Index index = new Index();
            index.group(field, IndexType);
            return index;
        }
        
        public Index group(String field, IndexType IndexType) {
            indexs.put(field, IndexType.getVal());
            return this;
        }

        public BasicDBObject getIndexs() {
            return indexs;
        }
    }
    
    
}

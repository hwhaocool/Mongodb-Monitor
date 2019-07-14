package com.fanggeek.mm.db;

import com.fanggeek.be.MongoDocument;
import com.fanggeek.common.utils.GenericsUtils;
import com.fanggeek.common.utils.mongodb.QueryStringBuilder;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.jongo.Aggregate.ResultsIterator;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public abstract class MongoDAOSupport<T extends Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDAOSupport.class);
    
    /**
     * 查询时间 阈值， 7秒
     */
    public static final long QUERY_TIME_LIMIT = 7 * 1000;

    @Autowired
    private Jongo jongo;

    protected Class<T> entityClass;

    public MongoDAOSupport() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }
    
    protected abstract String collectionName();
    
    public abstract void createIndexs();
    
    @PostConstruct
    final public void initIndexs() {
        this.createIndexs();
    }
    
    protected void createIndex(String field, IndexType IndexType) {
        this.createIndex(Index.of(field, IndexType));
    }

    protected MongoCollection getCollection() {
        return jongo.getCollection(collectionName());
    }

    protected MongoCollection getCollection(String collectionName) {
        return jongo.getCollection(collectionName);
    }

    public T getById(Object id) {
        return getCollection().findOne(new ObjectId((String) id)).as(entityClass);
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
    
    protected T get(String query, Object... parameters) {
        return getCollection().findOne(query, parameters).as(entityClass);
    }
    
    protected T getOne(String query, String sort, Object... parameters) {
        MongoCursor<T> results = getCollection().find(query, parameters).sort(sort).limit(1).as(entityClass);
        if(results.hasNext()){
            return results.next();
        }
        return null;
    }
    
    public T getOne(String query) {
        return getCollection().findOne(query).as(entityClass);
    }
    
    public T findOne() {
        return getCollection().findOne().as(entityClass);
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
    
    /**
     * <br>得到数量
     *
     * @param query 查询语句
     * @return
     * @author YellowTail
     * @since 2019-01-26
     */
    public long countByQuery(String query) {
        return getCollection().count(query);
    }
    
    /**
     * <br> 是否存在主键
     *
     * @param id
     * @return
     * @author YellowTail
     * @since 2019-03-02
     */
    public boolean existObjectId(String id) {
        long countByQuery = countByQuery("{_id: #}", new ObjectId(id));
        if (0L != countByQuery) {
            return true;
        }
        
        return false;
    }

    protected List<String> getDistinctList(String distinct, String query) {
        return getCollection().distinct(distinct).query(query).as(String.class);
    }
    
    public List<T> getListWithDocument(String query) {
        return toList((MongoCursor<T>) getCollection().find(query).as(entityClass));
    }
    
    /**
     * <br> 查询列表
     *
     * @param query 查询字符串
     * @param parameters query的参数
     * @return
     * @author YellowTail
     * @since 2019-01-26
     */
    public List<T> getListWithDocument(String query, Object... parameters) {
        return toList((MongoCursor<T>) getCollection().find(query, parameters).as(entityClass));
    }
    
    public List<T> getListWithDocument(String query, String sort, int limit) {
        return toList((MongoCursor<T>) getCollection().find(query).sort(sort).skip(0).limit(limit).as(entityClass));
    }
    
    /**
     * 全量分页查询
     */
    public List<T> getListByOid(String minId, int pagesize) {

        String query = QueryStringBuilder.newBuilder()
            .addField("del_flag_inner:{$ne: 1}")
            .addFieldWithObjectId("_id:{$lt: #}", minId)
            .build();
        
        String sort = "{_id: -1}";
        
        return getList(query, sort, pagesize);
    }
    
    protected List<T> getList(String query, String projection, String sort, int limit) {
        return toList((MongoCursor<T>) getCollection()
                .find(query).projection(projection)
                .sort(sort)
                .limit(limit)
                .as(entityClass));
    }
    
    protected List<T> getList(String query, String sort, int limit) {
        LOGGER.info("start to getList");
        
        Find find = getCollection().find(query).sort(sort).skip(0).limit(limit);
        
        LOGGER.info("already get 1  find");
        
        MongoCursor<T> xxx= (MongoCursor<T>) find.as(entityClass);
        
        LOGGER.info("already get 2  xxx");
        
        List<T> list = toList(xxx);
        
        LOGGER.info("already get 3  list");
        
        return list;
    }
    
    @SuppressWarnings("unchecked")
    protected List<T> getListWithClass(String query, String sort, int limit, Class<? extends T> targetClass) {
        return toList((MongoCursor<T>) getCollection().find(query).sort(sort).skip(0).limit(limit).as(targetClass));
    }

//    public List<T> getByIds(Collection<String> ids) {
//        String query = "{_id: {$in: #}}";
//        List<ObjectId> unitIds = idsToObjectIds(ids);
//        
//        return toList(this.getList(query, unitIds));
//    }
//    
//    /**
//     * <br>通过 _id 列表查询列表，可以指定 projection 来提升查询速度
//     *
//     * @param ids
//     * @param projection
//     * @return
//     * @author YellowTail
//     * @since 2019-01-26
//     */
//    public List<T> getByIdsWithProjection(Collection<String> ids, String projection) {
//        String query = "{_id:{$in:#}}";
//        List<ObjectId> unitIds = idsToObjectIds(ids);
//        
//        return toList((MongoCursor<T>) getCollection().find(query, unitIds).projection(projection).as(entityClass));
//    }
//
//    public static List<ObjectId> idsToObjectIds(Collection<String> ids) {
//        List<ObjectId> objectIds = ids.stream().map(houseId->new ObjectId(houseId)).collect(Collectors.toList());
//        return objectIds;
//    }
//    
//
//    public String save(T t) {
//        Date now = new Date();
//        if (t.getCreateTime() == null) {
//            t.setCreateTime(now);
//        }
//        if (t.getModifyTime() == null) {
//            t.setModifyTime(now);
//        }
//
//        getCollection().save(t);
//        return t.get_id();
//    }
//    
//    public void saves(T... ts) {
//        Date date = new Date();
//        for (T t : ts) {
//            if (t.getCreateTime() == null) {
//                t.setCreateTime(date);
//            }
//        }
//        getCollection().insert(new Object[] {ts});
//    }
//
//    public void saves(Collection<T> ts) {
//        Date date = new Date();
//        for (T t : ts) {
//            if (t.getCreateTime() == null) {
//                t.setCreateTime(date);
//            }
//        }
//
//        getCollection().insert(ts.toArray());
//    }
//
//    /**
//     * <br>更新文档，自动更新修改时间
//     *
//     * @param t
//     * @return
//     */
//    public String update(T t) {
//        return update(t, true);
//    }
//    
//    public String update(T t, boolean setModifyTime) {
//        if(setModifyTime){
//            t.setModifyTime(new Date());
//        }
//
//        getCollection().update(new ObjectId(t.get_id())).with(t);
//        return t.get_id();
//    }
//    
//    /**
//     * <br>更新document
//     * <br>
//     *
//     * @param query  查询条件，自行填充好，不要带占位符 #
//     * @param update 更新语句，比如  inc set 等
//     * @param updateParameters 更新参数
//     * 
//     * @author YellowTail
//     * @since 2018-11-27
//     */
//    public void update(String query, String update, Object... updateParameters) {
//        getCollection().update(query).with(update, updateParameters);
//    }
//    
//    /**
//     * <br>通过 _id 更新一个字段的值
//     *
//     * @param _id
//     * @param field 字段名
//     * @param newValue  新的值
//     * @author YellowTail
//     * @since 2018-11-15
//     */
//    public void set(String _id, String field, Object newValue) {
//        String query = "{_id: #}";
//        
//        set(query, field, newValue,  new ObjectId(_id));
//    }
//    
//    /**
//     * <br>通过一个查询条件，更新一个字段的值
//     *
//     * @param query 查询条件
//     * @param field 字段名
//     * @param newValue 新的值
//     * @param queryParameters 查询语句需要填充的值
//     * @author YellowTail
//     * @since 2018-11-15
//     */
//    public void set(String query, String field, Object newValue, Object... queryParameters) {
//        String update = "{$set: {#: #}}";
//        
//        getCollection().update(query, queryParameters).with(update, new Object[]{field, newValue});
//    }
//
//    /**
//     * <br>对某个字段进行 +1 操作
//     *
//     * @param _id
//     * @param key
//     * @author YellowTail
//     * @since 2019-01-12
//     */
//    public void inc(String _id, String key) {
//        String query = QueryStringBuilder.newBuilder()
//            .addFieldWithObjectId("_id: #", _id)
//            .build();
//        
//        String inc = QueryStringBuilder.newBuilder()
//            .addField("$inc: {#: 1}", key)
//            .build();
//        
//        getCollection().update(query).with(inc);
//    }
//    
//    /**
//     * <br>对某个字段进行 -1 操作
//     *
//     * @param _id
//     * @param key
//     * @author YellowTail
//     * @since 2019-01-12
//     */
//    public void sub(String _id, String key) {
//        String query = QueryStringBuilder.newBuilder()
//            .addFieldWithObjectId("_id: #", _id)
//            .build();
//        
//        String inc = QueryStringBuilder.newBuilder()
//            .addField("$inc: {#: -1}", key)
//            .build();
//        
//        getCollection().update(query).with(inc);
//    }
//
//    /**
//     * <br>软删除
//     * <br>物理删除请使用 remove
//     *
//     * @param id
//     * @author YellowTail
//     * @since 2018-11-15
//     */
//    public void delete(String id) {
//        getCollection().update(new ObjectId(id)).with("{ $set: {del_flag_inner: #}}", Constants.INTEGER_1);
//    }
//    
//    public void delete(ObjectId id) {
//        getCollection().update(id).with("{ $set: {del_flag_inner: #}}", Constants.INTEGER_1);
//    }
    
    /**
     * <br>物理删除
     * <br>软删除请使用 delete
     *
     * @param id
     * @author YellowTail
     * @since 2018-11-15
     */
    public void remove(String id) {
        getCollection().remove(new ObjectId(id));
    }

    public void remove(String query, Object... parameters) {
        getCollection().remove(query, parameters);
    }

    public static <T> List<T> toList(MongoCursor<T> cursor){
        List<T> datas = new ArrayList<>();
        
        if(cursor == null){
            return datas;
        }
        
        long start = System.currentTimeMillis();
        
        ObjectId objectId = new ObjectId();
        
        LOGGER.info("start to hasNext, {}", objectId);
        
        //第一次执行  cursor.hasNext()  时，很慢，需要1秒，后续就比较快
        while (cursor.hasNext()) {
            long cost = System.currentTimeMillis() - start;
            if (cost > QUERY_TIME_LIMIT) {
                //超出阈值了，直接退出
                LOGGER.error("error cursorhasNext cost {} objectId {}", cost, objectId);
                break;
            }
            
            LOGGER.info("normal cursor hasNext cost {} objectId {}", cost, objectId);
            
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
    
    protected <T> List<T> toList(ResultsIterator<T> cursor){
        return toList(cursor, t->t);
    }
    
    protected <T, R> List<R> toList(ResultsIterator<T> cursor, Function<T, R> transfer){
        List<R> datas = new ArrayList<R>();
        cursor.forEach(t->{
            R r = transfer.apply(t);
            datas.add(r);
        });
        return datas;
    }
    
    
    protected void createIndex(Index index) {
        BasicDBObject ops = new BasicDBObject("background", true);
        getCollection().getDBCollection().createIndex(index.getIndexs(), ops);
    }
    
    public static enum IndexType {
        ASCENDING(1),
        DESCENDING(-1),
        GEO2D("2d"),
        GEOHAYSTACK("geoHaystack"),
        GEOSPHERE("2dsphere"),
        HASHED("hashed"),
        TEXT("text"),
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

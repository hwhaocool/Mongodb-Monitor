package com.github.hwhaocool.mm.dao.model.doc;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <br> 字段介绍在 https://docs.mongodb.com/v3.4/reference/database-profiler/index.html
 *
 * @author YellowTail
 * @since 2019-12-27
 */
public class SlowOpRecordDocument {
    
    private ObjectId _id;                     //_id
    
    private String sha1;                    // 对一些字段进行hash，来避免重复
    
    @JsonProperty("ts")
    private Date createTime;                //发生时间

    private String op;                      //操作类型，query
    private String db;                      //库名，xx
    private String ns;                      //表名，xx.odin_checkins
    
    private Object query;                   //查询语句,op=query
    private Object command;                 //查询语句, op=command
    
    private Object originatingCommand;      //查询语句, op=getmore
    
    private Integer keysExamined;           //索引扫描数量
    private Integer docsExamined;           //文档扫描数量
    
    private Boolean cursorExhausted;        //游标
    
    private Integer numYield;               //该操作为了使其他操作完成而放弃的次数
    
    private Object locks;                   //锁信息
    
    private Integer nreturned;              //返回的文档数量
    private Integer responseLength;         //返回字节长度，如果这个数字很大，考虑只返回所需字段
    
    private String protocol;                //协议
    
    private Integer millis;                 //耗时
    
    private String planSummary;             //操作计划描述，IXSCAN #索引扫描，COLLSCAN #全表扫描，#根据索引去检索指定document
    private Object execStats;               //执行状态
    
    private String client;                  //客户端ip
    private String user;                    //帐号信息，
    
    /**
     * <br> ObjectId
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * <br>操作类型，query
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getOp() {
        return op;
    }
    
    public void setOp(String op) {
        this.op = op;
    }
    
    /**
     * <br>库名，
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getDb() {
        return db;
    }
    
    public void setDb(String db) {
        this.db = db;
    }
    
    /**
     * <br>表名，odin_checkins
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getNs() {
        return null == ns ? "null" : ns;
    }
    
    public void setNs(String ns) {
        this.ns = ns;
    }
    
    /**
     * <br>查询语句
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Object getQuery() {
        return query;
    }
    public void setQuery(Object query) {
        this.query = query;
    }
    
    /**
     * <br>查询语句
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Object getCommand() {
        return command;
    }
    
    public void setCommand(Object command) {
        this.command = command;
    }
    
    public Object getOriginatingCommand() {
        return originatingCommand;
    }

    public void setOriginatingCommand(Object originatingCommand) {
        this.originatingCommand = originatingCommand;
    }

    /**
     * <br>索引扫描数量
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getKeysExamined() {
        return null == keysExamined ? 0 : keysExamined;
    }
    
    public void setKeysExamined(Integer keysExamined) {
        this.keysExamined = keysExamined;
    }
    
    /**
     * <br>文档扫描数量
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getDocsExamined() {
        return null == docsExamined ? 0 : docsExamined;
    }
    
    public void setDocsExamined(Integer docsExamined) {
        this.docsExamined = docsExamined;
    }
    
    public Boolean getCursorExhausted() {
        return cursorExhausted;
    }
    
    public void setCursorExhausted(Boolean cursorExhausted) {
        this.cursorExhausted = cursorExhausted;
    }
    
    /**
     * <br>该操作为了使其他操作完成而放弃的次数
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getNumYield() {
        return numYield;
    }
    
    public void setNumYield(Integer numYield) {
        this.numYield = numYield;
    }
    
    /**
     * <br>锁信息
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Object getLocks() {
        return locks;
    }
    
    public void setLocks(Object locks) {
        this.locks = locks;
    }
    
    /**
     * <br>返回的文档数量
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getNreturned() {
        return null == nreturned ? 0 : nreturned;
    }
    
    public void setNreturned(Integer nreturned) {
        this.nreturned = nreturned;
    }
    
    /**
     * <br>返回字节长度，如果这个数字很大，考虑值返回所需字段
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getResponseLength() {
        return null == responseLength ? 0 : responseLength;
    }
    
    public void setResponseLength(Integer responseLength) {
        this.responseLength = responseLength;
    }
    
    /**
     * <br>协议
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    /**
     * <br>耗时, 单位 毫秒
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Integer getMillis() {
        return millis;
    }
    
    public void setMillis(Integer millis) {
        this.millis = millis;
    }
    
    /**
     * <br>操作计划描述
     * <br>IXSCAN #索引扫描，
     * <br>COLLSCAN #全表扫描，
     * <br>fetch #根据索引去检索指定document
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getPlanSummary() {
        return planSummary;
    }
    
    public void setPlanSummary(String planSummary) {
        this.planSummary = planSummary;
    }
    
    /**
     * <br>执行状态
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Object getExecStats() {
        return execStats;
    }
    
    public void setExecStats(Object execStats) {
        this.execStats = execStats;
    }
    
    /**
     * <br>发生时间
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * <br>客户端ip
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getClient() {
        return client;
    }
    
    public void setClient(String client) {
        this.client = client;
    }
    
    /**
     * <br>帐号信息
     *
     * @return
     * @author YellowTail
     * @since 2019-07-15
     */
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sha1 == null) ? 0 : sha1.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SlowOpRecordDocument other = (SlowOpRecordDocument) obj;
        if (sha1 == null) {
            if (other.sha1 != null)
                return false;
        } else if (!sha1.equals(other.sha1))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SlowOpRecordDocument [_id=" + _id + ", sha1=" + sha1 + ", createTime=" + createTime + ", op=" + op
                + ", db=" + db + ", ns=" + ns + ", query=" + query + ", command=" + command + ", originatingCommand="
                + originatingCommand + ", keysExamined=" + keysExamined + ", docsExamined=" + docsExamined
                + ", cursorExhausted=" + cursorExhausted + ", numYield=" + numYield + ", locks=" + locks
                + ", nreturned=" + nreturned + ", responseLength=" + responseLength + ", protocol=" + protocol
                + ", millis=" + millis + ", planSummary=" + planSummary + ", execStats=" + execStats + ", client="
                + client + ", user=" + user + "]";
    }
    
}

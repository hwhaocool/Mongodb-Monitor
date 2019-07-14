package com.fanggeek.msg.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TmplateMsgConfig
 * @Author: AndrewYan
 * @Date: 2019/6/27 10:58
 */
@Configuration
@ConfigurationProperties(prefix = "tmplatemsg")
public class TemplateMsgConfig {

    private String type;

    private String id;

    private String forumId;

    private String memberId;

    private String unitId;

    private String visitId;

    private String id2;

    private String buildingComment;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getBuildingComment() {
        return buildingComment;
    }

    public void setBuildingComment(String buildingComment) {
        this.buildingComment = buildingComment;
    }

    @Override
    public String toString() {
        return "TmplateMsgConfig{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", forumId='" + forumId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", visitId='" + visitId + '\'' +
                ", id2='" + id2 + '\'' +
                ", buildingComment='" + buildingComment + '\'' +
                '}';
    }
}

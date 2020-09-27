package com.github.hwhaocool.mm.dao.config;

import org.jongo.Jongo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hwhaocool.mm.common.constants.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;

@Configuration
@ConfigurationProperties(prefix = "monitor-major-mongodb")
public class MonitorMajorJongoConfig {

    private String    clientUri;
    private String    database;
    private Integer   connectionsPerHost;

    public MongoClient mongoClient() {
        int connections = Constants.DEFAULT_MONGO_CONNECTION_PER_HOST;
        if(connectionsPerHost != null && connectionsPerHost > 0) {
            connections = connectionsPerHost;
        }
        
        Builder option = MongoClientOptions.builder().connectionsPerHost(connections);
        MongoClient mongoClient = new MongoClient(new MongoClientURI(clientUri, option));
        return mongoClient;
    }

    @SuppressWarnings("deprecation")
    @Bean("monitorMajorJongo")
    public Jongo jongo() {
        return new Jongo(mongoClient().getDB(database));
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Integer getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(Integer connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

}

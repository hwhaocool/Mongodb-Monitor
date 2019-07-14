package com.fanggeek.mm.dao.config;

import com.fanggeek.mm.common.constants.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mongodb")
public class JongoConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JongoConfig.class);

    private String    clientUri;
    private String    database;
    private Integer   connectionsPerHost;

    @Bean
    public MongoClient mongoClient() throws Exception {
        LOGGER.info("init mongoClient start");
        
        int connections = Constants.DEFAULT_MONGO_CONNECTION_PER_HOST;
        if(connectionsPerHost != null && connectionsPerHost > 0) {
            connections = connectionsPerHost;
        }
        Builder option = MongoClientOptions.builder().connectionsPerHost(connections);
        MongoClient mongoClient = new MongoClient(new MongoClientURI(clientUri, option));
        return mongoClient;
    }

    @Bean
    public Jongo jongo(MongoClient mongoClient) {
        return new Jongo(mongoClient.getDB(database));
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

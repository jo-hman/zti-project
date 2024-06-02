package com.jochman.zti.auth.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configuration class for MongoDB settings.
 */
@Configuration
public class SimpleMongoConfig {

    /**
     * Bean definition for MongoClient.
     * @return an instance of MongoClient
     */
    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://jochman:UNvfPWLDHBVpc3rM@cluster0.ry6wt0n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    /**
     * Bean definition for MongoTemplate.
     * @return an instance of MongoTemplate
     * @throws Exception if an error occurs while creating the MongoTemplate
     */
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }
}

package org.jboss.eap.quickstarts.kitchensink.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.SocketSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class MongoClientConfig {

    @Value("${mongo.db.uri}")
    private String mongoUrl;

    @Value("${mongo.db.max.connection.pool.size:10}")
    private int maxConnectionPoolSize;

    @Value("${mongo.db.min.connection.pool.size:5}")
    private int minConnectionPoolSize;

    @Value("${mongo.db.max.connection.life.time:86400}")
    private int maxConnectionLifeTime;

    @Value("${mongo.db.max.connection.idle.time:30}")
    private int maxConnectionIdleTime;

    @Value("${mongo.db.max.wait.time:10}")
    private int maxWaitTime;

    @Value("${mongo.db.socket.read.time.out:3}")
    private int readTimeout;

    @Value("${mongo.db.socket.connection.time.out:20}")
    private int connectionTimeout;


    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUrl))
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(maxConnectionPoolSize)
                                .minSize(minConnectionPoolSize)
                                .maxWaitTime(maxWaitTime, SECONDS)
                                .maxConnectionLifeTime(maxConnectionLifeTime, SECONDS)
                                .maxConnectionIdleTime(maxConnectionIdleTime, SECONDS)
                                .build())
                .applyToSocketSettings(socketBuilder ->
                        SocketSettings.builder()
                                .readTimeout(readTimeout,SECONDS)
                                .connectTimeout(connectionTimeout,MILLISECONDS).build())
                .readPreference(ReadPreference.secondaryPreferred())
                .build();

        return MongoClients.create(settings);
    }
}


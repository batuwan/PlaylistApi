package com.batu.playlistapi.Config;


import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.query.CreatePrimaryQueryIndexOptions;
import com.couchbase.client.java.manager.query.CreateQueryIndexOptions;
import com.couchbase.client.java.manager.query.QueryIndexManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class CouchbaseIndexConfiguration {
    private final Cluster couchbaseCluster;
    private final CouchbaseProperties couchbaseProperties;

    public CouchbaseIndexConfiguration(Cluster couchbaseCluster, CouchbaseProperties couchbaseProperties) {
        this.couchbaseCluster = couchbaseCluster;
        this.couchbaseProperties = couchbaseProperties;
    }

    @Bean
    public void createIndexes(){

        QueryIndexManager indexManager = couchbaseCluster.queryIndexes();

        indexManager.createPrimaryIndex(couchbaseProperties.getBucketName(),
                CreatePrimaryQueryIndexOptions.createPrimaryQueryIndexOptions().ignoreIfExists(Boolean.TRUE));
        indexManager.createIndex(couchbaseProperties.getBucketName(), "user_idx",
                Collections.singletonList("userId"), CreateQueryIndexOptions.createQueryIndexOptions().ignoreIfExists(Boolean.TRUE));

    }
}

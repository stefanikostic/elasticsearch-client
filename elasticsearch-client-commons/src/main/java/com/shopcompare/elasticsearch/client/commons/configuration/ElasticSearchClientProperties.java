package com.shopcompare.elasticsearch.client.commons.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties class that stores Elasticsearch related properties.
 */
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties("elasticsearch-client")
@Configuration
public class ElasticSearchClientProperties {

    private String hostName = "localhost";
    private int port = 9200;
    @NonNull
    private String elasticUsername;
    @NonNull
    private String elasticPassword;
    @NonNull
    private String trustStoreLocation;
    @NonNull
    private String trustStorePassword;

}

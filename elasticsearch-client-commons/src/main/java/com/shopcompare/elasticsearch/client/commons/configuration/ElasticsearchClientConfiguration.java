package com.shopcompare.elasticsearch.client.commons.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining beans required for setting up Elasticsearch client.
 */
@Configuration
@RequiredArgsConstructor
public class ElasticsearchClientConfiguration {

    private final ElasticsearchClientProperties elasticSearchClientProperties;
    private static final String HTTPS_SCHEME = "https";

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClientBuilder restClientBuilder =
                RestClient.builder(new HttpHost(elasticSearchClientProperties.getHostName(),
                        elasticSearchClientProperties.getPort(), HTTPS_SCHEME));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new HttpClientConfigCallbackImpl
                (elasticSearchClientProperties);
        restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback);

        RestClient restClient = restClientBuilder.build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}

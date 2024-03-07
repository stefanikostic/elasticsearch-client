package com.shopcompare.elasticsearch.client.commons.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClientConfiguration {

    private static final String LOCALHOST_HOSTNAME = "localhost";
    private static final int PORT = 9200;
    private static final String HTTPS_SCHEME = "https";

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(LOCALHOST_HOSTNAME, PORT, HTTPS_SCHEME));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new HttpClientConfigCallbackImpl();
        restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback);

        RestClient restClient = restClientBuilder.build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}

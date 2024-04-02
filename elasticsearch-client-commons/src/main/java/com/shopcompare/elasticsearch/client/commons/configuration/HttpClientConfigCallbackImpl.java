package com.shopcompare.elasticsearch.client.commons.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;

/**
 * {@link RestClientBuilder.HttpClientConfigCallback} custom implementation that provides http client for Elasticsearch.
 */
@Slf4j
@RequiredArgsConstructor
class HttpClientConfigCallbackImpl implements RestClientBuilder.HttpClientConfigCallback {

    private final ElasticSearchClientProperties elasticSearchClientProperties;

    /**
     * Custom HttpClient configured for Elasticsearch use.
     */
    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(
                    elasticSearchClientProperties.getElasticUsername(),
                    elasticSearchClientProperties.getElasticPassword());
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            File trustStoreLocationFile = new File(elasticSearchClientProperties.getTrustStoreLocation());
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStoreLocationFile,
                    elasticSearchClientProperties.getTrustStorePassword().toCharArray());
            SSLContext sslContext = sslContextBuilder.build();
            httpAsyncClientBuilder.setSSLContext(sslContext);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return httpAsyncClientBuilder;
    }
}

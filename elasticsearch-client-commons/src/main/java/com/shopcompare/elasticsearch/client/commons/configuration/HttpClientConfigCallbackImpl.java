package com.shopcompare.elasticsearch.client.commons.configuration;

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

@Slf4j
class HttpClientConfigCallbackImpl implements RestClientBuilder.HttpClientConfigCallback {

    private static final String ELASTIC_USERNAME = "elastic";
    private static final String ELASTIC_PASSWORD = "TI9rqj3xl_jdvxGcaaFa";
    private static final String PASSWORD_STRING = "password";
    private static final String TRUST_STORE_LOCATION = "C:\\Users\\skostikj\\Downloads\\elasticsearch-8.12" +
            ".1-windows-x86_64\\elasticsearch-8.12.1\\config\\certs\\truststore.p12";

    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(ELASTIC_USERNAME,
                    ELASTIC_PASSWORD);
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            File trustStoreLocationFile = new File(TRUST_STORE_LOCATION);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStoreLocationFile,
                    PASSWORD_STRING.toCharArray());
            SSLContext sslContext = sslContextBuilder.build();
            httpAsyncClientBuilder.setSSLContext(sslContext);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return httpAsyncClientBuilder;
    }
}

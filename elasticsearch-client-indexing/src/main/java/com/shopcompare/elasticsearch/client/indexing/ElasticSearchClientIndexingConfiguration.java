package com.shopcompare.elasticsearch.client.indexing;

import com.shopcompare.elasticsearch.client.commons.configuration.ElasticsearchClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ElasticsearchClientConfiguration.class)
public class ElasticSearchClientIndexingConfiguration {
}

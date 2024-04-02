package com.shopcompare.elasticsearch.client.indexing.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.shopcompare.elasticsearch.client.commons.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Elasticsearch client service that indexes categories.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticsearchClientCategory {
    private static final String CATEGORIES_INDEX = "categories";
    private final ElasticsearchClient elasticsearchClient;

    /**
     * Inserts given categories in elasticsearch categories index.
     *
     * @param categories list of {@link Category}.
     *
     * @throws IOException in case of error during bulk index.
     */
    public void indexCategoriesData(List<Category> categories) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Category category : categories) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(CATEGORIES_INDEX)
                            .id(category.name())
                            .document(category)

                    )
            );
        }

        BulkResponse result = elasticsearchClient.bulk(br.build());

        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                ErrorCause errorCause = item.error();
                if (errorCause != null) {
                    log.error(errorCause.reason());
                }
            }
        }
    }
}

package com.shopcompare.elasticsearch.client.indexing.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.shopcompare.elasticsearch.client.commons.model.Product;
import com.shopcompare.elasticsearch.client.commons.model.ProductsDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticsearchClientProduct {

    private static final String PRODUCTS_INDEX = "products";
    private final ElasticsearchClient elasticsearchClient;

    public void indexProductData(Map<String, List<Product>> products) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Map.Entry<String, List<Product>> productEntry : products.entrySet()) {
            ProductsDocument productsDocument = new ProductsDocument(productEntry.getKey(), productEntry.getValue());
            br.operations(op -> op
                    .index(idx -> idx
                            .index(PRODUCTS_INDEX)
                            .id(productEntry.getKey())
                            .document(productsDocument)

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

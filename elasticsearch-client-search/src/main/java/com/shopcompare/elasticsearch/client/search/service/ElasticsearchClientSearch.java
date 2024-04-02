package com.shopcompare.elasticsearch.client.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.shopcompare.elasticsearch.client.commons.model.Category;
import com.shopcompare.elasticsearch.client.commons.model.Product;
import com.shopcompare.elasticsearch.client.commons.model.ProductsDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Elasticsearch client service that provides search operations of products and categories indexes.
 */
@Service
@RequiredArgsConstructor
public class ElasticsearchClientSearch {

    private static final String PRODUCTS_INDEX = "products";
    private static final int MAX_SIZE_RECORDS = 10000;
    private static final String NAME_KEYWORD_FIELDS = "name.keyword";
    private static final String CATEGORY_KEYWORD_FIELD = "productList.category.keyword";
    private final ElasticsearchClient elasticsearchClient;

    public List<Product> getProductsByName(String name) throws IOException {
        SearchResponse<ProductsDocument> searchResponse = elasticsearchClient.search(r -> r
                        .index(PRODUCTS_INDEX)
                        .query(q -> q
                                .regexp(regexp -> regexp
                                        .field(NAME_KEYWORD_FIELDS)
                                        .value(name))),
                ProductsDocument.class);


        ProductsDocument productsDocument = searchResponse.hits().hits().get(0).source();
        if (productsDocument != null) {
            return productsDocument.productList();
        }
        return null;
    }

    public List<Product> getProductsByCategory(String category) throws IOException {
        List<Product> products = new ArrayList<>();
        SearchResponse<ProductsDocument> searchResponse = elasticsearchClient.search(s -> s
                        .index(PRODUCTS_INDEX)
                        .size(MAX_SIZE_RECORDS)
                        .query(q -> q
                                .term(term -> term
                                        .field(CATEGORY_KEYWORD_FIELD)
                                        .value(category))),
                ProductsDocument.class);


        List<Hit<ProductsDocument>> productDocumentsHits = searchResponse.hits().hits();
        for (Hit<ProductsDocument> productsDocumentHit : productDocumentsHits) {
            ProductsDocument productsDocument = productsDocumentHit.source();
            if (productsDocument != null) {
                List<Product> productsByCategory = productsDocument.productList();
                Product product = productsByCategory.get(0);
                products.add(product);
            }
        }

        return products;
    }

    public List<Category> getCategories() throws IOException {
        SearchResponse<Category> searchResponse = elasticsearchClient.search(s -> s
                .index("categories")
                .size(MAX_SIZE_RECORDS)
                .query(q -> q.matchAll(maq -> maq.queryName("categories"))), Category.class);
        List<Hit<Category>> categoryHits = searchResponse.hits().hits();
        List<Category> categories = new ArrayList<>();
        for (Hit<Category> categoryHit : categoryHits) {
            Category category = categoryHit.source();
            categories.add(category);
        }
        return categories;
    }
}

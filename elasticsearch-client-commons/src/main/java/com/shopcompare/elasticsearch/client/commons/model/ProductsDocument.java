package com.shopcompare.elasticsearch.client.commons.model;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "products")
public record ProductsDocument(
        String name, List<Product> productList
) {}

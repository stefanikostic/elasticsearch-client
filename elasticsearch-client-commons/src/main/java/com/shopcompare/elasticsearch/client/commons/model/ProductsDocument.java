package com.shopcompare.elasticsearch.client.commons.model;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * Representing Products document.
 * It stores the name of the product and a list of listings of the same product in different shops.
 *
 * @param name standard name of the product, usually more normalized form of the product name.
 * @param productList contains all the listings of the product from different shop websites.
 */
@Document(indexName = "products")
public record ProductsDocument(
        String name, List<Product> productList
) {}

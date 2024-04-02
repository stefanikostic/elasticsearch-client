package com.shopcompare.elasticsearch.client.commons.model;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * Class representing categories document.
 *
 * @param id category id.
 * @param name category name.
 * @param subCategories subCategories of the category.
 */
@Document(indexName = "categories")
public record Category (int id, String name, List<Category> subCategories) { }

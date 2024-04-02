package com.shopcompare.elasticsearch.client.commons.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Class representing Product details.
 * @param name product name.
 * @param shopName name of the shop where the product belongs to.
 * @param category category name of the product.
 * @param link URL to the product view.
 * @param image link to product image resource.
 * @param isAvailable states whether product is in stock.
 * @param originalPrice initial price of the product.
 * @param promotionalPrice promotional price of the product on sale.
 * @param timestamp timestamp when the product has been entered into Elasticsearch.
 */
public record Product(@Id String name,
                      @Field(type = FieldType.Text, name = "shopName")
                      String shopName,
                      @Field(type = FieldType.Text, name = "category")
                      String category,
                      @Field(type = FieldType.Text, name = "link")
                      String link,
                      @Field(type = FieldType.Text, name = "image")
                      String image,
                      @Field(type = FieldType.Boolean, name = "isAvailable")
                      boolean isAvailable,
                      @Field(type = FieldType.Double, name = "originalPrice")
                      Double originalPrice,
                      @Field(type = FieldType.Double, name = "promotionalPrice")
                      Double promotionalPrice,
                      @Field(type = FieldType.Date, name = "timestamp")
                      Date timestamp)
{}

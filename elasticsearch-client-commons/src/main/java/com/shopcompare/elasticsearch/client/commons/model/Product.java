package com.shopcompare.elasticsearch.client.commons.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

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

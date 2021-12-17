package com.example.mybatis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "product")
public class Products {
    @Id
    private Long id;

    private String name;

    @Field(type = FieldType.Double, name = "price")
    private double price;

    @JsonProperty(value = "user_id")
    private Long userId;

    @Field(type = FieldType.Double, name = "quantity")
    private Double quantity;

}

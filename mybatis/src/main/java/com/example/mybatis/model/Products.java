package com.example.mybatis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class Products {
    private Long id;
    private String name;
    private double price;
    @JsonProperty(value = "user_id")
    private Long userId;


}

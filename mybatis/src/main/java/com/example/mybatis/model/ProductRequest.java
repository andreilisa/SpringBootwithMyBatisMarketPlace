package com.example.mybatis.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {
    private String name;
    @NotNull(message = "Price by product not be 0 or negative number")
    private double price;
}

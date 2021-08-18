package com.example.mybatis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like implements Serializable {
    private boolean isLiked;
    @JsonProperty(value = "productid")
    private Long productId;
    @JsonProperty(value = "userid")
    private Long userId;


    private Long id;

    public Like(boolean isLiked, Long productId, Long userId) {
        this.isLiked = isLiked;
        this.productId = productId;
        this.userId = userId;
    }
}

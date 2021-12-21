package com.example.mybatis.service;

import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.model.Products;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ListOfAllProductsService {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ProductMapper productMapper;

    public Page<Products> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return productMapper.findByPage();
    }

    public Object getProductsById(Long id, String name, Long userId) {
        if (id == null && name == null && userId == null) {
           return exceptionAppend(" please put id of product or name with userId together");
        }
        if (id != null && name != null && userId != null) {
            return exceptionAppend(" you can only find product by id or name together with userId");
        }
        if (id != null && name == null && userId != null) {
            return exceptionAppend("userId must be together only  with name");
        }
        if (id != null && name != null) {
            return exceptionAppend("name must be together only with userId");
        }
        if (id != null) {
            Query query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("id", id))
                    .build();
            SearchHits<Products> searchHits = elasticsearchRestTemplate.search(query, Products.class);

            return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        } else {
            if (name != null && userId != null) {
                Query query = new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.matchQuery("name.keyword", name))
                        .withFilter(QueryBuilders.matchQuery("userId", userId))
                        .build();
                SearchHits<Products> searchHits = elasticsearchRestTemplate.search(query, Products.class);

                return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
            } else {
                if (name != null) {
                    return exceptionAppend("name must be together with userId");
                } else
                    return exceptionAppend("userId must be together with name");
            }
        }
    }

    public Object exceptionAppend(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
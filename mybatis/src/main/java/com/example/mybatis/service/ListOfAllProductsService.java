package com.example.mybatis.service;

import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.model.Products;
import com.example.mybatis.model.ProductsRequestElastic;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ListOfAllProductsService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<Products> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return productMapper.findByPage();
    }

    public List<Products> getProductsById(ProductsRequestElastic productsRequestElastic) {

        Query query = new NativeSearchQueryBuilder()
                .withQuery(productsRequestElastic.createQuery(productsRequestElastic))
                .build();
        SearchHits<Products> searchHits = elasticsearchRestTemplate.search(query, Products.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());

    }


}
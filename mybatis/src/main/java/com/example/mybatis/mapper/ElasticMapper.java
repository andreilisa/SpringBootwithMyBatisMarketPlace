package com.example.mybatis.mapper;
import com.example.mybatis.model.Products;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticMapper extends ElasticsearchRepository<Products, Long> {

}


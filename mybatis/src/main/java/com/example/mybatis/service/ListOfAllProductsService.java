package com.example.mybatis.service;

import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
@Service
public class ListOfAllProductsService {
    @Autowired
    private ProductMapper productMapper;



    public List<Products> findAll(int pageNumber, int pageSize) {
        int pageable = Pageable.UNKNOWN_NUMBER_OF_PAGES;
        List<Products> products = productMapper.findAll(pageable);
        return products;
    }

}


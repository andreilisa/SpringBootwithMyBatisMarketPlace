package com.example.mybatis.service;

import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.model.Products;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class ListOfAllProductsService {
    @Autowired
    private ProductMapper productMapper;

    public Page<Products> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return productMapper.findByPage();
    }

}


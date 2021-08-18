package com.example.mybatis.controller;

import com.example.mybatis.model.Products;
import com.example.mybatis.service.ListOfAllProductsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api
@RestController
@RequestMapping("/list-all-products")
public class ListAllProductsController {
   @Autowired
   private ListOfAllProductsService listOfAllProductsService;



    @GetMapping("")
    public ResponseEntity<List<Products>> getAllProducts(@RequestParam("page-number") int pageNumber,
                                                         @RequestParam("page-size") int pageSize) {

        List<Products> pageableProducts = listOfAllProductsService.findAll(pageNumber, pageSize);
        Iterator<Products> iterator = pageableProducts.stream().iterator();
        List<Products> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);
        return new  ResponseEntity<>(products, HttpStatus.OK);

    }
}


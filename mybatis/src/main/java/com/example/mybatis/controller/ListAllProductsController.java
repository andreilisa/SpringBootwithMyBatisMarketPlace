package com.example.mybatis.controller;

import com.example.mybatis.model.Products;
import com.example.mybatis.service.ListOfAllProductsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api
@RestController
@RequestMapping("/list-all-products")
@Validated
public class ListAllProductsController {
   @Autowired
   private ListOfAllProductsService listOfAllProductsService;



    @GetMapping("")
    public ResponseEntity<List<Products>> getAllProducts(@RequestParam("page-number") @Min(0) int pageNumber,
                                                         @RequestParam("page-size") @Min(1) int pageSize) {

        List<Products> pageableProducts = listOfAllProductsService.findByPage(pageNumber, pageSize);
        Iterator<Products> iterator = pageableProducts.stream().iterator();
        List<Products> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);
        return new  ResponseEntity<>(products, HttpStatus.OK);
    }
}


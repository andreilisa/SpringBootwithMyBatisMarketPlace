package com.example.mybatis.controller;

import com.example.mybatis.mapper.ElasticMapper;
import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.ProductNotFoundException;
import com.example.mybatis.model.ProductRequest;
import com.example.mybatis.model.Products;
import com.example.mybatis.model.User;
import com.example.mybatis.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api
@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    @Autowired
    LikeService likeService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ElasticMapper elasticMapper;

    public User getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.findByUsername(authentication.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value = "create product ",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @PostMapping("products")
    public Products create(@Valid @RequestBody ProductRequest productRequest) {
        Products products = new Products();
        products.setName(productRequest.getName());
        products.setPrice(productRequest.getPrice());
        products.setUserId(getCurrentUserId().getId());
        products.setQuantity(productRequest.getQuantity());
        productMapper.save(products);
        elasticMapper.save(products);
        return products;
    }

    @ApiOperation(
            value = "update product by id ",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @PutMapping("products{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody ProductRequest products1, @PathVariable Long id) throws Exception {
        Products products = productMapper.findById(id);
        if (getCurrentUserId().getId().equals(products.getUserId())) {
            products.setName(products1.getName());
            products.setPrice(products1.getPrice());
            products.setQuantity(products1.getQuantity());
            productMapper.update(products);
            elasticMapper.save(products);
            return HttpStatus.OK + " \n Product Update with success";
        } else throw new ProductNotFoundException("No value present");
    }


    @ApiOperation(
            value = "delete product by id ",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @DeleteMapping("products{id}")
    public String delete(@PathVariable Long id) {
        Products products = productMapper.findById(id);
        if (getCurrentUserId().getId().equals(products.getUserId())) {
            productMapper.deleteById(id);
            return (HttpStatus.OK) + "\n Object deleted";
        } else
             throw new ProductNotFoundException("Product not found");
    }

    @ApiOperation(
            value = "show product by id",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @GetMapping("products{id}")
    public Products product(@RequestParam Long id) {
        Products products = productMapper.findById(id);
        if (!getCurrentUserId().getId().equals(products.getUserId()))
            productMapper.show(id);
        return products;
    }

    @ApiOperation(
            value = "like product",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @RequestMapping(value = "like", method = RequestMethod.PATCH)
    public String likeProduct(@RequestParam Long id) {

        return likeService.likeProducts(id);
    }

    @ApiOperation(
            value = "dislike product",
            authorizations = {
                    @Authorization(value = "Bearer")})
    @RequestMapping(value = "dislike", method = RequestMethod.PATCH)

    public String dislikeProduct(@RequestParam Long id) {

        return likeService.dislikeProduct(id);
    }
}
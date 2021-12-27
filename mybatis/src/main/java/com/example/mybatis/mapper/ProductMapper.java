package com.example.mybatis.mapper;

import com.example.mybatis.model.Products;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("select id,name,price,user_id as userId from products")
    List<Products> findAll(int pageable);

    @Insert("insert into products(name,price, user_id, quantity) values(#{name}, #{price},#{userId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Products products);

    @Select("select id,name,price,user_id as userId from products where id = #{id}")
    Products findById(Long id);

    @Delete("delete  from products where id = #{id}")
    void deleteById(Long id);

    @Select("update products set name=#{name}, price=#{price}, quantity=#{quantity} where id = #{id}")
    void update(Products products);

    @Select("select name, price from products where id =#{id}")
    void show(Long products);

    @Select("select id,name,price,user_id as userId from products")
    Page<Products> findByPage();


}

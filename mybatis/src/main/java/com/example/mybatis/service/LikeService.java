package com.example.mybatis.service;


import com.example.mybatis.mapper.LikeMapper;
import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    LikeMapper likeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    public User getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.findByUsername(authentication.getName());
    }

    @Transactional
    public String likeProducts(Long id) {
        Products product = productMapper.findById(id);
        User user = getCurrentUserId();

        if (product != null) {

            if (product.getUserId().equals(user.getId())) {
                throw new BadRequestException(" \n  not possible to like your own product!");
            }

            Like like = likeMapper.findByUserIdAndProdId(user.getId(), product.getId());
            if (like != null) {

                if (like.isLiked()) {
                    likeMapper.deleteById(like.getId());
                } else {
                    likeMapper.likeProduct(product.getId(), user.getId());
                }
            } else {

                likeMapper.save(new Like(true, product.getId(), user.getId()));
            }
            return (HttpStatus.OK) + "\n product liked";
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }


    @Transactional
    public String dislikeProduct(Long id) {
        Products product = productMapper.findById(id);
        User user = getCurrentUserId();

        if (product != null) {

            if (product.getUserId().equals(user.getId())) {
                throw new BadRequestException(" \n  not possible to unlike your own product!");
            }

            Like like = likeMapper.findByUserIdAndProdId(user.getId(), product.getId());
            if (like != null) {

                if (!like.isLiked()) {
                    likeMapper.deleteById(like.getId());
                } else {
                    likeMapper.dislikeProduct(product.getId(), user.getId());
                }
            } else {

                likeMapper.save(new Like(false, product.getId(), user.getId()));
            }
            return (HttpStatus.OK) + "\n product unliked";
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }
}
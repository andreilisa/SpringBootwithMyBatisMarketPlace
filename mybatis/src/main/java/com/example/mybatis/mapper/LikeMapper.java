package com.example.mybatis.mapper;

import com.example.mybatis.model.Like;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface LikeMapper {

    @Select("select isliked as isLiked, productid as productId, userid as userId, id from rating where userid=#{userId} and productid=#{productId}")
    Like findByUserIdAndProdId(Long userId, Long productId);

    @Delete("delete from rating where id=#{id}")
    void deleteById(Long id);

    @Select("update rating set isliked = false where  productid =#{productId} and  userid =#{userId}")
    void dislikeProduct(Long productId, Long userId);

    @Select("update rating set isliked = true where productid =#{productId} and userid =#{userId}")
    void likeProduct(Long productId, Long userId);

    @Insert("insert into rating(isliked, productid, userid) values(#{isLiked}, #{productId}, #{userId})")
    void save(Like like);
}

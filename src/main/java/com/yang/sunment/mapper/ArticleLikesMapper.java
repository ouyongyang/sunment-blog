package com.yang.sunment.mapper;


import com.yang.sunment.model.ArticleLikes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章点赞sql
 */
@Mapper
@Repository
public interface ArticleLikesMapper {

    @Delete("delete from article_likes where article_id=#{articleId}")
    void deleteArticleLikesByArticleId(long articleId);

    @Select("select like_date As likeDate from article_likes where article_id=#{articleId} and original_author=#{originalAuthor} and liker_id=#{likerId}")
    ArticleLikes isLiked(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("likerId") int likerId);

    @Insert("insert into article_likes(article_id,original_author,liker_id,like_date) values(#{articleId},#{originalAuthor},#{likerId},#{likeDate})")
    void insertArticleLikes(ArticleLikes articleLikes);
}

package com.yang.sunment.mapper;


import com.yang.sunment.model.CommentLikes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 评论点赞sql
 */
@Mapper
@Repository
public interface CommentLikesMapper {

    @Delete("delete from comment_likes where article_id=#{articleId}")
    void deleteCommentLikesByArticleId(long articleId);

    @Select("select like_date AS likeDate from comment_likes where article_id=#{articleId} and original_author=#{originalAuthor} and comment_id=#{commentId} and liker_id=#{likerId}")
    CommentLikes isLiked(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("commentId") long commentId, @Param("likerId") int likerId);

    @Insert("insert into comment_likes(article_id,original_author,comment_id,liker_id,like_date) values(#{articleId},#{originalAuthor},#{commentId},#{likerId},#{likeDate})")
    void insertCommentLikes(CommentLikes commentLikes);
}

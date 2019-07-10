package com.yang.sunment.mapper;

import com.yang.sunment.model.Categories;
import com.yang.sunment.model.Comment;
import com.yang.sunment.model.CommentInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 评论sql
 */
@Repository
@Mapper
public interface CommentMapper {

    @Delete("delete from comment where article_id=#{articleId}")
    void deleteCommentByArticleId(long articleId);

    @Select("select id,father_id AS fatherId,article_id AS articleId,comment_id  AS commentId,response_id AS responseId,original_author AS originalAuthor,comment_date AS commentDate,comment_content AS commentContent,likes from comment where article_id=#{articleId} and original_author=#{originalAuthor} and father_id=#{fatherId} order by id desc")
    List<Comment> findCommentByArticleIdOriginalAuthorFatherId(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("fatherId") long fatherId);

    @Select("select id,father_id AS fatherId,article_id AS articleId,comment_id  AS commentId,response_id AS responseId,original_author AS originalAuthor,comment_date AS commentDate,comment_content AS commentContent,likes from comment where article_id=#{articleId} and original_author=#{originalAuthor} and father_id=#{fatherId}")
    List<Comment> findComment(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("fatherId") long fatherId);

    @Insert("insert into comment(article_id,original_author,father_id,comment_id,response_id,comment_date,likes,comment_content)" +
            " values(#{articleId},#{originalAuthor},#{fatherId},#{commentId},#{responseId},#{commentDate},#{likes},#{commentContent})")
    void insertComment(Comment comment);

    @Update("update comment set likes=likes+1 where article_id=#{articleId} and original_author=#{originalAuthor} and id=#{id}")
    void updateLike(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("id") long id);

    @Select("select IFNULL(max(likes),0) from comment where article_id=#{articleId} and original_author=#{originalAuthor} and id=#{id}")
    int findLikes(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("id") long id);

    @Select("select id,father_id AS fatherId,article_id AS articleId,original_author AS originalAuthor,comment_id AS commentId,response_id AS responseId,comment_date AS commentDate,comment_content AS commentContent from comment where comment_id=#{commentId} order by id desc")
    List<Comment> getPersonalComment(@Param("commentId") int commentId);

    @Select("select count(*) from comment where father_id=#{id}")
    int countReplyNumById(@Param("id") long id);

    @Select("select count(*) from comment where id>#{id} and father_id=#{fatherId} and response_id=#{responseId}")
    int countReplyNumByIdAndRespondentId(@Param("fatherId") long fatherId, @Param("responseId") int respondentId, @Param("id") long id);

    @Select("select article_id AS articleId,original_author AS originalAuthor,father_id AS fatherId,comment_id AS commentId,response_id AS responseId,comment_date AS commentDate,comment_content AS commentContent from comment order by id desc")
    List<Comment> findFiveNewComment();

    @Select("select count(*) from comment")
    int commentNum();

    @Select("select c.id,a.article_title As articleTitle,s.username As commentUsername,c.comment_content As commentContent,c.comment_date As commentDate,c.likes from comment c LEFT JOIN sec_account s ON c.comment_id=s.id\n" +
            "LEFT JOIN article a ON c.article_id=a.article_id\n" +
            "order by c.id")
    List<CommentInfo> getArticleComment();

    @Delete("delete from comment where id=#{id}")
    void deleteComment(long id);
}

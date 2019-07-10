package com.yang.sunment.service;


import com.yang.sunment.model.CommentLikes;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:评论点赞记录业务操作
 */
@Service
public interface CommentLikesService {

    /**
     * 通过文章id删除该文章的所有点赞记录
     * @param articleId 文章id
     */
    void deleteCommentLikesByArticleId(long articleId);

    /**
     * 评论是否点赞
     * @return true -- 已经点过赞  false -- 还没有点过赞
     */
    boolean isLiked(long articleId, String originalAuthor, long commentId, String username);

    /**
     * 保存评论中点赞的记录
     * @param commentLikes
     */
    void insertCommentLikes(CommentLikes commentLikes);

}

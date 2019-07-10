package com.yang.sunment.service.impl;

import com.yang.sunment.mapper.CommentLikesMapper;
import com.yang.sunment.model.CommentLikes;
import com.yang.sunment.service.CommentLikesService;
import com.yang.sunment.service.SecAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:
 */
@Service
public class CommentLikesServiceImpl implements CommentLikesService {

    @Autowired
    CommentLikesMapper commentLikesMapper;
    @Autowired
    SecAccountService secAccountService;
    /**
     * 通过文章id删除该文章的所有点赞记录
     * @param articleId 文章id
     */
    @Override
    public void deleteCommentLikesByArticleId(long articleId) {
        commentLikesMapper.deleteCommentLikesByArticleId(articleId);
    }
    /**
     * 评论是否点赞
     * @return true -- 已经点过赞  false -- 还没有点过赞
     */    @Override
    public boolean isLiked(long articleId, String originalAuthor, long commentId, String username) {
        return commentLikesMapper.isLiked(articleId, originalAuthor, commentId, secAccountService.findIdByUsername(username)) != null;
    }
    /**
     * 保存评论中点赞的记录
     * @param commentLikes
     */
    @Override
    public void insertCommentLikes(CommentLikes commentLikes) {
        commentLikesMapper.insertCommentLikes(commentLikes);
    }
}

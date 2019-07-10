package com.yang.sunment.service.impl;

import com.yang.sunment.mapper.ArticleLikesMapper;
import com.yang.sunment.model.ArticleLikes;
import com.yang.sunment.service.ArticleLikesService;
import com.yang.sunment.service.SecAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:
 */
@Service
public class ArticleLikesServiceImpl implements ArticleLikesService {

    @Autowired
    ArticleLikesMapper articleLikesMapper;
    @Autowired
    SecAccountService secAccountService;


    /**
     * 通过文章id删除文章点赞记录
     * @param articleId 文章id
     */
    @Override
    public void deleteArticleLikesByArticleId(long articleId) {
        articleLikesMapper.deleteArticleLikesByArticleId(articleId);
    }

    /**
     * 文章是否已经点过赞
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @param username 点赞人
     * @return true--已经点过赞  false--没有点赞
     */
    @Override
    public boolean isLiked(long articleId, String originalAuthor, String username) {
        ArticleLikes articleLikes= articleLikesMapper.isLiked(articleId, originalAuthor, secAccountService.findIdByUsername(username));

        return articleLikes != null;
    }

    /**
     * 保存文章中点赞的记录
     * @param articleLikes
     */
    @Override
    public void insertArticleLikes(ArticleLikes articleLikes) {
        articleLikesMapper.insertArticleLikes(articleLikes);
    }


}

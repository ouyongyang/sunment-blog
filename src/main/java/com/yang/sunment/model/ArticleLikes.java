package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章点赞记录
 */
@Data
public class ArticleLikes {
    /**
     * id
     */
    private long id;

    /**
     * 文章id
     */
    private long articleId;

    /**
     * 点赞人id
     */
    private int likerId;

    /**
     * 原作者
     */
    private String originalAuthor;

    /**
     * 点赞时间
     */
    private String likeDate;

    public ArticleLikes() {
    }

    public ArticleLikes(long articleId, String originalAuthor, int likerId, String likeDate) {
        this.articleId = articleId;
        this.originalAuthor = originalAuthor;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}

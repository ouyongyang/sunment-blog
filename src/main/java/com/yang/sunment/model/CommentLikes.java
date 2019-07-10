package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章评论点赞记录
 */
@Data
public class CommentLikes {

    /**
     * id
     */
    private long id;

    /**
     * 文章id
     */
    private long articleId;

    /**
     * 评论者id
     */
    private long commentId;

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

    public CommentLikes() {
    }

    public CommentLikes(long articleId, String originalAuthor, int commentId, int likerId, String likeDate) {
        this.articleId = articleId;
        this.originalAuthor = originalAuthor;
        this.commentId = commentId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}

package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章评论
 */
@Data
public class Comment {

    private long id;

    /**
     * 回复的父id 若是评论则为 0，则是评论中的回复则为对应评论的id
     */
    private long fatherId=0;

    /**
     * 评论的文章id
     */
    private long articleId;

    /**
     * 评论者id
     */
    private int commentId;

    /**
     * 被回复者id
     */
    private int responseId;

    /**
     * 评论的文章的原作者
     */
    private String originalAuthor;

    /**
     * 评论日期
     */
    private String commentDate;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 点赞数
     */
    private int likes=0;


}

package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/4/17 19:24
 * Describe: 文章评论
 */
@Data
public class CommentInfo {

    /**
     * id
     */
    private int id;

    /**
     * 文章名
     */
    private String articleTitle;

    /**
     * 评论人
     */
    private String commentUsername;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论日期
     */
    private String commentDate;

    /**
     * 点赞数
     */
    private int likes=0;


}

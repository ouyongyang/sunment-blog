package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 文章
 */
@Data
public class Article {
    /**
     * id
     */
    private int id;

    /**
     * 文章id
     */
    private long articleId;

    /**
     * 文章作者
     */
    private String author;

    /**
     * 文章原作者
     */
    private String originalAuthor;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章标签
     */
    private String articleTags;

    /**
     * 文章类型
     */
    private String articleType;

    /**
     * 文章分类
     */
    private String articleCategories;

    /**
     * 文章摘要
     */
    private String articleAbstract;

    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    private String articleUrl;

    /**
     * 文章点赞数
     */
    private int articleLikes = 0;

    /**
     * 发布文章时间
     */
    private String publishDate;

    /**
     * 文章最后一次修改时间
     */
    private String updateDate;


    /**
     * 上一篇文章id
     */
    private long lastArticleId;

    /**
     * 下一篇文章id
     */
    private long nextArticleId;



}

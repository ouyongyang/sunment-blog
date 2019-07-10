package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 联系管理员
 */
@Data
public class ContactAdmin {

    /**
     * 联系管理员表id
     */
    private int id;

    /**
     * 发送内容
     */
    private String privateWord;

    /**
     * 发送者
     */
    private int publisherId;

    /**
     * 回复者
     */
    private int  replierId;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 发布时间
     */
    private String publisherDate;

    public ContactAdmin() {
    }

    public ContactAdmin(String privateWord, int publisherId, String publisherDate) {
        this.privateWord = privateWord;
        this.publisherId = publisherId;
        this.publisherDate = publisherDate;
    }
}

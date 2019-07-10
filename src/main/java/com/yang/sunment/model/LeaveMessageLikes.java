package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 留言中点赞
 */
@Data
public class LeaveMessageLikes {

    /**
     * 留言点赞表id
     */
    private long id;

    /**
     * 留言页的HTML名字
     */
    private String pageName;

    /**
     * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
     */
    private int fatherId=0;

    /**
     * 点赞人
     */
    private int likerId;

    /**
     * 点赞时间
     */
    private String likeDate;

    public LeaveMessageLikes() {
    }

    public LeaveMessageLikes(String pageName, int fatherId, int likerId, String likeDate) {
        this.pageName = pageName;
        this.fatherId = fatherId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}

package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 留言
 */
@Data
public class LeaveMessage {

    /**
     * 留言表id
     */
    private int id;

    /**
     * 留言页的HTML名字
     */
    private String pageName;

    /**
     * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
     */
    private int fatherId=0;

    /**
     * 留言者
     */
    private int leaverId;

    /**
     * 被回复者
     */
    private int responseId;

    /**
     * 留言日期
     */
    private String leaveMessageDate;

    /**
     * 留言点赞
     */
    private int likes=0;

    /**
     * 留言内容
     */
    private String leaveMessageContent;

    public LeaveMessage() {
    }

    public LeaveMessage(String pageName, int leaverId, int responseId, String leaveMessageDate, String leaveMessageContent) {
        this.pageName = pageName;
        this.leaverId = leaverId;
        this.responseId = responseId;
        this.leaveMessageDate = leaveMessageDate;
        this.leaveMessageContent = leaveMessageContent;
    }
}

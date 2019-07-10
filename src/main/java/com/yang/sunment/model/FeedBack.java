package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 反馈
 */
@Data
public class FeedBack {

    /**
     * 反馈表id
     */
    private int id;

    /**
     * 反馈内容
     */
    private String feedBackContent;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 反馈人
     */
    private int personId;

    /**
     * 反馈时间
     */
    private String feedBackDate;

}

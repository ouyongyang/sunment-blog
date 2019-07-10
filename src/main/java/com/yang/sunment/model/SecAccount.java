package com.yang.sunment.model;

import lombok.Data;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/11/23 11:49
 * Describe: 用户实体类
 */
@Data
public class SecAccount {

    private int id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 个人简介
     */
    private String personalProfile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private String lastLogintime;

    /**
     * 头像地址
     */
    private String avatarImagesUrl;

    /**
     * 管理员通过注册的员工（0：不通过   1：通过）
     */
    private String type;

    private List<Role> roles;

    public SecAccount() {
    }

    public SecAccount(String phone, String username, String password, String gender) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public SecAccount(String phone, String username, String password, String gender, String trueName, String birthday, String personalProfile, String email, String lastLogintime, String avatarImagesUrl) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.trueName = trueName;
        this.birthday = birthday;
        this.personalProfile = personalProfile;
        this.email = email;
        this.lastLogintime = lastLogintime;
        this.avatarImagesUrl = avatarImagesUrl;
    }
}

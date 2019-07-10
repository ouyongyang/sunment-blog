package com.yang.sunment.service;

import com.yang.sunment.model.SecAccount;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: OYY
 * @Date: 2018/11/24 15:54
 * Describe: user业务操作
 */
public interface SecAccountService {

    /**
     *  通过手机号查找注册用户
     * @param phone 手机号
     * @return 用户
     */
    SecAccount findAccountByPhone(String phone);

    /**
     * 通过id查找用户名
     * @param id
     * @return
     */
    String findUsernameById(int id);

    /**
     * 注册用户
     * @param secAccount 用户
     * @return "1"--用户存在，插入失败             "2"--用户不存在，插入成功
     */
    @Transactional
    String insert(SecAccount secAccount);

    /**
     * 通过手机号查找用户id
     * @param phone 手机号
     * @return 用户id
     */
    int findUserIdByPhone(String phone);

    /**
     * 通过手机号修改密码
     * @param phone 手机号
     * @param password 密码
     */
    void updatePasswordByPhone(String phone, String password);

    /**
     * 通过用户名获得手机号
     * @param username 用户名
     * @return 手机号
     */
    String findPhoneByUsername(String username);

    /**
     * 通过用户名查找id
     * @param username
     * @return
     */
    int findIdByUsername(String username);

    /**
     * 通过手机号查找用户名
     * @param phone 手机号
     * @return 用户名
     */
    SecAccount findUsernameByPhone(String phone);

    /**
     * 更新最近登录时间
     * @param username 用户名
     * @param lastLogintime 最近登录时间
     */
    void updateLastLoginTime(String username, String lastLogintime);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true--存在  false--不存在
     */
    boolean usernameIsExit(String username);

    /**
     * 通过手机号判断是否为超级用户
     * @param phone 手机号
     * @return true--超级管理员  false--非超级管理员
     */
    boolean isSuperAdmin(String phone);

    /**
     * 更改头像
     * @param avatarImgUrl 头像地址
     */
    @Transactional
    void updateAvatarImgUrlById(String avatarImgUrl, int id);

    /**
     * 获得头像url
     * @param id
     * @return
     */
    JSONObject getHeadPortraitUrl(int id);

    /**
     * 获得用户个人信息
     * @return
     */
    JSONObject getAccountPersonalInfoByUsername(String username);

    /**
     * 保存用户个人信息
     * @param secAccount 个人信息
     * @param username 当前更改的用户
     * @return
     */
    JSONObject savePersonalInfo(SecAccount secAccount, String username);

    /**
     * 获得用户头像的地址
     * @return 头像的url
     */
    String getHeadPortraitUrlByUserId(int userId);

    /**
     * 统计总用户量
     * @return
     */
    int countUserNum();

    /**
     * 获得用户管理
     */
    JSONObject getSecAccountManagement(int rows, int pageNum);

    /**
     * 通过员工
     * @return
     */
    @Transactional
    int agreeSecAccount(int id);

    /**
     * 不通过员工
     * @return
     */
    @Transactional
    int disAgreeSecAccount(int id);

    /**
     * 删除用户
     * @param id 用户id
     * @return 1--删除成功   0--删除失败
     */
    @Transactional
    int deleteSecAccount(long id);
}

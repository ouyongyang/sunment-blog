package com.yang.sunment.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.sunment.constant.RoleConstant;
import com.yang.sunment.mapper.SecAccountMapper;
import com.yang.sunment.model.Categories;
import com.yang.sunment.model.SecAccount;
import com.yang.sunment.service.SecAccountService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Oyy
 * @Date: 2019/1/11 15:56
 * Describe: 员工表接口具体业务逻辑
 */
@Service
@Slf4j
public class SecAccountServiceImpl implements SecAccountService {

    @Autowired
    SecAccountMapper secAccountMapper;

    @Override
    public SecAccount findAccountByPhone(String phone) {
        return secAccountMapper.findAccountByPhone(phone);
    }

    @Override
    public String findUsernameById(int id) {
        return secAccountMapper.findUsernameById(id);
    }

    @Override
    public String insert(SecAccount secAccount) {
        if(userIsExit(secAccount.getPhone())){
            return "1";
        }
        if("男".equals(secAccount.getGender())){
            secAccount.setAvatarImagesUrl("https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/static/images/%E7%94%B7%E7%94%9F%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        } else {
            secAccount.setAvatarImagesUrl("https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/static/images/%E5%A5%B3%E7%94%9F%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
        }
        secAccountMapper.insert(secAccount);
        int userId = secAccountMapper.findUserIdByPhone(secAccount.getPhone());
        insertRole(userId, RoleConstant.ROLE_USER);
        return "2";
    }

    @Override
    public int findUserIdByPhone(String phone) {
        return 0;
    }

    @Override
    public void updatePasswordByPhone(String phone, String password) {
        secAccountMapper.updatePassword(phone, password);
    }

    @Override
    public String findPhoneByUsername(String username) {
        return secAccountMapper.findPhoneByUsername(username);
    }

    @Override
    public int findIdByUsername(String username) {
        return secAccountMapper.findIdByUsername(username);
    }

    @Override
    public SecAccount findUsernameByPhone(String phone) {
        return secAccountMapper.findUsernameByPhone(phone);
    }

    @Override
    public void updateLastLoginTime(String username, String lastLogintime) {
        String phone = secAccountMapper.findPhoneByUsername(username);
        secAccountMapper.updateLastLoginTime(phone, lastLogintime);
    }

    @Override
    public boolean usernameIsExit(String username) {
        SecAccount secAccount = secAccountMapper.findUsernameByUsername(username);
        return secAccount != null;
    }
    /**
     * 通过手机号判断是否为超级用户
     * @param phone 手机号
     * @return true--超级管理员  false--非超级管理员
     */
    @Override
    public boolean isSuperAdmin(String phone) {
        int userId = secAccountMapper.findUserIdByPhone(phone);
        List<Object> roleIds = secAccountMapper.findRoleIdByUserId(userId);

        for(Object i : roleIds){
            if((int)i == 3){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateAvatarImgUrlById(String avatarImgUrl, int id) {
        secAccountMapper.updateAvatarImgUrlById(avatarImgUrl, id);
    }
    /**
     * 获得头像
     */
    @Override
    public JSONObject getHeadPortraitUrl(int id) {
        JSONObject jsonObject = new JSONObject();
        String avatarImgUrl = secAccountMapper.getHeadPortraitUrl(id);
        if(!"".equals(avatarImgUrl) && avatarImgUrl != null){
            jsonObject.put("status",200);
            jsonObject.put("avatarImgUrl",avatarImgUrl);
        }
        return jsonObject;
    }
    /**
     * 获得个人资料
     */
    @Override
    public JSONObject getAccountPersonalInfoByUsername(String username) {
        SecAccount secAccount = secAccountMapper.getAccountPersonalInfoByUsername(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        JSONObject userJon = new JSONObject();
        userJon.put("phone", secAccount.getPhone());
        userJon.put("username", secAccount.getUsername());
        userJon.put("gender", secAccount.getGender());
        userJon.put("trueName", secAccount.getTrueName());
        userJon.put("birthday", secAccount.getBirthday());
        userJon.put("email", secAccount.getEmail());
        userJon.put("personalProfile", secAccount.getPersonalProfile());
        userJon.put("avatarImgUrl", secAccount.getAvatarImagesUrl());
        jsonObject.put("result",userJon);
        return jsonObject;
    }

    @Override
    public JSONObject savePersonalInfo(SecAccount secAccount, String username) {
        JSONObject returnJson = new JSONObject();

        //改了昵称
        if(!secAccount.getUsername().equals(username)){
            if(usernameIsExit(secAccount.getUsername())){
                returnJson.put("status",500);
                return returnJson;
            }
            returnJson.put("status",200);
            //注销当前登录用户
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        //没改昵称
        else {
            returnJson.put("status",201);
        }
        secAccountMapper.savePersonalDate(secAccount, username);

        return returnJson;
    }

    @Override
    public String getHeadPortraitUrlByUserId(int userId) {
        return secAccountMapper.getHeadPortraitUrl(userId);
    }

    /**
     * 查询员工总数
     */
    @Override
    public int countUserNum() {
        return secAccountMapper.countUserNum();
    }

    /**
     * 获得用户管理
     */
    @Override
    public JSONObject getSecAccountManagement(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<SecAccount> secAccounts = secAccountMapper.getSecAccountManagement();
        PageInfo<SecAccount> pageInfo = new PageInfo<>(secAccounts);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        JSONObject articleJson;
        for(SecAccount secAccount : secAccounts){
            articleJson = new JSONObject();
            articleJson.put("id",secAccount.getId());
            articleJson.put("phone",secAccount.getPhone());
            articleJson.put("username",secAccount.getUsername());
            articleJson.put("email",secAccount.getEmail());
            articleJson.put("avatarImagesUrl",secAccount.getAvatarImagesUrl());
            articleJson.put("lastLogintime",secAccount.getLastLogintime());
            articleJson.put("type",secAccount.getType());
            returnJsonArray.add(articleJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",returnJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }

    /**
     * 通过员工
     * @return
     */
    @Override
    public int agreeSecAccount(int id) {
        int i = secAccountMapper.selectById(id);
        if(i==1){
            return 1;
        }else {
            secAccountMapper.agreeSecAccount(id);
            return 0;
        }
    }

    /**
     * 不通过员工
     * @return
     */
    @Override
    public int disAgreeSecAccount(int id) {
        int i = secAccountMapper.selectById(id);
        if(i==0){
            return 1;
        }else {
            secAccountMapper.disAgreeSecAccount(id);
            return 0;
        }
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 1--删除成功   0--删除失败
     */
    @Override
    public int deleteSecAccount(long id) {
        try {
            //删除用户
            secAccountMapper.deleteSecAccount(id);

        }catch (Exception e){
            log.error("删除文章分类失败，文章分类id=" + id);
            return 0;
        }
        return 1;
    }

    /**
     * 增加用户权限
     * @param userId 员工id
     * @param roleId 权限id
     */
    private void insertRole(int userId, int roleId) {
        secAccountMapper.insertRole(userId, roleId);
    }

    /**
     * 通过手机号判断用户是否存在
     * @param phone 手机号
     * @return true--存在  false--不存在
     */
    private boolean userIsExit(String phone){
        SecAccount secAccount = secAccountMapper.findAccountByPhone(phone);
        return secAccount != null;
    }
}

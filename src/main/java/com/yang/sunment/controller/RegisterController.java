package com.yang.sunment.controller;

import com.yang.sunment.model.SecAccount;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2019/1/7 19:48
 * Describe:注册
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    SecAccountService secAccountService;

    @PostMapping("/registerAccount")
    @ResponseBody
    public String register(SecAccount secAccount, HttpServletRequest request){

        String authCode = request.getParameter("authCode");

        String trueMsgCode = (String) request.getSession().getAttribute("trueMsgCode");
        //手机验证码是否对
        if(!authCode.equals(trueMsgCode)){
            return "0";
        }
        if(secAccountService.usernameIsExit(secAccount.getUsername())){
            return "3";
        }
        //注册时对密码进行MD5加密
        MD5Util md5Util = new MD5Util();
        secAccount.setPassword(md5Util.encode(secAccount.getPassword()));
        secAccount.setType("0");
        return secAccountService.insert(secAccount);
    }

}

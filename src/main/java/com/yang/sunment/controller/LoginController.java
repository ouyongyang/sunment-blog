package com.yang.sunment.controller;

import com.yang.sunment.model.SecAccount;
import com.yang.sunment.repository.mybatis.SecAccountRepository;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2019/1/7 9:24
 * Describe: 登录
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    SecAccountService secAccountService;
    @Autowired
    SecAccountRepository secAccountRepository;

    @ResponseBody
    @PostMapping("/modifyPassword")
    public String modifyPassword(@RequestParam("phone") String phone,
                                 @RequestParam("authCode") String authCode,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request){

        String trueMsgCode = (String) request.getSession().getAttribute("trueMsgCode");
        if(!authCode.equals(trueMsgCode)){
            return "0";
        }
        SecAccount secAccount = secAccountService.findAccountByPhone(phone);
        if(secAccount == null){
            return "2";
        }
        MD5Util md5Util = new MD5Util();
        String MD5Password = md5Util.encode(newPassword);
        secAccountService.updatePasswordByPhone(phone, MD5Password);

        return "1";
    }

    @ResponseBody
    @PostMapping("/apply")
    public String apply(@RequestParam("phone") String phone) {
        if (phone!="") {
            SecAccount secAccount = secAccountRepository.findByPhone(phone);

            if ("0".equals(secAccount.getType())) {
                return "0";
            }
        }
            return "1";
        }

}

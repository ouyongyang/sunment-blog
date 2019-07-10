package com.yang.sunment.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yang.sunment.component.PhoneRandomBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2018/11/15 15:03
 * Describe: 注册获得手机验证码
 */
@Controller
@RequestMapping("/phone")
public class PhoneCodeControl {

    @PostMapping("/getVerificationCode")
    @ResponseBody
    public int getVerificationCode(HttpServletRequest request){

        String phone = request.getParameter("phone");
        String sign = request.getParameter("sign");
        String trueMsgCode = PhoneRandomBuilder.randomBuilder();
        request.getSession().setAttribute("trueMsgCode",trueMsgCode);

        String msgCode = "SMS_158493417";
        //注册
        if("register".equals(sign)){
            msgCode = "SMS_158493431";
        }
        //改密码
        else {
            msgCode = "SMS_158493437";

        }
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = sendSmsResponse(phone,trueMsgCode,msgCode);
        } catch (ClientException e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public static SendSmsResponse sendSmsResponse(String phoneNumber, String code, String msgCode) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //"***"分别填写自己的AccessKey ID和Secret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI7h0wtLOyd7Uz", "AHpSdBKM37LYL3v8oadgcCKyyUGNzZ");
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        //填写接收方的手机号码
        request.setPhoneNumbers(phoneNumber);
        //此处填写已申请的短信签名
        request.setSignName("sunment");
        //此处填写获得的短信模版CODE
        request.setTemplateCode(msgCode);
        //笔者的短信模版中有${code}, 因此此处对应填写验证码
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//请求成功

            System.out.println("请求成功`");
        }
        return sendSmsResponse;
    }
}

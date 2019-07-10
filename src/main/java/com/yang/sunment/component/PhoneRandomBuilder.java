package com.yang.sunment.component;

import org.springframework.stereotype.Component;

/**
 * @author: OYY
 * @Date: 2018/11/16 15:07
 * Describe: 手机验证码随机生成
 */
@Component
public class PhoneRandomBuilder {

    public static String randomBuilder(){

        String result = "";
        for(int i=0;i<4;i++){
            result += Math.round(Math.random() * 9);
        }

        return result;

    }

}

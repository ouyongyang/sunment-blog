package com.yang.sunment.component;

/**
 * @author: OYY
 * @Date: 2019/1/28 12:37
 * Describe:防止评论是js代码，会运行js代码
 */
public class JavaScriptCheck {

    public static String javaScriptCheck(String comment){
        int begin,end,theEnd;
        String newStr = comment;
        begin = comment.indexOf("<script");
        end = comment.indexOf("</script>");
        while (begin != -1){
            theEnd = comment.indexOf(">");
            newStr += comment.substring(0, begin);
            newStr += "[removed]" + comment.substring(theEnd+1,end) + "[removed]";

            comment = comment.substring(end+9);

            begin = comment.indexOf("<script");
            end = comment.indexOf("</script>");
        }
        return newStr;
    }

}

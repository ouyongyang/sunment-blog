package com.yang.sunment.controller;

import com.yang.sunment.model.SecAccount;
import com.yang.sunment.service.CommentService;
import com.yang.sunment.service.ContactAdminService;
import com.yang.sunment.service.LeaveMessageService;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.FileUtil;
import com.yang.sunment.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 个人主页
 */
@Controller
@RequestMapping("/personal")
@Slf4j
public class PersonalController {

    @Autowired
    SecAccountService secAccountService;

    @Autowired
    ContactAdminService contactAdminService;

    @Autowired
    CommentService commentService;

    @Autowired
    LeaveMessageService leaveMessageService;

    /**
     * 上传头像
     */
    @PostMapping("/uploadHead")
    @ResponseBody
    public JSONObject uploadHead(HttpServletRequest request,
                                 @AuthenticationPrincipal Principal principal){
        String username;
        //是否有用户名，没有就要登录
        try {
            username = principal.getName();
            log.info(username+"+++++++++++++++++");
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        String img = request.getParameter("img");
        //获得上传文件的后缀名
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);

        try {
            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
            TimeUtil timeUtil = new TimeUtil();
             //base64字符转换成file
            File file = fileUtil.base64ToFile(filePath, img, timeUtil.getLongTime() + strFileExtendName);

             //上传文件到阿里云OSS

            String url = fileUtil.uploadFile(file, "account/avatar/" + username);
            int userId = secAccountService.findIdByUsername(username);
            secAccountService.updateAvatarImgUrlById(url, userId);
            jsonObject = secAccountService.getHeadPortraitUrl(userId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("更改头像失败",e.getMessage(),e);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 获得头像
     */
    @GetMapping("/getHeadPortraitUrl")
    @ResponseBody
    public JSONObject getHeadPortraitUrl(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return secAccountService.getHeadPortraitUrl(secAccountService.findIdByUsername(username));
    }

    /**
     * 获得个人资料
     */
    @GetMapping("/getAccountPersonalInfo")
    @ResponseBody
    public JSONObject getAccountPersonalInfo(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return secAccountService.getAccountPersonalInfoByUsername(username);
    }

    /**
     * 保存个人资料
     */
    @PostMapping("/savePersonalInfo")
    @ResponseBody
    public JSONObject savePersonalInfo(SecAccount secAccount,
                                       @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return secAccountService.savePersonalInfo(secAccount, username);
    }

    /**
     * 联系管理员
     * @param rows 一页大小
     * @param pageNum 当前页
     */
    @PostMapping("/getPrivateWordByPublisher")
    @ResponseBody
    public JSONObject getPrivateWordByPublisher(@RequestParam("rows") String rows,
                                                @RequestParam("pageNum") String pageNum,
                                                @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return contactAdminService.getPrivateWordByPublisher(username, Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 发送信息
     * @param privateWord 悄悄话内容
     */
    @PostMapping("/sendPrivateWord")
    @ResponseBody
    public JSONObject sendPrivateWord(@RequestParam("privateWord") String privateWord,
                                      @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return contactAdminService.publishPrivateWord(privateWord, username);
    }

    /**
     * 获得该用户曾今的所有评论
     */
    @PostMapping("/getPersonalComment")
    @ResponseBody
    public JSONObject getPersonalComment(@RequestParam("rows") String rows,
                                     @RequestParam("pageNum") String pageNum,
                                     @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return commentService.getPersonalComment(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }

    /**
     * 获得该用户曾今的所有留言
     */
    @PostMapping("/getPersonalLeaveMessage")
    @ResponseBody
    public JSONObject getPersonalLeaveMessage(@RequestParam("rows") String rows,
                                          @RequestParam("pageNum") String pageNum,
                                          @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return leaveMessageService.getPersonalLeaveMessage(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }
}

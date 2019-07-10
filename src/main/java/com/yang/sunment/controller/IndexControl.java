package com.yang.sunment.controller;


import com.yang.sunment.model.FeedBack;
import com.yang.sunment.service.*;
import com.yang.sunment.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2018/2/17 19:24
 * Describe:
 */
@Controller
@RequestMapping("/index")
@Slf4j
public class IndexControl {

    @Autowired
    VisitorService visitorService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SecAccountService secAccountService;
    @Autowired
    FeedBackService feedBackService;
    @Autowired
    LeaveMessageService leaveMessageService;
    /**
     * 分页获得当前页文章
     * @param rows 一页的大小
     * @param pageNum 当前页
     */
    @PostMapping("/articlesList")
    public @ResponseBody JSONArray articlesList(@RequestParam("rows") String rows,
                                @RequestParam("pageNum") String pageNum, @RequestParam("articleAbstract") String articleAbstract){

        return articleService.findAllArticles(rows,pageNum,articleAbstract);

    }

    /**
     * 反馈
     * @param feedBack
     * @param principal
     * @return
     */
    @PostMapping("/submitFeedBack")
    @ResponseBody
    public JSONObject submitFeedback(FeedBack feedBack,
                                     @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            return jsonObject;
        }
        feedBack.setPersonId(secAccountService.findIdByUsername(username));
        return feedBackService.submitFeedback(feedBack);

    }

    /**
     * 获得最新评论
     */
    @GetMapping("/newComment")
    @ResponseBody
    public JSONObject newComment(@RequestParam("rows") String rows,
                                 @RequestParam("pageNum") String pageNum){

        return commentService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }

    /**
     * 获得最新留言
     */
    @GetMapping("/newLeaveWord")
    @ResponseBody
    public JSONObject newLeaveWord(@RequestParam("rows") String rows,
                                   @RequestParam("pageNum") String pageNum){
        return leaveMessageService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }

    /**
     * 获得标签云
     */
    @GetMapping("/findTagsCloud")
    @ResponseBody
    public JSONObject findTagsCloud(){
        return tagService.findTagsCloud();
    }

    /**
     * 网站信息
     */
    @GetMapping("/getSiteInfo")
    @ResponseBody
    public JSONObject getSiteInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articleNum", articleService.countArticle());
        jsonObject.put("tagsNum", tagService.countTagsNum());
        jsonObject.put("leaveWordNum", leaveMessageService.countLeaveMessageNum());
        jsonObject.put("commentNum", commentService.commentNum());
        return jsonObject;
    }

    /**
     * 增加访客量
     * @return  网站总访问量以及访客量
     */
    @GetMapping("/getVisitorNumByPageName")
    public @ResponseBody JSONObject getVisitorNumByPageName(HttpServletRequest request,
                                                            @RequestParam("pageName") String pageName) throws UnsupportedEncodingException {

        int index = pageName.indexOf("?");
        if(index == -1){
            pageName = "visitorVolume";
        } else {
            String subPageName = pageName.substring(0, index);
            if("jump/archives".equals(subPageName) || "categories".equals(subPageName) || "tags".equals(subPageName) || "login".equals(subPageName) || "register".equals(subPageName)){
                pageName = "visitorVolume";
            } else {
                //接收到文章的url将url中utf8的16进制数转换成汉字
                int originalAuthorIndex = pageName.indexOf("originalAuthor");
                String originalAuthorUtf18 = pageName.substring(originalAuthorIndex + 15);
                pageName = pageName.substring(0, originalAuthorIndex + 15) + TransCodingUtil.utf16ToUtf8(originalAuthorUtf18);
            }
        }
        visitorService.addVisitorNumByPageName(pageName, request);
        return visitorService.getVisitorNumByPageName(pageName);
    }

    /**
     * 我的文章
     * @param rows 一页的大小
     * @param pageNum 当前页
     */
    @PostMapping("/myArticle")
    public @ResponseBody JSONArray myArticle(@RequestParam("rows") String rows,
                                                @RequestParam("pageNum") String pageNum, @AuthenticationPrincipal Principal principal){

        String username = principal.getName();
        return articleService.myArticle(rows,pageNum,username);

    }
}

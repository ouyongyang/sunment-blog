package com.yang.sunment.controller;

import com.yang.sunment.mapper.CommentMapper;
import com.yang.sunment.service.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2019/2/7 16:14
 * Describe: 后台管理页面
 */
@RestController
@RequestMapping("/administrators")
@Slf4j
public class AdministratorsController {

    @Autowired
    ArticleService articleService;
    @Autowired
    FeedBackService feedBackService;
    @Autowired
    SecAccountService secAccountService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    ContactAdminService contactAdminService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentMapper commentMapper;

    /**
     * 获得文章管理
     * @return
     */
    @GetMapping("/getArticleManagement")
    public JSONObject getArticleManagement(@AuthenticationPrincipal Principal principal,
                                           @RequestParam("rows") String rows,
                                           @RequestParam("pageNum") String pageNum){
        String username = null;
        JSONObject returnJson = new JSONObject();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            returnJson.put("status",403);
            return  returnJson;
        }
        return articleService.getArticleManagement(Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 1--删除成功   0--删除失败
     */
    @GetMapping("/deleteArticle")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public int deleteArticle(@RequestParam("id") String id){
        if("".equals(id) || id == null){
            return 0;
        }
        return articleService.deleteArticle(Long.parseLong(id));
    }

    /**
     * 分页获得所有反馈信息
     * @param rows 一页大小
     * @param pageNum 当前页
     */
    @GetMapping("/getAllFeedback")
    public JSONObject getAllFeedback(@RequestParam("rows") String rows,
                                     @RequestParam("pageNum") String pageNum){
        return feedBackService.getAllFeedback(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }

    /**
     * 获得统计信息
     * @return
     */
    @GetMapping("/getStatisticsInfo")
    public JSONObject getStatisticsInfo(){
        JSONObject returnJson = new JSONObject();
        long num = visitorService.getAllVisitor();
        returnJson.put("allVisitor", num);
        returnJson.put("allUser", secAccountService.countUserNum());
        returnJson.put("yesterdayVisitor", commentService.commentNum());
        returnJson.put("articleNum", articleService.countArticle());
        returnJson.put("commentNum",commentMapper.commentNum());
        return returnJson;
    }


    /**
     * 获得所有悄悄话
     * @return
     */
    @PostMapping("/getAllPrivateWord")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public JSONObject getAllPrivateWord(){
        return contactAdminService.getAllPrivateWord();
    }

    /**
     * 回复悄悄话
     * @return
     */
    @PostMapping("/replyPrivateWord")
    public JSONObject replyPrivateWord(@AuthenticationPrincipal Principal principal,
                                       @RequestParam("replyContent") String replyContent,
                                       @RequestParam("replyId") String id){
        String username;
        JSONObject jsonObject;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            jsonObject = new JSONObject();
            jsonObject.put("status",403);
            return jsonObject;
        }

        return contactAdminService.replyPrivateWord(replyContent, username, Integer.parseInt(id));
    }

    /**
     * 获得文章分类
     * @return
     */
    @GetMapping("/getArticleCategories")
    public JSONObject getArticleCategories(@AuthenticationPrincipal Principal principal,
                                           @RequestParam("rows") String rows,
                                           @RequestParam("pageNum") String pageNum){
        String username = null;
        JSONObject returnJson = new JSONObject();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            returnJson.put("status",403);
            return  returnJson;
        }
        return categoryService.getArticleCategories(Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 添加文章分类
     * @return
     */
    @PostMapping("/addAtegories")
    public int addAtegories(@RequestParam("categoriesName") String categoriesName){
        return categoryService.addAtegories(categoriesName);
    }

    /**
     * 删除分类文章
     * @param id 文章分类id
     * @return 1--删除成功   0--删除失败
     */
    @GetMapping("/deleteCategories")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public int deleteCategories(@RequestParam("id") String id){
        if("".equals(id) || id == null){
            return 0;
        }
        return categoryService.deleteCategories(Long.parseLong(id));
    }


    /**
     * 获得用户管理
     * @return
     */
    @GetMapping("/getSecAccountManagement")
    public JSONObject getSecAccountManagement(@AuthenticationPrincipal Principal principal,
                                           @RequestParam("rows") String rows,
                                           @RequestParam("pageNum") String pageNum){
        String username = null;
        JSONObject returnJson = new JSONObject();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            returnJson.put("status",403);
            return  returnJson;
        }
        return secAccountService.getSecAccountManagement(Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 审批通过员工
     * @return
     */
    @PostMapping("/agreeSecAccount")
    public int agreeSecAccount(@RequestParam("id") Integer id){
        return secAccountService.agreeSecAccount(id);
    }

    /**
     * 审批不通过员工
     * @return
     */
    @PostMapping("/disAgreeSecAccount")
    public int disAgreeSecAccount(@RequestParam("id") Integer id){
        return secAccountService.disAgreeSecAccount(id);
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 1--删除成功   0--删除失败
     */
    @GetMapping("/deleteSecAccount")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public int deleteSecAccount(@RequestParam("id") String id){
        if("".equals(id) || id == null){
            return 0;
        }
        return secAccountService.deleteSecAccount(Long.parseLong(id));
    }

    /**
     * 获得评论管理
     * @return
     */
    @GetMapping("/getArticleComment")
    public JSONObject getArticleComment(@AuthenticationPrincipal Principal principal,
                                           @RequestParam("rows") String rows,
                                           @RequestParam("pageNum") String pageNum){
        String username = null;
        JSONObject returnJson = new JSONObject();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            returnJson.put("status",403);
            return  returnJson;
        }
        return commentService.getArticleComment(Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 删除评论
     * @param id 评论id
     * @return 1--删除成功   0--删除失败
     */
    @GetMapping("/deleteComment")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public int deleteComment(@RequestParam("id") String id){
        if("".equals(id) || id == null){
            return 0;
        }
        return commentService.deleteComment(Long.parseLong(id));
    }
}


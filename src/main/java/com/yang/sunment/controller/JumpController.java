package com.yang.sunment.controller;

import com.yang.sunment.service.ArticleService;
import com.yang.sunment.utils.TransCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * @author: OYY
 * @Date: 2018/11/17 19:24
 * Describe: 所有页面跳转
 */
@Controller
@RequestMapping("/jump")
public class JumpController {

    @Autowired
    ArticleService articleService;

    /**
     去登录页面
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    /**
     保存跳转页面的url
     */
    @GetMapping("/toLogin")
    @ResponseBody
    public void toLogin(HttpServletRequest request){
        //保存跳转页面的url
        request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));
    }
    /**
     去博客主页
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response,
                        @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            request.getSession().removeAttribute("lastUrl");
            return "index";
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("lastUrl", (String) request.getSession().getAttribute("lastUrl"));
        return "index";
    }

    /**
     去注册页面
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    /**
     去我的主页页面
     */
    @GetMapping("/personal")
    public String personal(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "personal";
    }
    /**
     去我的后台页面
     */
    @GetMapping("/Administrators")
    public String superadmin(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "Administrators";
    }
    /**
     去编辑博客页面
     */
    @GetMapping("/edit")
    public String edit(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        String id = request.getParameter("id");
        if(!"".equals(id)){
            request.getSession().setAttribute("id", id);
        }
        return "edit";
    }

    @GetMapping("/findArticle")
    public String show(@RequestParam("articleId") String articleId,
                       @RequestParam("originalAuthor") String originalAuthor,
                       HttpServletResponse response,
                       Model model,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");

        Map<String, String> articleMap = articleService.findArticleTitle(Long.parseLong(articleId), originalAuthor);
        model.addAttribute("articleTitle",articleMap.get("articleTitle"));
        String articleTabloid = articleMap.get("articleAbstract");
        if(articleTabloid.length() <= 110){
            model.addAttribute("articleAbstract",articleTabloid);
        } else {
            model.addAttribute("articleAbstract",articleTabloid.substring(0,110));
        }

        //将文章id和原作者存入响应头
        response.setHeader("articleId",articleId);
        response.setHeader("originalAuthor", TransCodingUtil.stringToUnicode(originalAuthor));
        return "article";
    }

    @GetMapping("/categories")
    public String categories(HttpServletResponse response,
                             HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String category = request.getParameter("category");

        try {
            response.setHeader("category", TransCodingUtil.stringToUnicode(category));
        } catch (Exception e){
        }
        return "categories";
    }

    @GetMapping("/archives")
    public String archives(HttpServletResponse response,
                           HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String archive = request.getParameter("archive");

        try {
            response.setHeader("archive", TransCodingUtil.stringToUnicode(archive));
        } catch (Exception e){
        }
        return "archives";
    }

    @GetMapping("/tags")
    public String tags(HttpServletResponse response,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");

        String tag = request.getParameter("tag");
        try {
            response.setHeader("tag", TransCodingUtil.stringToUnicode(tag));
        } catch (Exception e){
        }
        return "tags";
    }

    @GetMapping("/aboutme")
    public String aboutme(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "aboutme";
    }

}

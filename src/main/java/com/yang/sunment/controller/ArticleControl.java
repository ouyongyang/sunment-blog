package com.yang.sunment.controller;


import com.yang.sunment.model.ArticleLikes;
import com.yang.sunment.service.ArticleLikesService;
import com.yang.sunment.service.ArticleService;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.TimeUtil;
import com.yang.sunment.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2019/2/5 16:21
 * Describe: 文章显示页面
 */
@Controller
@RequestMapping("/article")
@Slf4j
public class ArticleControl {


    @Autowired
    ArticleLikesService articleLikesService;
    @Autowired
    ArticleService articleService;
    @Autowired
    SecAccountService secAccountService;

    /**
     *  获取文章
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @return
     */
    @PostMapping("/getArticle")
    public @ResponseBody JSONObject getArticleByIdAndOriginalAuthor(@RequestParam("articleId") String articleId,
                                                                    @RequestParam("originalAuthor") String originalAuthor,
                                                                    @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
        }
        JSONObject jsonObject = articleService.getArticle(Long.parseLong(articleId), TransCodingUtil.unicodeToString(originalAuthor),username);
        return jsonObject;
    }



    /**
     * 点赞
     * @param articleId 文章号
     * @return
     */
    @GetMapping("/addArticleLike")
    public @ResponseBody int addArticleLike(@RequestParam("articleId") String articleId,
                                            @RequestParam("originalAuthor") String originalAuthor,
                                            @AuthenticationPrincipal Principal principal){

        String username="";
        try {
            username = principal.getName();
        }catch (NullPointerException e){
            log.error("username " + username + " is not login");
            return -1;
        }

        String stringOriginalAuthor = TransCodingUtil.unicodeToString(originalAuthor);
        if(articleLikesService.isLiked(Long.parseLong(articleId), stringOriginalAuthor, username)){
            log.info("你已经点过赞了");
            return -2;
        }
        int likes = articleService.updateLike(Long.parseLong(articleId), stringOriginalAuthor);
        ArticleLikes articleLikes = new ArticleLikes(Long.parseLong(articleId), stringOriginalAuthor, secAccountService.findIdByUsername(username), new TimeUtil().getFormatDateForFive());
        articleLikesService.insertArticleLikes(articleLikes);
        log.info("点赞成功");
        return likes;
    }


}

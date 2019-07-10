package com.yang.sunment.controller;

import com.yang.sunment.component.JavaScriptCheck;
import com.yang.sunment.model.Comment;
import com.yang.sunment.model.CommentLikes;
import com.yang.sunment.service.CommentLikesService;
import com.yang.sunment.service.CommentService;
import com.yang.sunment.service.SecAccountService;

import com.yang.sunment.utils.TimeUtil;
import com.yang.sunment.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2019/3/17 19:24
 * Describe: 评论和回复
 */
@Controller
@RequestMapping("/comment")
@Slf4j
public class CommentControl {


    @Autowired
    private CommentService commentService;
    @Autowired
    CommentLikesService commentLikesService;
    @Autowired
    SecAccountService secAccountService;

    /**
     * 评论
     * @param principal 当前用户
     * @return
     */
    @PostMapping("/publishComment")
    @ResponseBody
    public JSONArray publishComment(Comment comment,
                                    @AuthenticationPrincipal Principal principal){
        String publisher;
        try {
            publisher  = principal.getName();
        } catch (NullPointerException e){
            log.error("no principal,please to login");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        TimeUtil timeUtil = new TimeUtil();
        comment.setCommentDate(timeUtil.getFormatDateForFive());
        int userId = secAccountService.findIdByUsername(publisher);
        String respondent = TransCodingUtil.unicodeToString(comment.getOriginalAuthor());
        comment.setCommentId(userId);
        comment.setOriginalAuthor(respondent);
        comment.setResponseId(secAccountService.findIdByUsername(respondent));
        comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));

        commentService.insertComment(comment, respondent);

        JSONArray jsonArray = commentService.findAllComment(comment.getArticleId(), comment.getOriginalAuthor(),publisher);
        return jsonArray;
    }

    /**
     * 获得该文章所有评论
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @return
     */
    @PostMapping("/getAllComment")
    @ResponseBody
    public JSONArray getAllComment(@RequestParam("articleId") String articleId,
                                   @RequestParam("originalAuthor") String originalAuthor,
                                   @AuthenticationPrincipal Principal principal){

        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
        }
        JSONArray jsonArray = commentService.findAllComment(Long.parseLong(articleId), TransCodingUtil.unicodeToString(originalAuthor),username);
        return jsonArray;

    }


    /**
     * 是否登陆
     * @param principal 当前用户
     * @return
     */
    @GetMapping("/isLogin")
    @ResponseBody
    public int isLogin(@AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
            return 1;
        } catch (NullPointerException e){
            log.info("This user is not login");
            return 0;
        }
    }

    /**
     * 评论中的回复
     * @param principal 当前用户
     * @return
     */
    @PostMapping("/publishReply")
    @ResponseBody
    public JSONArray publishReply(Comment comment,
                                  @RequestParam("parentId") String parentId,
                                  @RequestParam("respondent") String respondent,
                                  @AuthenticationPrincipal Principal principal){

        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonArray.add(jsonObject);
            return jsonArray;
        }

        comment.setFatherId(Long.parseLong(parentId.substring(1)));
        comment.setCommentId(secAccountService.findIdByUsername(username));
        comment.setResponseId(secAccountService.findIdByUsername(respondent));
        comment.setOriginalAuthor(TransCodingUtil.unicodeToString(comment.getOriginalAuthor()));
        TimeUtil timeUtil = new TimeUtil();
        comment.setCommentDate(timeUtil.getFormatDateForFive());
        comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));
        comment = commentService.insertComment(comment, respondent);

        JSONArray jsonArray = commentService.replyReplyReturn(comment, username, respondent);
        return jsonArray;
    }

    /**
     * 评论点赞
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @param responseId 评论的id
     * @param principal 当前用户
     * @return 点赞数
     */
    @GetMapping("/addCommentLike")
    @ResponseBody
    public int addCommentLike(@RequestParam("articleId") String articleId,
                              @RequestParam("originalAuthor") String originalAuthor,
                              @RequestParam("responseId") String responseId,
                              @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
            return -1;
        }

        TimeUtil timeUtil = new TimeUtil();
        CommentLikes commentLikes = new CommentLikes(Long.parseLong(articleId),TransCodingUtil.unicodeToString(originalAuthor),
                Integer.parseInt(responseId.substring(1)),secAccountService.findIdByUsername(username),timeUtil.getFormatDateForFive());
        if(commentLikesService.isLiked(commentLikes.getArticleId(), commentLikes.getOriginalAuthor(), commentLikes.getCommentId(), username)){
            log.info("This user had clicked good for this article");
            return -2;
        }
        int likes = commentService.updateLike(commentLikes.getArticleId(),commentLikes.getOriginalAuthor(),commentLikes.getCommentId());
        commentLikesService.insertCommentLikes(commentLikes);
        return likes;
    }

}



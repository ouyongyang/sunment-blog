package com.yang.sunment.controller;


import com.yang.sunment.component.JavaScriptCheck;
import com.yang.sunment.model.LeaveMessage;
import com.yang.sunment.model.LeaveMessageLikes;
import com.yang.sunment.service.LeaveMessageLikesService;
import com.yang.sunment.service.LeaveMessageService;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:
 */
@RestController
@RequestMapping("/leaveMessage")
@Slf4j
public class LeaveMessageControl {


    @Autowired
    LeaveMessageService leaveMessageService;
    @Autowired
    LeaveMessageLikesService leaveMessageLikesService;
    @Autowired
    SecAccountService secAccountService;

    /**
     * 发表留言
     * @param leaveMessageContent 留言内容
     * @param pageName 留言页
     * @param principal 当前用户
     * @return
     */
    @PostMapping("/publishLeaveMessage")
    public JSONObject publishLeaveMessage(@RequestParam("leaveMessageContent") String leaveMessageContent,
                                          @RequestParam("pageName") String pageName,
                                          @AuthenticationPrincipal Principal principal){

        String answerer = null;
        JSONObject jsonObject;
        try {
            answerer = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
            jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonObject.put("result","You are not sign in");
            return jsonObject;
        }
        leaveMessageService.publishLeaveMessage(leaveMessageContent,pageName, answerer);
        return leaveMessageService.findAllLeaveMessage(pageName, 0, answerer);

    }

    /**
     * 获得当前页的留言
     * @param pageName 当前页
     * @return
     */
    @GetMapping("/getPageLeaveMessage")
    public JSONObject getPageLeaveMessage(@RequestParam("pageName") String pageName,
                                          @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
        }
        return leaveMessageService.findAllLeaveMessage(pageName, 0, username);
    }

    /**
     * 发布留言中的评论
     * @return
     */
    @PostMapping("/publishLeaveMessageReply")
    public JSONObject publishLeaveMessageReply(LeaveMessage leaveMessage,
                                               @RequestParam("parentId") String parentId,
                                               @RequestParam("respondent") String respondent,
                                               @AuthenticationPrincipal Principal principal){
        String username = null;
        JSONObject jsonObject;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            jsonObject = new JSONObject();
            log.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","You are not sign in");
        }
        leaveMessage.setLeaverId(secAccountService.findIdByUsername(username));
        leaveMessage.setFatherId(Integer.parseInt(parentId.substring(1)));
        leaveMessage.setLeaveMessageContent(JavaScriptCheck.javaScriptCheck(leaveMessage.getLeaveMessageContent()));
        leaveMessage = leaveMessageService.publishLeaveMessageReply(leaveMessage, respondent);

        return leaveMessageService.leaveMessageNewReply(leaveMessage, username, respondent);
    }

    /**
     * 点赞
     * @return 点赞数
     */
    @GetMapping("/addLeaveMessageLike")
    public int addLeaveMessageLike(@RequestParam("pageName") String pageName,
                                   @RequestParam("respondentId") String respondentId,
                                   @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            log.info("This user is not login");
            return -1;
        }
        TimeUtil timeUtil = new TimeUtil();
        int userId = secAccountService.findIdByUsername(username);
        LeaveMessageLikes leaveMessageLikes = new LeaveMessageLikes(pageName, Integer.parseInt(respondentId.substring(1)), userId, timeUtil.getFormatDateForFive());
        if(leaveMessageLikesService.isLiked(leaveMessageLikes.getPageName(), leaveMessageLikes.getFatherId(), userId)){
            log.info("This user had clicked good for this page");
            return -2;
        }
        int likes = leaveMessageService.updateLikeByPageNameAndId(pageName, leaveMessageLikes.getFatherId());
        leaveMessageLikesService.insertLeaveMessageLikes(leaveMessageLikes);
        return likes;
    }

}

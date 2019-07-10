package com.yang.sunment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.sunment.component.JavaScriptCheck;
import com.yang.sunment.mapper.LeaveMessageMapper;
import com.yang.sunment.model.LeaveMessage;
import com.yang.sunment.service.LeaveMessageLikesService;
import com.yang.sunment.service.LeaveMessageService;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:
 */
@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    LeaveMessageMapper leaveMessageMapper;
    @Autowired
    LeaveMessageLikesService leaveMessageLikesService;
    @Autowired
    SecAccountService secAccountService;

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String pageName, String answerer) {

        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        leaveMessageContent = JavaScriptCheck.javaScriptCheck(leaveMessageContent);
        LeaveMessage leaveMessage = new LeaveMessage(pageName, secAccountService.findIdByUsername(answerer), secAccountService.findIdByUsername("Admin"), nowStr, leaveMessageContent);

        leaveMessageMapper.publishLeaveMessage(leaveMessage);

    }

     /**
     * 保存留言回复信息
     * @param leaveMessage
     */
    @Override
    public LeaveMessage publishLeaveMessageReply(LeaveMessage leaveMessage, String respondent) {
        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        leaveMessage.setLeaveMessageDate(nowStr);
        String commentContent = leaveMessage.getLeaveMessageContent();
        if('@' == commentContent.charAt(0)){
            leaveMessage.setLeaveMessageContent(commentContent.substring(respondent.length() + 1));
        }
        leaveMessage.setResponseId(secAccountService.findIdByUsername(respondent));
        leaveMessageMapper.publishLeaveMessage(leaveMessage);
        return leaveMessage;
    }

     /**
     * 返回最新的留言回复
     * @param leaveMessage
     * @return
     */
    @Override
    public JSONObject leaveMessageNewReply(LeaveMessage leaveMessage, String leaver, String respondent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        JSONObject result = new JSONObject();
        result.put("answerer",leaver);
        result.put("respondent",respondent);
        result.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
        result.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
        jsonObject.put("result",result);
        return jsonObject;
    }

    @Override
    public JSONObject findAllLeaveMessage(String pageName, int fatherId, String username) {

        List<LeaveMessage> leaveMessages = leaveMessageMapper.findAllLeaveMessage(pageName, fatherId);
        JSONObject returnJson,replyJson;
        JSONObject leaveMessageJson = new JSONObject();
        JSONArray replyJsonArray;
        JSONArray leaveMessageJsonArray = new JSONArray();
        List<LeaveMessage> leaveMessageReplies;

        returnJson = new JSONObject();
        returnJson.put("status",200);

        for(LeaveMessage leaveMessage : leaveMessages){
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("id",leaveMessage.getId());
            leaveMessageJson.put("answerer",secAccountService.findUsernameById(leaveMessage.getLeaverId()));
            leaveMessageJson.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
            leaveMessageJson.put("likes",leaveMessage.getLikes());
            leaveMessageJson.put("avatarImgUrl",secAccountService.getHeadPortraitUrlByUserId(leaveMessage.getLeaverId()));
            leaveMessageJson.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
            if(null == username){
                leaveMessageJson.put("isLiked",0);
            } else {
                if(!leaveMessageLikesService.isLiked(pageName, leaveMessage.getId(), secAccountService.findIdByUsername(username))){
                    leaveMessageJson.put("isLiked",0);
                } else {
                    leaveMessageJson.put("isLiked",1);
                }
            }

            leaveMessageReplies = leaveMessageMapper.findLeaveMessageReply(pageName, leaveMessage.getId());
            replyJsonArray = new JSONArray();
            for(LeaveMessage reply : leaveMessageReplies){
                replyJson = new JSONObject();
                replyJson.put("answerer", secAccountService.findUsernameById(reply.getLeaverId()));
                replyJson.put("respondent", secAccountService.findUsernameById(reply.getResponseId()));
                replyJson.put("leaveMessageDate", reply.getLeaveMessageDate());
                replyJson.put("leaveMessageContent", reply.getLeaveMessageContent());

                replyJsonArray.add(replyJson);
            }
            leaveMessageJson.put("replies",replyJsonArray);
            leaveMessageJsonArray.add(leaveMessageJson);
        }
        returnJson.put("result",leaveMessageJsonArray);

        return returnJson;
    }
    /**
     * 更新点赞数
     * @return 点赞数
     *
     **/
    @Override
    public int updateLikeByPageNameAndId(String pageName, int id) {
        leaveMessageMapper.updateLikeByPageNameAndId(pageName, id);
        return leaveMessageMapper.findLikesByPageNameAndId(pageName, id);
    }

    @Override
    public JSONObject getPersonalLeaveMessage(int rows, int pageNum, String username) {

        int leaverId = secAccountService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> leaveMessages = leaveMessageMapper.getPersonalLeaveMessage(leaverId);
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);
        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        JSONObject leaveMessageJson;
        JSONArray leaveMessageJsonArray = new JSONArray();
        for(LeaveMessage leaveMessage : leaveMessages){
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("pageName",leaveMessage.getPageName());
            leaveMessageJson.put("answerer",username);
            leaveMessageJson.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
            if(leaveMessage.getFatherId() == 0){
                leaveMessageJson.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
                leaveMessageJson.put("replyNum",leaveMessageMapper.countReplyNumById(leaveMessage.getId()));
            } else {
                leaveMessageJson.put("leaveMessageContent","@" + secAccountService.findUsernameById(leaveMessage.getResponseId()) + " " + leaveMessage.getLeaveMessageContent());
                leaveMessageJson.put("replyNum",leaveMessageMapper.countReplyNumByIdAndRespondentId(leaveMessage.getId(), leaveMessage.getResponseId()));
            }
            leaveMessageJsonArray.add(leaveMessageJson);
        }

        returnJson.put("result",leaveMessageJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);
        return returnJson;
    }
    /**
     * 返回最新5条留言
     */
    @Override
    public JSONObject findFiveNewComment(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> fiveLeaveWords = leaveMessageMapper.findFiveNewLeaveWord();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(fiveLeaveWords);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(LeaveMessage leaveMessage : fiveLeaveWords){
            jsonObject = new JSONObject();
            if(leaveMessage.getFatherId() != 0){
                leaveMessage.setLeaveMessageContent("@" + secAccountService.findUsernameById(leaveMessage.getResponseId()) + " " + leaveMessage.getLeaveMessageContent());
            }
            jsonObject.put("pagePath",leaveMessage.getPageName());
            jsonObject.put("answerer",secAccountService.findUsernameById(leaveMessage.getLeaverId()));
            jsonObject.put("leaveWordDate",leaveMessage.getLeaveMessageDate().substring(0,10));
            jsonObject.put("leaveWordContent",leaveMessage.getLeaveMessageContent());
            jsonArray.add(jsonObject);
        }

        returnJson.put("status",200);
        returnJson.put("result",jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);
        return returnJson;
    }

    @Override
    public int countLeaveMessageNum() {
        return leaveMessageMapper.countLeaveMessageNum();
    }
}

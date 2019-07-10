package com.yang.sunment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.sunment.mapper.ContactAdminMapper;
import com.yang.sunment.model.ContactAdmin;
import com.yang.sunment.service.ContactAdminService;

import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/3/22 20:21
 * Describe:
 */
@Service
public class ContactAdminServiceImpl implements ContactAdminService {

    @Autowired
    private ContactAdminMapper contactAdminMapper;
    @Autowired
    SecAccountService secAccountService;

    @Override
    public JSONObject publishPrivateWord(String privateWordContent, String username) {
        TimeUtil timeUtil = new TimeUtil();
        ContactAdmin privateWord = new ContactAdmin(privateWordContent, secAccountService.findIdByUsername(username), timeUtil.getFormatDateForSix());
        contactAdminMapper.publishPrivateWord(privateWord);
        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        return returnJson;
    }

    @Override
    public JSONObject getPrivateWordByPublisher(String publisher, int rows, int pageNum) {
        int publisherId = secAccountService.findIdByUsername(publisher);
        PageHelper.startPage(pageNum, rows);
        List<ContactAdmin> privateWords = contactAdminMapper.getPrivateWordByPublisher(publisherId);
        PageInfo<ContactAdmin> pageInfo = new PageInfo<>(privateWords);

        JSONObject returnJson = new JSONObject();
        JSONObject privateWordJson;
        JSONArray privateWordJsonArray = new JSONArray();
        for(ContactAdmin privateWord : privateWords){
            privateWordJson = new JSONObject();
            privateWordJson.put("privateWord", privateWord.getPrivateWord());
            privateWordJson.put("publisher", publisher);
            if(privateWord.getReplyContent() == null){
                privateWordJson.put("replier", "");
                privateWordJson.put("replyContent", "");
            } else {
                privateWordJson.put("replier", secAccountService.findUsernameById(privateWord.getReplierId()));
                privateWordJson.put("replyContent", privateWord.getReplyContent());
            }
            String publisherDate = privateWord.getPublisherDate().substring(0,4) + "年" + privateWord.getPublisherDate().substring(5,7) + "月" +
                    privateWord.getPublisherDate().substring(8,10) + "日" + privateWord.getPublisherDate().substring(11);
            privateWordJson.put("publisherDate", publisherDate);
            privateWordJsonArray.add(privateWordJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",privateWordJsonArray);

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
    public JSONObject getAllPrivateWord() {
        List<ContactAdmin> privateWords = contactAdminMapper.getAllPrivateWord();

        JSONObject returnJson = new JSONObject();
        JSONObject userJson;
        JSONArray allJsonArray = new JSONArray();
        JSONObject newUserJson;

        returnJson.put("status",200);
        List<String> publishers = new ArrayList<>();
        String publisher;
        for(ContactAdmin privateWord : privateWords){
            userJson = new JSONObject();
            userJson.put("privateWord", privateWord.getPrivateWord());
            publisher = secAccountService.findUsernameById(privateWord.getPublisherId());
            userJson.put("publisher", publisher);
            userJson.put("publisherDate", privateWord.getPublisherDate());
            userJson.put("id", privateWord.getId());
            if(privateWord.getReplyContent() == null){
                userJson.put("replier","");
                userJson.put("replyContent","");
            } else {
                userJson.put("replyContent",privateWord.getReplyContent());
                userJson.put("replier",secAccountService.findUsernameById(privateWord.getReplierId()));
            }
            if(!publishers.contains(publisher)){
                publishers.add(publisher);
                newUserJson = new JSONObject();
                newUserJson.put("publisher",publisher);
                newUserJson.put("content",new JSONArray());
                allJsonArray.add(newUserJson);
            }
            for(int i=0;i<allJsonArray.size();i++){
                JSONObject arrayUser = (JSONObject) allJsonArray.get(i);
                if(arrayUser.get("publisher").equals(publisher)){
                    JSONArray jsonArray = (JSONArray) arrayUser.get("content");
                    jsonArray.add(userJson);
                    arrayUser.put("publisher", publisher);
                    arrayUser.put("content", jsonArray);
                    allJsonArray.remove(i);

                    allJsonArray.add(arrayUser);
                    break;
                }
            }
        }
        returnJson.put("result",allJsonArray);
        //System.out.println("getAllPrivateWord result is " + returnJson);
        return returnJson;
    }

    @Override
    public JSONObject replyPrivateWord(String replyContent, String username, int id) {
        JSONObject returnJson = new JSONObject();
        contactAdminMapper.replyPrivateWord(replyContent, secAccountService.findIdByUsername(username), id);
        returnJson.put("status",200);
        JSONObject replyJson = new JSONObject();
        replyJson.put("replyContent",replyContent);
        replyJson.put("replyId",id);
        returnJson.put("result",replyJson);
        return returnJson;
    }
}

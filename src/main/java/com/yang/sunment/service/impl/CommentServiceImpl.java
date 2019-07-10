package com.yang.sunment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.sunment.mapper.CommentMapper;
import com.yang.sunment.model.Categories;
import com.yang.sunment.model.Comment;
import com.yang.sunment.model.CommentInfo;
import com.yang.sunment.service.ArticleService;
import com.yang.sunment.service.CommentLikesService;
import com.yang.sunment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {


    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentLikesService commentLikesService;
    @Autowired
    ArticleService articleService;
    @Autowired
    SecAccountServiceImpl secAccountService;


    /**
     * 通过文章id删除该文章的所有评论
     * @param articleId 文章id'
     */
    @Override
    public void deleteCommentByArticleId(long articleId) {
        commentMapper.deleteCommentByArticleId(articleId);
    }
    /**
     * 通过文章id和原作者获得文章所有评论和回复
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @return
     */
    @Override
    public JSONArray findAllComment(long articleId, String originalAuthor, String username) {
        List<Comment> commentLists = commentMapper.findCommentByArticleIdOriginalAuthorFatherId(articleId, originalAuthor, 0);
        JSONArray commentJsonArray = new JSONArray();
        JSONArray replyJsonArray;
        JSONObject commentJsonObject;
        JSONObject replyJsonObject;
        List<Comment> replyLists;

        for(Comment comment : commentLists){
            commentJsonObject = new JSONObject();

            replyLists = commentMapper.findComment(articleId, originalAuthor, comment.getId());

            replyJsonArray = new JSONArray();
            //封装所有评论中的回复
            for(Comment reply : replyLists){
                replyJsonObject = new JSONObject();
                replyJsonObject.put("answerer",secAccountService.findUsernameById(reply.getCommentId()));
                replyJsonObject.put("commentDate",reply.getCommentDate());
                replyJsonObject.put("commentContent",reply.getCommentContent());
                replyJsonObject.put("respondent",secAccountService.findUsernameById(reply.getResponseId()));
                replyJsonArray.add(replyJsonObject);
            }

            //封装评论
            commentJsonObject.put("id",comment.getId());
            commentJsonObject.put("answerer",secAccountService.findUsernameById(comment.getCommentId()));
            commentJsonObject.put("commentDate",comment.getCommentDate());
            commentJsonObject.put("likes",comment.getLikes());
            commentJsonObject.put("commentContent",comment.getCommentContent());
            commentJsonObject.put("replies",replyJsonArray);
            commentJsonObject.put("avatarImgUrl",secAccountService.getHeadPortraitUrlByUserId(comment.getCommentId()));

            if(username == null){
                commentJsonObject.put("isLiked",0);
            } else {
                if(commentLikesService.isLiked(articleId, originalAuthor, comment.getId(), username)){
                    commentJsonObject.put("isLiked",1);
                } else {
                    commentJsonObject.put("isLiked",0);
                }
            }
            commentJsonArray.add(commentJsonObject);
        }
        commentJsonObject = new JSONObject();
        commentJsonObject.put("status",200);
        commentJsonArray.add(commentJsonObject);
        return commentJsonArray;
    }

     /**
     * 保存留言
     * @param comment 留言
     */
    @Override
    public Comment insertComment(Comment comment, String respondent) {
        String commentContent = comment.getCommentContent();
        if('@' == commentContent.charAt(0)){
            comment.setCommentContent(commentContent.substring(respondent.length() + 1));
        }
        commentMapper.insertComment(comment);
        return comment;
    }
    /**
     * 返回评论中的回复
     * @param comment
     * @return
     */
    @Override
    public JSONArray replyReplyReturn(Comment comment, String answerer, String respondent) {

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject1.put("answerer", answerer);
        jsonObject1.put("respondent", respondent);
        jsonObject1.put("commentContent", comment.getCommentContent());
        jsonObject1.put("commentDate", comment.getCommentDate());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("status",200);

        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);

        return jsonArray;
    }
    /**
     * 更新评论点赞数
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @param id 该评论的id
     * @return 点赞数
     */
    @Override
    public int updateLike(long articleId, String originalAuthor, long id) {
        commentMapper.updateLike(articleId, originalAuthor, id);
        return commentMapper.findLikes(articleId, originalAuthor, id);
    }
    /**
     * 分页获得用户所有的评论
     * @param rows 一页大小
     * @param pageNum 当前页
     * @param username 用户
     * @return
     */
    @Override
    public JSONObject getPersonalComment(int rows, int pageNum, String username) {
        int userId = secAccountService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<Comment> comments = commentMapper.getPersonalComment(userId);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        JSONObject commentJson;
        JSONArray commentJsonArray = new JSONArray();
        for(Comment comment : comments){
            commentJson = new JSONObject();
            commentJson.put("articleId",comment.getArticleId());
            commentJson.put("originalAuthor",comment.getOriginalAuthor());
            commentJson.put("articleTitle",articleService.findArticleTitle(comment.getArticleId(), comment.getOriginalAuthor()).get("articleTitle"));
            commentJson.put("answerer", username);
            if(comment.getFatherId() == 0){
                commentJson.put("commentContent",comment.getCommentContent());
                commentJson.put("replyNum",commentMapper.countReplyNumById(comment.getId()));
            } else {
                commentJson.put("commentContent","@" + secAccountService.findUsernameById(comment.getResponseId()) + " " + comment.getCommentContent());
                commentJson.put("replyNum",commentMapper.countReplyNumByIdAndRespondentId(comment.getFatherId(), userId, comment.getId()));
            }
            commentJson.put("commentDate",comment.getCommentDate());
            commentJsonArray.add(commentJson);
        }
        returnJson.put("result",commentJsonArray);

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
     * 获得最新的5条评论
     * @return
     */
    @Override
    public JSONObject findFiveNewComment(int rows, int pageNum) {

        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<Comment> fiveComments = commentMapper.findFiveNewComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(fiveComments);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        for(Comment comment : fiveComments){
            jsonObject = new JSONObject();
            if(comment.getFatherId() != 0){
                comment.setCommentContent("@" + secAccountService.findUsernameById(comment.getResponseId()) + " " + comment.getCommentContent());
            }
            jsonObject.put("articleId",comment.getArticleId());
            jsonObject.put("originalAuthor",comment.getOriginalAuthor());
            jsonObject.put("answerer",secAccountService.findUsernameById(comment.getCommentId()));
            jsonObject.put("commentDate",comment.getCommentDate().substring(0,10));
            jsonObject.put("commentContent",comment.getCommentContent());
            jsonObject.put("articleTitle",articleService.findArticleTitle(comment.getArticleId(),comment.getOriginalAuthor()).get("articleTitle"));
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

    /**
     * 获得评论总数
     */
    @Override
    public int commentNum() {
        return commentMapper.commentNum();
    }

    /**
     * 分页获得评论管理
     */
    @Override
    public JSONObject getArticleComment(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<CommentInfo> commentInfos = commentMapper.getArticleComment();
        PageInfo<CommentInfo> pageInfo = new PageInfo<>(commentInfos);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        JSONObject articleJson;
        for(CommentInfo commentInfo : commentInfos){
            articleJson = new JSONObject();
            articleJson.put("id",commentInfo.getId());
            articleJson.put("articleTitle",commentInfo.getArticleTitle());
            articleJson.put("commentUsername",commentInfo.getCommentUsername());
            articleJson.put("commentContent",commentInfo.getCommentContent());
            articleJson.put("commentDate",commentInfo.getCommentDate());
            articleJson.put("likes",commentInfo.getLikes());
            returnJsonArray.add(articleJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",returnJsonArray);
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
     * 删除评论
     * @param id 评论id
     * @return 1--删除成功   0--删除失败
     */
    @Override
    public int deleteComment(long id) {
        try {
            //删除评论
            commentMapper.deleteComment(id);

        }catch (Exception e){
            log.error("删除评论失败，评论id=" + id);
            return 0;
        }
        return 1;
    }

}

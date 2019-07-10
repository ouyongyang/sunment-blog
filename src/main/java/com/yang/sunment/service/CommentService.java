package com.yang.sunment.service;

import com.yang.sunment.model.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:评论业务操作
 */
@Service
public interface CommentService {


    /**
     * 通过文章id删除该文章的所有评论
     * @param articleId 文章id'
     */
    void deleteCommentByArticleId(long articleId);

    /**
     * 通过文章id和原作者获得文章所有评论和回复
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @return
     */
    JSONArray findAllComment(long articleId, String originalAuthor, String username);

    /**
     * 保存留言
     * @param comment 留言
     */
    @Transactional
    Comment insertComment(Comment comment, String respondent);

    /**
     * 返回评论中的回复
     * @param comment
     * @return
     */
    JSONArray replyReplyReturn(Comment comment, String answerer, String respondent);

    /**
     * 更新评论点赞数
     * @param articleId 文章id
     * @param originalAuthor 原作者
     * @param commentId 该评论的id
     * @return 点赞数
     */
    int updateLike(long articleId, String originalAuthor, long commentId);

    /**
     * 分页获得用户所有的评论
     * @param rows 一页大小
     * @param pageNum 当前页
     * @param username 用户
     * @return
     */
    JSONObject getPersonalComment(int rows, int pageNum, String username);

    /**
     * 获得最新的5条评论
     * @return
     */
    JSONObject findFiveNewComment(int rows, int pageNum);

    /**
     * 获得评论总数
     */
    int commentNum();

    /**
     * 分页获得评论管理
     */
    JSONObject getArticleComment(int rows, int pageNum);

    /**
     * 删除评论
     * @param id 评论id
     * @return 1--删除成功   0--删除失败
     */
    @Transactional
    int deleteComment(long id);
}

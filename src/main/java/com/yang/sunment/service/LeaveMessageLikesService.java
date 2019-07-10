package com.yang.sunment.service;


import com.yang.sunment.model.LeaveMessageLikes;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:留言中点赞业务操作
 */
@Service
public interface LeaveMessageLikesService {

    /**
     * 是否点赞
     * @param pageName 文章页
     * @param fatherId 父id
     * @param likeId 当前用户id
     * @return true -- 已经点过赞  false -- 还没有点过赞
     */
    boolean isLiked(String pageName, int fatherId, int likeId);

    /**
     * 保存点赞记录
     * @param leaveMessageLikes
     */
    void insertLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes);

}

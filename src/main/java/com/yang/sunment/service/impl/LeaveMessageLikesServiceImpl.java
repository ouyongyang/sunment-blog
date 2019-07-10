package com.yang.sunment.service.impl;


import com.yang.sunment.mapper.LeaveMessageLikesMapper;
import com.yang.sunment.model.LeaveMessageLikes;
import com.yang.sunment.service.LeaveMessageLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:
 */
@Service
public class LeaveMessageLikesServiceImpl implements LeaveMessageLikesService {

    @Autowired
    LeaveMessageLikesMapper leaveMessageLikesMapper;

    /**
     * 是否点赞
     * @param pageName 文章页
     * @param fatherId 父id
     * @param likeId 当前用户id
     * @return true -- 已经点过赞  false -- 还没有点过赞
     */
    @Override
    public boolean isLiked(String pageName, int fatherId, int likeId) {

        return leaveMessageLikesMapper.isLiked(pageName, fatherId, likeId) != null;
    }

    @Override
    public void insertLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes) {
        leaveMessageLikesMapper.insertLeaveMessageLikes(leaveMessageLikes);
    }
}

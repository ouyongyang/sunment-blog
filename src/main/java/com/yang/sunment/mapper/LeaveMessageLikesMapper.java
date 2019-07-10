package com.yang.sunment.mapper;

import com.yang.sunment.model.LeaveMessageLikes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 留言点赞sql
 */
@Mapper
@Repository
public interface LeaveMessageLikesMapper {

    @Select("select like_date AS likeDate from leave_message_likes where page_name=#{pageName} and father_id=#{fatherId} and liker_id=#{likerId}")
    LeaveMessageLikes isLiked(@Param("pageName") String pageName, @Param("fatherId") int fatherId, @Param("likerId") int likerId);

    @Insert("insert into leave_message_likes(page_name,father_id,liker_id,like_date) " +
            "values(#{pageName},#{fatherId},#{likerId},#{likeDate})")
    void insertLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes);
}

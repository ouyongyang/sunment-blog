package com.yang.sunment.mapper;


import com.yang.sunment.model.LeaveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 留言sql
 */
@Mapper
@Repository
public interface LeaveMessageMapper {

    @Insert("insert into leave_message(page_name,father_id,leaver_id,response_id,leave_message_date,likes,leave_message_content) " +
            "values(#{pageName},#{fatherId},#{leaverId},#{responseId},#{leaveMessageDate},#{likes},#{leaveMessageContent})")
    void publishLeaveMessage(LeaveMessage leaveMessage);

    @Select("select id,page_name AS pageName,father_id AS fatherId,likes,leaver_id AS leaverId,response_id AS responseId,leave_message_date AS leaveMessageDate,leave_message_content AS leaveMessageContent from leave_message where page_name=#{pageName} and father_id=#{fatherId} order by id desc")
    List<LeaveMessage> findAllLeaveMessage(@Param("pageName") String pageName, @Param("fatherId") int fatherId);

    @Select("select leaver_id AS leaverId,response_id AS responseId,leave_message_date AS leaveMessageDate,leave_message_content AS leaveMessageContent from leave_message where page_name=#{pageName} and father_id=#{fatherId}")
    List<LeaveMessage> findLeaveMessageReply(@Param("pageName") String pageName, @Param("fatherId") int fatherId);

    @Update("update leave_message set likes=likes+1 where page_name=#{pageName} and id=#{id}")
    void updateLikeByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select IFNULL(max(likes),0) from leave_message where page_name=#{pageName} and id=#{id}")
    int findLikesByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select father_id AS fatherId,page_name AS pageName,leaver_id AS leaverId,response_id AS responseId,leave_message_date AS leaveMessageDate,leave_message_content AS leaveMessageContent from leave_message where leaver_id=#{leaverId} order by id desc")
    List<LeaveMessage> getPersonalLeaveMessage(@Param("leaverId") int leaverId);

    @Select("select count(*) from leave_message where father_id=#{id}")
    int countReplyNumById(@Param("id") int id);

    @Select("select count(*) from leave_message where father_id=#{id} and response_id=#{responseId}")
    int countReplyNumByIdAndRespondentId(@Param("id") int id, @Param("responseId") int responseId);

    @Select("select page_name AS pageName,father_id AS fatherId,leaver_id AS leaverId,response_id AS responseId,leave_message_date AS leaveMessageDate,leave_message_content AS leaveMessageContent from leave_message order by id desc")
    List<LeaveMessage> findFiveNewLeaveWord();

    @Select("select count(*) from leave_message")
    int countLeaveMessageNum();
}

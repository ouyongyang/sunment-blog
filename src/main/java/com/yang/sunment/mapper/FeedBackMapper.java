package com.yang.sunment.mapper;

import com.yang.sunment.model.FeedBack;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe: 反馈sql
 */
@Mapper
@Repository
public interface FeedBackMapper {

    @Insert("insert into feed_back(feed_back_content,contact_info,person_id,feed_back_date) values(#{feedBackContent},#{contactInfo},#{personId},#{feedBackDate})")
    void insertFeedback(FeedBack feedBack);

    @Select("select id,feed_back_content AS feedBackContent,contact_info AS contactInfo,person_id AS personId,feed_back_date AS feedBackDate  from feed_back")
    List<FeedBack> getAllFeedback();

}

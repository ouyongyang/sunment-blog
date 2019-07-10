package com.yang.sunment.mapper;


import com.yang.sunment.model.ContactAdmin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/3/22 20:21
 * Describe: 悄悄话sql
 */
@Mapper
@Repository
public interface ContactAdminMapper {

    @Insert("insert into contact_admin(private_word,publisher_id,replier_id,reply_content,publisher_date) " +
            "values(#{privateWord},#{publisherId},#{replierId},#{replyContent},#{publisherDate})")
    void publishPrivateWord(ContactAdmin contactAdmin);

    @Select("select id,private_word AS privateWord,publisher_id AS publisherId,replier_id AS replierId,reply_content AS replyContent,publisher_date AS publisherDate from contact_admin where publisher_id=#{publisherId} order by id desc")
    List<ContactAdmin> getPrivateWordByPublisher(@Param("publisherId") int publisherId);

    @Select("select id,private_word AS privateWord,publisher_id AS publisherId,replier_id AS replierId,reply_content AS replyContent,publisher_date AS publisherDate  from contact_admin")
    List<ContactAdmin> getAllPrivateWord();

    @Update("update contact_admin set replier_id=#{replierId},reply_content=#{replyContent} where id=#{id}")
    void replyPrivateWord(@Param("replyContent") String replyContent, @Param("replierId") int replierId, @Param("id") int id);

}

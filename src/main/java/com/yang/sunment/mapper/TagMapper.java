package com.yang.sunment.mapper;

import com.yang.sunment.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/2/17 19:24
 * Describe: 标签sql
 */
@Mapper
@Repository
public interface TagMapper {

    @Select("select IFNULL(max(id),0) from tags where tag_name=#{tagName}")
    int findIsExitByTagName(@Param("tagName") String tagName);

    @Insert("insert into tags(tag_name,tag_size) values(#{tagName},#{tagSize})")
    void insertTag(Tag tag);

    @Select("select tag_size from tags where tag_name=#{tagName}")
    int tagsSizeByTagName(String tagName);

    @Select("select id,tag_name AS tagName,tag_size AS tagSize from tags order by id desc")
    List<Tag> findTagsCloud();

    @Select("select count(*) from tags")
    int countTagsNum();

}

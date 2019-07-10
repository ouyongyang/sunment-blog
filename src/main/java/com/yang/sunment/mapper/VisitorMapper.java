package com.yang.sunment.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 访客sql
 */
@Mapper
@Repository
public interface VisitorMapper {

    @Select("select visitor_num from visitor where page_name=#{pageName}")
    long getVisitorNumByPageName(@Param("pageName") String pageName);

    @Insert("insert into visitor(visitor_num,page_name) values(0,#{pageName})")
    void insertVisitorArticle(String pageName);

    @Update("update visitor set visitor_num=(case page_name when 'totalVisitor' then visitor_num+1 when #{pageName} then visitor_num+1 else visitor_num end)")
    void updateVisitorNumByTotalVisitorAndPageName(@Param("pageName") String pageName);

    @Update("update visitor set visitor_num=visitor_num+1 where page_name='totalVisitor'")
    void updateVisitorNumByTotalVisitor();

    @Select("select visitor_num from visitor where page_name='totalVisitor'")
    long getAllVisitor();
}

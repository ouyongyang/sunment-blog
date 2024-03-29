package com.yang.sunment.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 归档sql
 */
@Mapper
@Repository
public interface ArchiveMapper {

    @Select("select archive_name from archives order by id desc")
    List<String> findArchives();

    @Insert("insert into archives(archive_name) values(#{archiveName})")
    void addArchiveName(@Param("archiveName") String archiveName);

    @Select("select IFNULL(max(id),0) from archives where archive_name=#{archiveName}")
    int findArchiveNameByArchiveName(@Param("archiveName") String archiveName);

}

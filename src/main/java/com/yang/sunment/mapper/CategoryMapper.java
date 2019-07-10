package com.yang.sunment.mapper;

import com.yang.sunment.model.Article;
import com.yang.sunment.model.Categories;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/2/17 19:24
 * Describe: 分类sql
 */
@Mapper
@Repository
public interface CategoryMapper {

    @Select("select category_name from categories")
    List<String> findCategoriesName();

    @Select("select id,category_name As categoryName from categories order by id")
    List<Categories> getArticleCategories();

    @Insert("insert into categories(category_name) values(#{categoriesName})")
    void addAtegories(@Param("categoriesName") String categoriesName);

    @Delete("delete from categories where id=#{id}")
    void deleteCategories(long id);

    @Select("select count(*) from categories where category_name=#{categoriesName}")
    int selectBycategoriesName(@Param("categoriesName") String categoriesName);
}

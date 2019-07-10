package com.yang.sunment.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.sunment.mapper.CategoryMapper;
import com.yang.sunment.model.Article;
import com.yang.sunment.model.Categories;
import com.yang.sunment.service.ArticleService;
import com.yang.sunment.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/2/17 19:24
 * Describe:
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ArticleService articleService;

    /**
     * 获得所有的分类
     * @return
     */
    @Override
    public JSONArray findCategoriesName() {
        List<String> categoryNames = categoryMapper.findCategoriesName();
        return JSONArray.fromObject(categoryNames);
    }
    /**
     * 获得所有的分类以及该分类的文章总数
     * @return
     */
    @Override
    public JSONObject allCategoriesNameArticleNum() {
        List<String> categoryNames = categoryMapper.findCategoriesName();
        JSONObject categoryJson;
        JSONArray categoryJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        for(String categoryName : categoryNames){
            categoryJson = new JSONObject();
            categoryJson.put("categoryName",categoryName);
            categoryJson.put("categoryArticleNum",articleService.countArticleCategory(categoryName));
            categoryJsonArray.add(categoryJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",categoryJsonArray);
        return returnJson;
    }

    /**
     * 分页获得文章分类
     */
    @Override
    public JSONObject getArticleCategories(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Categories> categories = categoryMapper.getArticleCategories();
        PageInfo<Categories> pageInfo = new PageInfo<>(categories);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        JSONObject articleJson;
        for(Categories categorie : categories){
            articleJson = new JSONObject();
            articleJson.put("id",categorie.getId());
            articleJson.put("categoryName",categorie.getCategoryName());
            returnJsonArray.add(articleJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",returnJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }

    /**
     * 添加文章分类
     * @return
     */
    @Override
    public int addAtegories(String categoriesName) {
        int i = categoryMapper.selectBycategoriesName(categoriesName);
        if(i>0){
            return 1;
        }else {
            categoryMapper.addAtegories(categoriesName);
            return 0;
        }

    }

    /**
     * 通过id删除文章分类
     * @param id 文章分类id
     * @return 1--删除成功  0--删除失败
     */
    @Override
    public int deleteCategories(long id) {
        try {
            //删除文章分类
            categoryMapper.deleteCategories(id);

        }catch (Exception e){
            log.error("删除文章分类失败，文章分类id=" + id);
            return 0;
        }
        return 1;
    }


}

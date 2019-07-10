package com.yang.sunment.controller;


import com.yang.sunment.service.ArticleService;
import com.yang.sunment.service.CategoryService;
import com.yang.sunment.utils.TransCodingUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:
 */
@RestController
@RequestMapping("/categories")
public class CategoriesControl {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /**
     * 获得所有分类名以及每个分类名的文章数目
     * @return
     */
    @GetMapping("/allCategoriesNameArticleNum")
    public JSONObject allCategoriesNameArticleNum(){
        return categoryService.allCategoriesNameArticleNum();
    }

    /**
     * 分页获得该分类下的文章
     * @return
     */
    @GetMapping("/getCategoryArticle")
    public JSONObject getCategoryArticle(@RequestParam("category") String category,
                                         HttpServletRequest request){

        try {
            category = TransCodingUtil.unicodeToString(category);
        } catch (Exception e){
        }
        int rows = Integer.parseInt(request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        return articleService.findArticleByCategory(category, rows, pageNum);
    }


}

package com.yang.sunment.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: OYY
 * @Date: 2019/2/17 19:24
 * Describe:分类业务操作
 */
@Service
public interface CategoryService {



    /**
     * 获得所有的分类
     * @return
     */
    JSONArray findCategoriesName();

    /**
     * 获得所有的分类以及该分类的文章总数
     * @return
     */
    JSONObject allCategoriesNameArticleNum();

    /**
     * 分页获得文章分类
     */
    JSONObject getArticleCategories(int rows, int pageNum);

    /**
     * 添加文章分类
     * @return
     */
    @Transactional
    int addAtegories(String categoriesName);

    /**
     * 通过id删除文章分类
     * @param id 文章分类id
     * @return 1--删除成功  0--删除失败
     */
    @Transactional
    int deleteCategories(long id);

}

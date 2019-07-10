package com.yang.sunment.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:访客业务操作
 */
@Service
public interface VisitorService {


    /**
     * 通过页名获得访客量
     * @param pageName 页名
     * @return 访客量
     */
    long getNumByPageName(String pageName);

    /**
     * 发布文章后保存该文章的访客量
     * @param pageName 文章url
     */
    void insertVisitorArticle(String pageName);

    /**
     * 通过页名增加访客量
     * @param pageName
     */
    void addVisitorNumByPageName(String pageName, HttpServletRequest request);

    /**
     * 通过页名获得总访问量和访客量
     * @param pageName 页名
     * @return
     */
    JSONObject getVisitorNumByPageName(String pageName);

    /**
     * 获得总访问量
     * @return
     */
    long getAllVisitor();
}

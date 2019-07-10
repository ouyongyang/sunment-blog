package com.yang.sunment.service.impl;


import com.yang.sunment.mapper.VisitorMapper;
import com.yang.sunment.service.VisitorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 访客实现类
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    /**
     * 通过页名获得访客量
     * @param pageName 页名
     * @return 访客量
     */
    @Override
    public long getNumByPageName(String pageName) {
        return visitorMapper.getVisitorNumByPageName(pageName);
    }

    /**
     * 发布文章后保存该文章的访客量
     * @param pageName 文章url
     */
    @Override
    public void insertVisitorArticle(String pageName) {
        visitorMapper.insertVisitorArticle(pageName);
    }

    /**
     * 通过页名增加访客量
     * @param pageName
     */
    @Override
    public void addVisitorNumByPageName(String pageName, HttpServletRequest request) {
        String visitor;
        if("visitorVolume".equals(pageName)){
            visitor = (String) request.getSession().getAttribute("visitor");
            if(visitor == null){
                visitorMapper.updateVisitorNumByTotalVisitorAndPageName(pageName);
                request.getSession().setAttribute("visitor","yes");
            }else {
                visitorMapper.updateVisitorNumByTotalVisitor();
            }
        } else {
            visitor = (String) request.getSession().getAttribute(pageName);
            if(visitor == null){
                visitorMapper.updateVisitorNumByTotalVisitorAndPageName(pageName);
                request.getSession().setAttribute(pageName, "yes");
            } else {
                visitorMapper.updateVisitorNumByTotalVisitor();
            }
        }
    }

    /**
     * 通过页名获得总访问量和访客量
     * @param pageName 页名
     * @return
     */
    @Override
    public JSONObject getVisitorNumByPageName(String pageName) {
        long totalVisitor = visitorMapper.getVisitorNumByPageName("totalVisitor");
        long pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalVisitor", totalVisitor);
        jsonObject.put("pageVisitor", pageVisitor);
        return jsonObject;
    }

    /**
     * 获得总访问量
     * @return
     */
    @Override
    public long getAllVisitor() {
        return visitorMapper.getAllVisitor();
    }
}

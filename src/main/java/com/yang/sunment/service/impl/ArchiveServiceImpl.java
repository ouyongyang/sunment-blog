package com.yang.sunment.service.impl;


import com.yang.sunment.mapper.ArchiveMapper;
import com.yang.sunment.service.ArchiveService;
import com.yang.sunment.service.ArticleService;
import com.yang.sunment.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe:
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    ArticleService articleService;

    /**
     * 获得归档信息
     * @return
     */
    @Override
    public JSONObject findArchiveNameAndArticleNum() {
        List<String> archives = archiveMapper.findArchives();
        JSONArray archivesJsonArray = new JSONArray();
        JSONObject archiveJson;
        TimeUtil timeUtil = new TimeUtil();
        for(String archiveName : archives){
            archiveJson = new JSONObject();
            archiveJson.put("archiveName",archiveName);
            archiveName = timeUtil.timeYearToWhippletree(archiveName);
            archiveJson.put("archiveArticleNum",articleService.countArticleArchive(archiveName));
            archivesJsonArray.add(archiveJson);
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        returnJson.put("result", archivesJsonArray);
        return returnJson;
    }
    /**
     * 添加归档日期
     * @param archiveName
     */
    @Override
    public void addArchiveName(String archiveName) {
        int archiveNameIsExit = archiveMapper.findArchiveNameByArchiveName(archiveName);
        if(archiveNameIsExit == 0){
            archiveMapper.addArchiveName(archiveName);
        }
    }

}

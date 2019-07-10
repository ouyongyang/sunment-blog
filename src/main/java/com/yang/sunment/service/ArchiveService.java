package com.yang.sunment.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2018/1/17 19:24
 * Describe: 归档业务操作
 */
@Service
public interface ArchiveService {

    /**
     * 获得归档信息
     * @return
     */
    JSONObject findArchiveNameAndArticleNum();

    /**
     * 添加归档日期
     * @param archiveName
     */
    void addArchiveName(String archiveName);

}

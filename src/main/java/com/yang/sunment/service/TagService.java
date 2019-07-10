package com.yang.sunment.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: OYY
 * @Date: 2019/2/17 19:24
 * Describe:标签业务操作
 */
@Service
public interface TagService {

    /**
     * 获得标签云
     * @return
     */
    JSONObject findTagsCloud();

    /**
     * 加入标签
     * @param tags 一群标签
     * @param tagSize 标签大小
     */
    void addTags(String[] tags, int tagSize);

    /**
     * 通过标签名获得标签大小
     * @param tagName
     * @return
     */
    int tagsSizeByTagName(String tagName);

    /**
     * 获得标签云数量
     * @return
     */
    int countTagsNum();
}

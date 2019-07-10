package com.yang.sunment.service.impl;

import com.yang.sunment.mapper.TagMapper;
import com.yang.sunment.model.Tag;
import com.yang.sunment.service.TagService;
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
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    /**
     * 获得标签云
     * @return
     */
    @Override
    public JSONObject findTagsCloud() {
        List<Tag> tags = tagMapper.findTagsCloud();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("result",JSONArray.fromObject(tags));
        jsonObject.put("tagsNum",tags.size());
        return jsonObject;
    }

    /**
     * 加入标签
     * @param tags 一群标签
     * @param tagSize 标签大小
     */
    @Override
    public void addTags(String[] tags, int tagSize) {
        for(String tag : tags){
            if(tagMapper.findIsExitByTagName(tag) == 0){
                Tag t = new Tag(tag, tagSize);
                tagMapper.insertTag(t);
            }
        }
    }

    /**
     * 通过标签名获得标签大小
     * @param tagName
     * @return
     */
    @Override
    public int tagsSizeByTagName(String tagName) {
        return tagMapper.tagsSizeByTagName(tagName);
    }

    /**
     * 获得标签云数量
     * @return
     */
    @Override
    public int countTagsNum() {
        return tagMapper.countTagsNum();
    }
}

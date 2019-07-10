package com.yang.sunment.service;

import com.yang.sunment.model.FeedBack;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: OYY
 * @Date: 2019/3/28 12:31
 * Describe:反馈业务操作
 */
@Service
public interface FeedBackService {

    /**
     * 保存反馈信息
     * @param feedBack
     * @return
     */
    @Transactional
    JSONObject submitFeedback(FeedBack feedBack);

    /**
     * 获得所有的反馈
     * @return
     */
    JSONObject getAllFeedback(int rows, int pageNum);

}

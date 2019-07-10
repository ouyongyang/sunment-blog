package com.yang.sunment.repository.mybatis;

import com.yang.sunment.model.SecAccount;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangocean
 * @Date: 2018/6/5 19:37
 * Describe:
 */
@Repository
public interface SecAccountRepository {

    /**
     *  通过手机号查找用户
     * @param phone 手机号
     * @return 用户
     */
    SecAccount findByPhone(String phone);

}

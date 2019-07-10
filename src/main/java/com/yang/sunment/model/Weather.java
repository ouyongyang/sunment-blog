package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2018/4/17 19:24
 * Describe: 天气
 */
@Data
public class Weather {

    /**
     * id
     */
    private long id;

    /**
     * 城市天气编码
     */
    private String code;

    /**
     * 城市名
     */
    private String cityName;



}

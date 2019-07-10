package com.yang.sunment.mapper;

import com.yang.sunment.model.Weather;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: OYY
 * @Date: 2018/4/17 19:24
 * Describe: 天气
 */
@Mapper
@Repository
public interface WeatherMapper {


    @Select("select id,code,city_name AS cityName from weather where city_name=#{cityName}")
    List<Weather> findByCityName(@Param("cityName") String cityName);

}

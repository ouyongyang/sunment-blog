package com.yang.sunment.component;


import com.yang.sunment.mapper.WeatherMapper;
import com.yang.sunment.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yang.sunment.component.WebPage.spiderURL;
import static com.yang.sunment.component.split.dateTojson;
import static com.yang.sunment.component.split.readerFile;


@Service
public class ScheduledService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    WeatherMapper weatherMapper;

    @Scheduled(cron = "0 2 0/1 * * ? ")//每小时的2分0秒就执行一次
    public void timingGain() {
        List<Weather> weatherList = weatherMapper.findByCityName("广州");
        List<String> list = new ArrayList<String>();
        String regex = "(http|https)://[\\w+\\.?/?]+\\.[A-Za-z]+";
        //文件路径和文件名
        String filePath = "C:\\personal\\spider\\web\\";
        String fileName = "8btc_content1.txt";
        weatherList.iterator().forEachRemaining(str-> {
            if (str.getCode() != null) {
                String url = "http://www.weather.com.cn/weather/" + str.getCode() + ".shtml";
                spiderURL(url, regex, "8btc");
                String data = readerFile(filePath, fileName);
                List<String> listdata=dateTojson(data);
                list.addAll(listdata);
                //System.out.println("城市:" + str.getCityName());
            }
        });
        redisTemplate.opsForValue().set("weather",list);
        System.out.println("777++++");
    }

}
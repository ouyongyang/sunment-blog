package com.yang.sunment.controller;

import com.yang.sunment.mapper.WeatherMapper;
import com.yang.sunment.model.FeedBack;
import com.yang.sunment.model.Weather;
import com.yang.sunment.service.CommentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yang.sunment.component.WebPage.spiderURL;
import static com.yang.sunment.component.split.dateTojson;
import static com.yang.sunment.component.split.readerFile;

/**
 * @author: OYY
 * @Date: 2018/4/17 19:24
 * Describe: 天气
 */
@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    WeatherMapper weatherMapper;

    @ResponseBody
    @RequestMapping("/sevenDay")
    public JSONObject sevenDay(){
        JSONObject returnJson = new JSONObject();
       /* List<Weather> weatherList = weatherMapper.findByCityName("广州");
        List<String> list = new ArrayList<String>();
        String regex = "(http|https)://[\\w+\\.?/?]+\\.[A-Za-z]+";
        //文件路径和文件名
        String filePath = "D:\\spider\\web\\";
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
        redisTemplate.opsForValue().set("weather",list);*/
        List<String> listWeather = (List<String>) redisTemplate.opsForValue().get("weather");
        returnJson.put("today",listWeather.get(0));
        returnJson.put("y1",listWeather.get(1));
        returnJson.put("y2",listWeather.get(2));
        returnJson.put("y3",listWeather.get(3));
        returnJson.put("y4",listWeather.get(4));
        returnJson.put("y5",listWeather.get(5));
        returnJson.put("y6",listWeather.get(6));
        return returnJson;
    }
    //转json
    public static String listToJson(List<?> list) {

        String jsonStr = JSONArray.fromObject(list).toString();

        //System.out.println("list转json:" + jsonStr);

        return jsonStr;

    }
}

package com.yang.sunment.component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: OYY
 * @Date: 2018/4/17 19:24
 * Describe: 爬天气
 */
public class WebPage {
    public static void main(String[] args) {
        String url = "http://www.weather.com.cn/weather/101010100.shtml";
        String regex = "(http|https)://[\\w+\\.?/?]+\\.[A-Za-z]+";
        spiderURL( url, regex, "8btc" );

    }

    // String regex = "(http|https)://[\\w+\\.?/?]+\\.[A-Za-z]+";
    public static void spiderURL(String url, String regex, String filename) {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String time = sdf.format( new Date() );
        URL realURL = null;
        URLConnection connection = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        PrintWriter pw1 = null;
        Pattern pattern = Pattern.compile( regex );
        try {
            realURL = new URL( url );
            connection = realURL.openConnection();
            //connection.connect();
            File fileDir = new File( "C:/personal/spider/web/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }            //将爬取到的内容放到E盘相应目录下
            pw = new PrintWriter( new FileWriter( "C:/personal/spider/web/" + filename + "_content1.txt" ), true );
            pw1 = new PrintWriter( new FileWriter( "C:/personal/spider/web/" + filename + "_URL1.txt" ), true );
            br = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
            String line = null;
            while ((line = br.readLine()) != null) {
                pw.print( line );
                Matcher matcher = pattern.matcher( line );
                while (matcher.find()) {
                    pw1.print( matcher.group() );
                }
            }
            //System.out.println( "爬取成功！" );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                pw.close();
                pw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

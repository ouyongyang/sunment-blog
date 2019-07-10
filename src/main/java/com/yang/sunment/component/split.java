package com.yang.sunment.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: OYY
 * @Date: 2018/4/17 19:24
 * Describe: 天气
 */
public class split {
    //文件路径和文件名
    private static String filePath = "C:\\personal\\spider\\web\\";
    private static String fileName = "8btc_content1.txt";

    public static void main(String[] args) {
        String data = readerFile( filePath, fileName );
        splitData( data );
        splitData2( data );
        splitData3( data );
        splitData4( data );
        splitData1( data );

    }

    //把数据转成jso格式
    public static List<String> dateTojson(String data){
        String s1=splitData(data);
        List<String> list = new ArrayList<String>();
        String s[] = s1.split(",");
        //有顺序的map
        //Map<String, Object> map = new LinkedHashMap<String, Object>();
        /*map.put("当天日期",s[0]);
        map.put("天气",s[2]);
        map.put("温度",s[3]);
        map.put("风向",s[4]);
        map.put("风级",s[5]);*/
        String today="实时天气 : "+s[0]+",  天气 : "+s[2]+",  温度 : "+s[3]+",  风级 : "+s[5]+",  风向 : "+s[4];

      /*  String s2=splitData2(data);
        String air[] = s2.split(",");
        map.put("空气指数",air[0]);
        map.put("空气指数建议",air[1]);

        String light[]=splitData3(data);
        //String light[] = s3.split(",");
        map.put("紫外线指数",light[0]);
        map.put("紫外线指数建议",light[1]);

        String clothes[]=splitData4(data);
        //String clothes[] = s4.split(",");
        map.put("穿衣指数",clothes[0]);
        map.put("穿衣指数建议",clothes[1]);*/

        //Map<String,Object> map1 = new LinkedHashMap<String,Object>();
        //Map<String,Object> map2 = new LinkedHashMap<String,Object>();
        String[] s5=splitData1(data);
        String[] st1=s5[0].split(",");
        String y1="日期 : "+st1[0]+",  天气 : "+st1[1]+",  温度 : "+st1[2]+",  风级 : "+st1[3];
      /*  map2.put("天气",st1[1]);
        map2.put("温度",st1[2]);
        map2.put("风级",st1[3]);
        map1.put(st1[0],map2);*/

        //Map<String,Object> map3 = new LinkedHashMap<String,Object>();
        String[] st2=s5[1].split(",");
        String str=st2[0].substring(0,3);
        String y2="日期 : "+str+" ( 后天 ),  天气 : "+st2[1]+",  温度 : "+st2[2]+",  风级 : "+st2[3];
       /* map3.put("天气",st2[1]);
        map3.put("温度",st2[2]);
        map3.put("风级",st2[3]);
        map1.put(st2[0],map3);*/

        //Map<String,Object> map4 = new LinkedHashMap<String,Object>();
        String[] st3=s5[2].split(",");
        String y3="日期 : "+st3[0]+",  天气 : "+st3[1]+",  温度 : "+st3[2]+",  风级 : "+st3[3];
       /* map4.put("天气",st3[1]);
        map4.put("温度",st3[2]);
        map4.put("风级",st3[3]);
        map1.put(st3[0],map4);*/

        //Map<String,Object> map5 = new LinkedHashMap<String,Object>();
        String[] st4=s5[3].split(",");
        String y4="日期 : "+st4[0]+",  天气 : "+st4[1]+",  温度 : "+st4[2]+",  风级 : "+st4[3];
       /* map5.put("天气",st4[1]);
        map5.put("温度",st4[2]);
        map5.put("风级",st4[3]);
        map1.put(st4[0],map5);

        Map<String,Object> map6 = new LinkedHashMap<String,Object>();*/
        String[] st5=s5[4].split(",");
        String y5="日期 : "+st5[0]+",  天气 : "+st5[1]+",  温度 : "+st5[2]+",  风级 : "+st5[3];
       /* map6.put("天气",st5[1]);
        map6.put("温度",st5[2]);
        map6.put("风级",st5[3]);
        map1.put(st5[0],map6);

        Map<String,Object> map7 = new LinkedHashMap<String,Object>();*/
        String[] st6=s5[5].split(",");
        String y6="日期 : "+st6[0]+",  天气 : "+st6[1]+",  温度 : "+st6[2]+",  风级 : "+st6[3];
        /*map7.put("天气",st6[1]);
        map7.put("温度",st6[2]);
        map7.put("风级",st6[3]);
        map1.put(st6[0],map7);*/

        list.add(today);
        list.add(y1);
        list.add(y2);
        list.add(y3);
        list.add(y4);
        list.add(y5);
        list.add(y6);
        return list;
    }

    //实时天气
    public static String splitData(String data) {
        Pattern pattern = Pattern.compile( "(.*)(var hour3data=)(.*?)(\"])(.*)" );
        Matcher matcher = pattern.matcher( data );

        StringBuffer sb = new StringBuffer();
        if (matcher.matches()) {
			/*for(int i=0;i<matcher.groupCount();i++){
				sb.append(matcher.group(i)+"\n");
			}*/
            sb.append( matcher.group( 3 ) );
        }
        String[] strings = sb.toString().split( "\",\"" );
        String temple=null;
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                strings[i] = strings[i].substring( 8 );
            }
            //判断当前时间在哪个时间段内就显示对应的天气情况
            if (isBelong( "08:00", "11:00" )) {
                //System.out.println( "当前天气：" + strings[0] );
                //break;
                temple=strings[0];
            }
            if (isBelong( "11:00", "14:00" )) {
                //System.out.println( "当前天气：" + strings[1] );
                //break;
                temple=strings[1];
            }
            if (isBelong( "14:00", "17:00" )) {
                //System.out.println( "当前天气：" + strings[2] );
                //break;
                temple=strings[2];
            }
            if (isBelong( "17:00", "18:00" )) {
                //System.out.println( "当前天气：" + strings[3] );
                //break;
                temple=strings[3];
            }
            //由于下午6点天气网会自动更新的数据，所以数组的顺序被重新排序，需要执行新的顺序
            if (isBelong( "18:00", "20:00" )) {
                //System.out.println( "当前天气：" + strings[0] );
                //break;
                temple=strings[0];
            }
            if (isBelong( "20:00", "23:00" )) {
                //System.out.println( "当前天气：" + strings[0] );
                //break;
                temple=strings[0];
            }
            if (isBelong( "23:00", "24:00" )) {
                //System.out.println( "当前天气：" + strings[1] );
                //break;
                temple=strings[1];
            }
            if (isBelong( "00:00", "02:00" )) {
                //System.out.println( "当前天气：" + strings[1] );
                //break;
                temple=strings[1];
            }
            if (isBelong( "02:00", "05:00" )) {
                //System.out.println( "当前天气：" + strings[2] );
                //break;
                temple=strings[2];
            }
            if (isBelong( "05:00", "08:00" )) {
                //System.out.println( "当前天气：" + strings[3] );
                //break;
                temple=strings[3];
            }
            //System.out.println(strings[i]);
        }
        return temple;
    }

    //未来一周内天气预报
    public static String[] splitData1(String data) {
        //Pattern pattern = Pattern.compile( "(.*)(<input type=\"hidden\" id=\"hidden_title\" value=\")(.*?)( />)(.*)" );

        Pattern pattern = Pattern.compile( "(.*)(<ul class=\"t clearfix\"><li class=\"sky skyid lv)(.*?)(</p><div class=\"slid\"></div></li></ul><i class=\"line1\"></i>)(.*)" );

        Matcher matcher = pattern.matcher( data );

        StringBuffer sb = new StringBuffer();
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        String[] temple=new String[7];

        String[] strings = sb.toString().split( "<li class=\"sky skyid lv" );
        strings = removestrings( strings );  //因为今天的天气预报前面的实时天气已经有了，所以不需要重复
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                strings[i] = towsplit( strings[i] );
            }
            //System.out.println( strings[i] );
            temple[i]=strings[i];
        }
        return temple;
    }

    //空气指数
    public static String splitData2(String data) {
        Pattern pattern = Pattern.compile( "(.*)(<li class=\"li6\">)(.*?)(</li>)(.*)" );
        Matcher matcher = pattern.matcher( data );

        StringBuffer sb = new StringBuffer();
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        String temple=null;
        String temple1=null;
        String[] strings = sb.toString().split( "空气污染扩散指数</em><p>" );
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                strings[i] = strings[i].substring( 13, strings[i].length() - 11 );
                //System.out.print( "空气指数：" + strings[i] + " ; " );
                temple=strings[i];
            }
            if (i == 1) {
                strings[i] = strings[i].substring( 0, strings[i].length() - 4 );
                //System.out.println( "建议：" + strings[i] );
                temple1=strings[i];
            }
        }
        return temple+","+temple1;
    }

    //紫外线指数
    public static String[] splitData3(String data) {
        Pattern pattern = Pattern.compile( "(.*)(<div class=\"hide show\"><ul class=\"clearfix\"><li class=\"li1\"><i></i><span>)(.*?)(</p>)(.*)" );
        Matcher matcher = pattern.matcher( data );

        StringBuffer sb = new StringBuffer();
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        String[] temple=new String[2];
        String[] strings = sb.toString().split( "</span><em>紫外线指数</em><p>" );
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                strings[i] = strings[i].substring( 0 );
                //System.out.print( "紫外线指数：" + strings[i] + " ; " );
                temple[i]=strings[i];
            }
            if (i == 1) {
                strings[i] = strings[i].substring( 0 );
                //System.out.println( "建议：" + strings[i] );
                temple[i]=strings[i];
            }
        }
        return temple;
    }

    //穿衣推荐
    public static String[] splitData4(String data) {
        Pattern pattern = Pattern.compile( "(.*)(<div class=msg_block></div></div><i></i><span>)(.*?)(</p>)(.*)" );
        Matcher matcher = pattern.matcher( data );

        StringBuffer sb = new StringBuffer();
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        String[] temple=new String[2];
        String[] strings = sb.toString().split( "</span><em>穿衣指数</em><p>" );
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                strings[i] = strings[i].substring( 0 );
                //System.out.print( "穿衣指数：" + strings[i] + " ; " );
                temple[i]=strings[i];
            }
            if (i == 1) {
                strings[i] = strings[i].substring( 0 );
                //System.out.println( "建议：" + strings[i] );
                temple[i]=strings[i];
            }
        }
        //System.out.println( "未来一周天气：" );
        return temple;
    }


    //删除数组中其中一个元素
    public static String[] removestrings(String[] str) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            list.add( str[i] );
        }
        list.remove( 0 ); //删除第一个元素，即今天的天气预报
        return list.toArray( new String[1] ); //返回一个包含所有对象的指定类型的数组
    }

    //再切割方法(未来一周天气情况)
    public static String towsplit(String trg) {
        StringBuffer sb = new StringBuffer();
        //日期
        Pattern pattern = Pattern.compile( "(.*)(<h1>)(.*?)(</h1>)(.*)" );
        Matcher matcher = pattern.matcher( trg );
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        //天气
        pattern = Pattern.compile( "(.*)(class=\"wea\">)(.*?)(</p>)(.*)" );
        matcher = pattern.matcher( trg );
        if (matcher.matches()) {
            sb.append( "," + matcher.group( 3 ) + "," );
        }
        //温度起
        pattern = Pattern.compile( "(.*)(/<i>)(.*?)(</i>)(.*)" );
        matcher = pattern.matcher( trg );
        if (matcher.matches()) {
            sb.append( matcher.group( 3 ) );
        }
        //温度止
        pattern = Pattern.compile( "(.*)(<p class=\"tem\"><span>)(.*?)(</span>)(.*)" );
        matcher = pattern.matcher( trg );
        if (matcher.matches()) {
            sb.append( "~" + matcher.group( 3 ) + "℃" );
        }
        //风力
        pattern = Pattern.compile( "(.*)(</span></em><i>)(.*?)(</i>)(.*)" );
        matcher = pattern.matcher( trg );
        if (matcher.matches()) {
            sb.append( "," + matcher.group( 3 ) );
        }

        return sb.toString();
    }

    /*解析文件*/
    public static String readerFile(String filePath, String fileName) {
        File file = new File( filePath + fileName );
        StringBuffer sb = new StringBuffer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( file );
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read( bytes )) != -1) {
                String str = new String( bytes, 0, len );
                sb.append( str );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    //判断当前时间
    public static Boolean isBelong(String begin, String end) {
        SimpleDateFormat df = new SimpleDateFormat( "HH:mm" );
        //设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse( df.format( new Date() ) );
            //now = df.parse( "08:09" );
            beginTime = df.parse( begin );
            endTime = df.parse( end );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean flag = belongCalendar( now, beginTime, endTime );
        return flag;
    }

    /*
     * 判断时间是否在时间段内
     */

    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime( nowTime );
        Calendar begin = Calendar.getInstance();
        begin.setTime( beginTime );
        Calendar end = Calendar.getInstance();
        end.setTime( endTime );
        if (date.after( begin ) && date.before( end )) {
            return true;
        } else {
            return false;
        }
    }
}
package com.xuyang.utilcode.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by XuYang
 * 2019-11-25
 * Email:544066591@qq.com
 */
public class DateUtils {
    public static Date maxDate(List<Date> dates) {
        return Collections.max(dates);
    }

    public static Date stringToDate(String s) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(s);
    }

    public static String dateToString(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static boolean timeCompare(String startTime, String endTime){
       //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                //结束时间小于开始时间
                return false;
            }else if (date2.getTime()==date1.getTime()){
                //开始时间与结束时间相同
                return false;
            }else if (date2.getTime()>date1.getTime()){
                //结束时间大于开始时间
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

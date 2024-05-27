package com.edusys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author duyplus
 */
public class XDate {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /*
     * Chuyển đổi từ String sang Date
     */
    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                sdf.applyPattern(pattern[0]);
            }
            if (date == null) {
                return XDate.now();
            }
            return sdf.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Chuyển đổi từ Date sang String
     */
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            sdf.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = XDate.now();
        }
        return sdf.format(date);
    }

    /*
     * Lấy thời gian hiện tại
     */
    public static Date now() {
        return new Date(); //new Date lấy giờ hiện tại
    }

    /*
     * Bổ sung số ngày vào thời gian
     */
    public static Date addDays(int days) {
        //Date date
        //date.setTime(date.getTime()+days*24*60*60*1000);
        //return date
        //setTime gán cho biến date 1 mốc thời gian được chuyển từ milisecon (long)
        //getTime chuyển mốc thời gian của biến date thành milisecon (long)
        Calendar cal = Calendar.getInstance();
        cal.setTime(XDate.now());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /*
     * Bổ sung số ngày vào thời gian hiện hành
     */
    public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }
}

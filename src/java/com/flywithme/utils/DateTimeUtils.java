    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class DateTimeUtils {

    /**
     * Chuyển đổi thời gian từ định dạng 12 giờ (hh:mm a) sang 24 giờ (HH:mm:ss).
     * 
     * @param time12h Thời gian định dạng 12 giờ (vd: "12:00 AM")
     * @return Thời gian dạng Timestamp với định dạng 24 giờ (vd: "00:00:00")
     */
    public static Timestamp convertTo24HourFormat(Timestamp time12h) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a"); // Định dạng 12 giờ
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss"); // Định dạng 24 giờ

            // Chuyển Timestamp thành String theo định dạng 12 giờ
            String timeString = new SimpleDateFormat("hh:mm a").format(time12h);

            // Chuyển đổi sang định dạng 24 giờ và trả về Timestamp
            String time24h = outputFormat.format(inputFormat.parse(timeString));
            return Timestamp.valueOf("1970-01-01 " + time24h + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Nếu lỗi, trả về null
        }
    }

    /**
     * Chuyển đổi thời gian từ định dạng 24 giờ (HH:mm:ss) sang 12 giờ (hh:mm a).
     * 
     * @param time24h Thời gian định dạng 24 giờ (vd: "13:45:00")
     * @return Thời gian dạng Timestamp với định dạng 12 giờ (vd: "01:45 PM")
     */
    public static Timestamp convertTo12HourFormat(Timestamp time24h) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss"); // Định dạng 24 giờ
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a"); // Định dạng 12 giờ

            // Chuyển Timestamp thành String theo định dạng 24 giờ
            String timeString = new SimpleDateFormat("HH:mm:ss").format(time24h);

            // Chuyển đổi sang định dạng 12 giờ và trả về Timestamp
            String time12h = outputFormat.format(inputFormat.parse(timeString));
            return Timestamp.valueOf("1970-01-01 " + time12h + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Nếu lỗi, trả về null
        }
    }
}
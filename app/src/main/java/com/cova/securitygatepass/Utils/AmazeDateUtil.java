package com.cova.securitygatepass.Utils;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AmazeDateUtil {
    public static final String TAG = "Date utils";

    public static final String format_dd_mm_yy = "dd/MM/yy";
    public static final String format_mm_dd_yy = "MM-dd-yy";
    public static final String format_dd_mm_yy_hh_mm = "dd-MM-yy hh:mm";
    public static final String format_dd_mm_yy_hh_mm_ss = "dd-MM-yy hh:mm:ss";
    public static final String format_dd_mm_yy_HH_mm = "dd/MM/yy HH:mm";
    public static final String format_dd_mm_yy_HH_mm_ss = "dd-MM-yy hh:mm:ss";
    public static final String format_mm_dd_yy_hh_mm_ss = "MM-dd-yy";
    public static final String format_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String format_hh_mm_a = "hh:mm a";
    public static final String format_hh_mm = "hh:mm";
    public static final String format_HH_mm_a = "HH:mm a";
    public static final String format_HH_mm = "hh:mm";
    public static final String format_Wed_Jul_4 = "EEE, MMM d";
    public static final String format_Wed_Jul_4_2016 = "EEE, MMM d yyyy";
    public static final String format_Wed_Jul_4_2016_21_30 = "EEE, MMM d yyyy HH:mm";
    public static final String format_Wed_Jul_4_2016_21_30_00 = "EEE, MMM d yyyy HH:mm:ss";
    public static final String format_Wed_Jul_4_2016_08_30_pm = "EEE, MMM d yyyy hh:mm a";
    public static final String utc_format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String gate_pass_time_format = "yyyy-MM-dd'T'HH:mm:ss";


    public static String format_date(String source_format, String expected_format, String datestring) {
        String inputPattern = source_format;
        String outputPattern = expected_format;
        Date date = null;
        String formated_date_str = null;
        SimpleDateFormat inputFormat = null;
        SimpleDateFormat outputFormat = null;

        try {
            inputFormat = new SimpleDateFormat(inputPattern);
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = "error in source time format";
            return formated_date_str;
        }
        try {
            outputFormat = new SimpleDateFormat(outputPattern);
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = "error in expected time format";
            return formated_date_str;
        }

        try {

            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
            formated_date_str = "invalid input date";
            return formated_date_str;
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = e.getLocalizedMessage();
        } finally {
            return formated_date_str;
        }

    }

    public static String minutes_to_hour(int minutes) {
        try {
            int hours = minutes / 60;
            int min = minutes % 60;
            if (minutes < 60) {
                return minutes + " Min";
            } else {
                return hours + "." + min + " Hour";
            }
        } catch (Exception e) {
            return "invalid minute";
        }
    }

    public static boolean isDateInCurrentWeek(String dt_string, String date_format) {
        String inputPattern = date_format;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(dt_string);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    public static boolean isToday(String dt_string, String date_format) {
        String inputPattern = date_format;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(dt_string);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        int day = currentCalendar.get(Calendar.DAY_OF_YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        int targetDay = targetCalendar.get(Calendar.DAY_OF_YEAR);
        return week == targetWeek && year == targetYear && day == targetDay;
    }

    public static String getMyPrettyDate(String dateString) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(utc_format);
            Date date = null;
            try {
                date = inputFormat.parse(dateString);
            } catch (ParseException e) {
                return e.getMessage();
            }
            Calendar currentCalendar = Calendar.getInstance();

            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setTime(date);

            if ((targetCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR))) {

                if ((targetCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH))) {

                    if (targetCalendar.get(Calendar.DATE) - currentCalendar.get(Calendar.DATE) == 1) {
                        return "Tomorrow at " + format_date(utc_format, format_hh_mm_a, dateString);

                    } else if (currentCalendar.get(Calendar.DATE) == targetCalendar.get(Calendar.DATE)) {
                        return "Today at " + format_date(utc_format, format_hh_mm_a, dateString);

                    } else if (currentCalendar.get(Calendar.DATE) - targetCalendar.get(Calendar.DATE) == 1) {
                        return "Yesterday at, " + format_date(utc_format, format_hh_mm_a, dateString);

                    } else {
                        //here return like "May 31, 12:00"
                        return format_date(utc_format, "EEEE", dateString) + " at " + format_date(utc_format, format_hh_mm_a, dateString);
                    }

                } else {
                    //here return like "May 31, 12:00"
                    return format_date(utc_format, "MMMM d", dateString) + " at " + format_date(utc_format, format_hh_mm_a, dateString);
                }

            } else {
                //here return like "May 31 2010, 12:00" - it's a different year we need to show it
                return format_date(utc_format, "MMMM dd yyyy", dateString) + " at " + format_date(utc_format, format_hh_mm_a, dateString);
            }

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static String getTimeDifference(String startDate, String endDate) {
        Date startdate = convert_date_to_dd_mm_yyyy(startDate);
        Date enddate = convert_date_to_dd_mm_yyyy(endDate);

        long different = enddate.getTime() - startdate.getTime();
        boolean isOverdue = different < 0;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;

        String diff = "";
        if (isOverdue) {
            diff = "Overdue From ";
        } else {
            diff = "Due In ";
        }
        diff = elapsedDays != 0 ? diff + elapsedDays + " Days " : diff;
        diff = elapsedHours != 0 ? diff + elapsedHours + " Hrs " : diff;
        diff = elapsedMinutes != 0 ? diff + elapsedMinutes + " Mins" : diff;

        diff = diff.replaceAll("-", "");

        return diff;
    }

    public static String convert_date_to_day_short_name_and_time(String dt_string) {
        String inputPattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(dt_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE hh:mm aa");
        return outputFormat.format(date);
    }

    public static boolean is_valid_time(String selected_time) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd HH:mm");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-M-dd HH:mm");
        String currentDateString = df.format(c.getTime());

        try {
            Date currentDate = df.parse(currentDateString);
            Date selectedDate = df2.parse(selected_time);
            return currentDate.compareTo(selectedDate) < 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getTodaysStartTime() {
        Calendar calendar = Calendar.getInstance();
        long date_ship_millis = calendar.getTimeInMillis();
        Date date = calendar.getTime();
        String formatted_from_date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "T" + "00:00:00.000Z";
        String todays_start_time = convert_local_to_server_format(formatted_from_date);
        return todays_start_time;
    }

    public static String getTodaysToTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date next_date = calendar.getTime();
        String formatted_to_date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "T" + "00:00:00.000Z";
        String todays_end_time = convert_local_to_server_format(formatted_to_date);
        return todays_end_time;
    }

    public static String convert_local_to_GMT(String datestring, String inputPattern, String outputPattern) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        outputFormat.setTimeZone(tz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;
    }

    public static String convert_GMT_to_local(String datestring) {
        String outputPattern = "dd MMM yyyy";

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone inputTz = TimeZone.getTimeZone("GMT");

        TimeZone outputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;
    }

    public static String convert_GMT_to_local_with_format(String inputPattern, String outputPattern, String datestring) {
        String formated_date_str = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            TimeZone inputTz = TimeZone.getTimeZone("GMT");
            TimeZone outputTz = TimeZone.getDefault();

            inputFormat.setTimeZone(inputTz);
            outputFormat.setTimeZone(outputTz);

            Date date = null;

            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);

        } catch (ParseException e) {
            formated_date_str = e.getMessage();
        }
        return formated_date_str;
    }

    public static String convert_GMT_to_local_with_time(String datestring) {
        String outputPattern = "dd MMM yyyy HH:mm:ss";

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone inputTz = TimeZone.getTimeZone("GMT");

        TimeZone outputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;
    }

    public static String convert_GMT_to_local_without_time(String datestring) {
        String outputPattern = "dd MMM yyyy";

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone inputTz = TimeZone.getTimeZone("GMT");

        TimeZone outputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;
    }

    public static String convert_local_to_server_format(String datestring) {
        String outputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone outputTz = TimeZone.getTimeZone("UTC/Greenwich");

        TimeZone inputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;


    }

    public static String convert_server_to_local_format(String datestring, String outputPattern) {

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone inputTz = TimeZone.getTimeZone("GMT");

        TimeZone outputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;


    }

    public static Date convert_date_to_dd_mm_yyyy(String datestring) {
        String outputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        TimeZone inputTz = TimeZone.getDefault();

        TimeZone outputTz = TimeZone.getDefault();

        inputFormat.setTimeZone(inputTz);
        outputFormat.setTimeZone(outputTz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;


    }

    @SuppressLint("SimpleDateFormat")
    public static String getFormatedCurrentDateTime(String expected_format) {
        return new SimpleDateFormat(expected_format).format(Calendar.getInstance().getTime());
    }

    public static String milliseconds_to_gmt_date(long mills) {
        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone inputTz = TimeZone.getTimeZone("GMT");
        formatter.setTimeZone(inputTz);
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public static boolean is_time_in_same_day(String date_string) {


        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        TimeZone inputTz = TimeZone.getTimeZone("GMT");
        inputFormat.setTimeZone(inputTz);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");


        Date date = null;
        Long timeinmills = 0L;

        try {
            date = inputFormat.parse(date_string);

            Calendar time = Calendar.getInstance(inputTz);

            timeinmills = date.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.i(TAG, "" + DateUtils.isToday(timeinmills));

        return DateUtils.isToday(timeinmills);
    }

    public static boolean is_gmt_in_previous_day(String start_time) {

        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        TimeZone inputTz = TimeZone.getTimeZone("GMT");
        inputFormat.setTimeZone(inputTz);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");


        Date date = null;
        float total_diff = 0.0f;

        try {
            date = inputFormat.parse(start_time);

            Calendar time = Calendar.getInstance(inputTz);
            time.setTimeInMillis(date.getTime());
            int selected_hour = time.get(Calendar.HOUR_OF_DAY);
            int selected_minute = time.get(Calendar.MINUTE);
            int selected_second = time.get(Calendar.SECOND);


            int totalSecs = TimeZone.getDefault().getOffset(Calendar.ZONE_OFFSET) / 1000;
            int offset_hour = totalSecs / 3600;
            int offset_minute = (totalSecs % 3600) / 60;
            int offset_second = totalSecs % 60;

            int diffHours = selected_hour + offset_hour;
            float diffmin = selected_minute / 60.0f + offset_minute / 60.0f;
            total_diff = diffHours + diffmin;


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return total_diff > 24;
    }

}

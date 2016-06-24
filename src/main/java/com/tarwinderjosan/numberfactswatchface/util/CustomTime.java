package com.tarwinderjosan.numberfactswatchface.util;

import java.util.Calendar;

/**
 * Time handling based on the Calendar class.
 */
public class CustomTime {


    private static CustomTime instance;
    private static Calendar calendar;

    private String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
    "NOV", "DEC"};
    private int mCurrentMinute, mCurrentHour;

    public static CustomTime getInstance() {
        if(instance == null) {
            instance = new CustomTime();
        }
        return instance;
    }

    public boolean isChanged() {
        if(Calendar.getInstance().get(Calendar.MINUTE) !=
        calendar.get(Calendar.MINUTE)) {
            return true;
        }
        return false;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setToNow() {
        calendar = Calendar.getInstance();
        mCurrentMinute = calendar.get(Calendar.MINUTE);
        mCurrentHour = calendar.get(Calendar.HOUR);
    }

    public int getMinute() {
        return mCurrentMinute;
    }
    public String getFormattedMinute() {
        String minute = String.format("%02d", mCurrentMinute);
        return minute;
    }

    /**
     * Return the hour with a minimum field width of two opposed to just one.
     */
    public String getFormattedHour() {
        if(mCurrentHour == 0) {
            mCurrentHour = 12;
        }
        String hour = String.format("%02d", mCurrentHour);
        return hour;
    }

    /**
     * Return the minute with a minimum field width of two opposed to just one.
     */
    public int getHour() {
        // Noon and midnight are represented by zero, return twelve because of this
        if(mCurrentHour == 0) { return 12; } return mCurrentHour;
    }

    /**
     * Return a date formatted as a String.
     * Format: dd mm yyyy
     */
    public String getFormattedDate() {
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = months[calendar.get(Calendar.MONTH)];
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        return day + " " + month + " " + year + "";
    }
}

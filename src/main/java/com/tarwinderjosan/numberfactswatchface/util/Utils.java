package com.tarwinderjosan.numberfactswatchface.util;

import android.content.Context;

import com.tarwinderjosan.numberfactswatchface.facts.FactManager;

import java.util.Calendar;

/**
 * Useful utility methods and constants
 */
public class Utils {
    // Constants
    public static Context CONTEXT = null;
    public static final String GENERAL_TABLE = "general";
    public static final String DB_NAME = "facts.db";
    public static final int DB_VERSION = 1;
    public static final int TIME_PADDING_TOP_ROUND = 15;
    public static final int FACT_PADDING_TOP_SQUARE = 25;
    public static final int FACT_PADDING_TOP_ROUND = 5;

    public static int width(Context c) {
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    public static int height(Context c) {
        return c.getResources().getDisplayMetrics().heightPixels;
    }

    public static float density(Context c) {
        return c.getResources().getDisplayMetrics().density;
    }

    public static String nameAs(Choices choice) {
        return choice.name();
    }
}

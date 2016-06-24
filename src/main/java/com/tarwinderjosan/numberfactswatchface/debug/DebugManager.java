package com.tarwinderjosan.numberfactswatchface.debug;

import android.util.Log;

import com.tarwinderjosan.numberfactswatchface.BuildConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Useul debugging methods
 */
public class DebugManager {

    private static long startTime, endTime;

    /**
     * Print a String with the tag "TAG", if the build is not release.
     * @param x String to print
     */
    public static void print(String x) {

        if(BuildConfig.DEBUG)
            Log.d("TAG", x);
    }

    /**
     * Start time for measuring efficiency
     */
    public static void startTime() {
        startTime = System.nanoTime();
    }

    /**
     * End time for measuring efficiency
     */
    public static void endTime() {
        endTime = System.nanoTime();
    }

    /**
     * Get the time elapsed, taken into factor the two efficiencies.
     * @param timeUnit The value to convert to
     * @return The value in the TimeUnit form.
     */
    public static long getElapsed(TimeUnit timeUnit) {
        if(timeUnit.equals(TimeUnit.MILLISECONDS)) {
            return TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        }
        return -1;
    }
}

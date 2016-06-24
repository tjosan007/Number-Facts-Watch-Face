package com.tarwinderjosan.numberfactswatchface.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Encapsulates the app preference utilities.
 * Date: Sept 26, 2015
 */
public class Prefs {

    private static Prefs p;

    private SharedPreferences userPrefs;
    private Context c;

    private Prefs(Context c) {
        userPrefs = c.getSharedPreferences("wearprefs", Context.MODE_PRIVATE);
        this.c = c;
    }

    public static Prefs init(Context c) {
        if(p == null) {
            p = new Prefs(c);
        }
        return p;
    }



    public SharedPreferences get() {
        return userPrefs;
    }


    public boolean putString(String key, String val) {
        SharedPreferences.Editor e = userPrefs.edit();
        e.putString(key, val);
        return e.commit();
    }

    public String getString(String key) {
        return userPrefs.getString(key, "");
    }

    /**
     * Retrieve a boolean from the preferences.
     * @param key The key associated with the boolean.
     * @return The value associated with the key, false or true, true default.
     */
    public boolean getBoolean(String key) {
        return userPrefs.getBoolean(key, false);
    }

    public boolean putBoolean(String key, boolean val) {
        SharedPreferences.Editor e = userPrefs.edit();
        e.putBoolean(key, val);
        return e.commit();
    }
}

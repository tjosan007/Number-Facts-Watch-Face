package com.tarwinderjosan.numberfactswatchface.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Manages preferences in JSON format.
 *
 * When the user modifies preferences on the device
 * and submits to push changes, a JSON version of all the preference
 * is sent back to the watch. This helps to keep both devices in sync in terms
 * of preferences. This class internally helps decide if and which preference values
 * have been modified.
 */
public class JSONPreferences {
    /**
     * Converts a SharedPreferences object into a JSON string
     *
     * @param mSP SharedPreferences object
     * @return JSON string version of the preferences
     */
    public static String preferencesAsString(SharedPreferences mSP) throws JSONException {
        Map<String, ?> allEntries = mSP.getAll();
        // Main JSON object
        JSONObject parent = new JSONObject();
        JSONObject[] objects = new JSONObject[allEntries.size()];
        int counter = 0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            // Get key/values of the preferences
            String key = entry.getKey();
            String value = entry.getValue().toString();
            JSONObject newObject = new JSONObject();
            newObject.put(key, value);
            objects[counter] = newObject;
            counter++;
        }
        for (int i = 0; i < objects.length; i++) {
            parent.put("" + i, objects[i]);
        }
        return parent.toString();
    }

    /**
     * Set the preferences to the specified JSON String
     * which contains key/value pairs to override
     * Used when JSON string is received on the watch.
     *
     * @param format
     * @return true if successful, false otherwise
     */
    public static boolean setPreferencesFromJSONString(String data, Context c,
                                                       String[] format) throws JSONException {



        return true;
    }

}
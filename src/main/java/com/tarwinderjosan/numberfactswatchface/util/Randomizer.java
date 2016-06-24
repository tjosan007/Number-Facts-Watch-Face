package com.tarwinderjosan.numberfactswatchface.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tarwinderjosan.numberfactswatchface.facts.FactManager;
import java.util.ArrayList;
import java.util.List;

public class Randomizer {

    private SQLiteDatabase db;

    private static char[] prefix = new char[26];
    static {
        int c = 0;
        for(int i = 97; i<=122;i++) {
            char letter = (char)i;
            prefix[c] = letter;
            c++;
        }
    }

    // Fact char relating to column
    // 'a' for column 1, 'b' for column 2, etc
    private static char mFactChar;

    public static char getFactChar() {
        return mFactChar;
    }

    /**
     * Construct a Randomizer.
     * @param readableDB A readable database
     */
    public Randomizer(SQLiteDatabase readableDB) {
        db = readableDB;
    }

    public String getRandomFact(int minute, Choices choice) {
        // Utils.nameAs(Choices choice) returns the lowercase name of the enum
        // All sub-sheets will have their column numbers labeled as the subsheet-name1 - 60,
        // In the SQLite database
        // E.g, general1, general2, general2
        // science1, science2, science3

        Cursor factsInCol = db.rawQuery("SELECT " + Utils.nameAs(choice) + minute + " FROM " + Utils.nameAs(choice), null);
        // Get a list of all usable facts in the current column
        List<String> usableFacts = getNumUsableFacts(factsInCol);
        for(String x : usableFacts) {
            Log.d("TAG", "Usable fact in minute " + minute + "is " + x);
        }

        // The number of the fact to use
        // DEMO Currently only use one of the first three facts in each column
        // because the the first three facts in each column have their images done
        // and are consistent
        int factNumber = (int)(3 * Math.random());
        //int factNumber = (int)(usableFacts.size() * Math.random());
        // First fact, 'a', second fact, 'b'
        mFactChar = prefix[factNumber];


        return usableFacts.get(factNumber);
    }

    /**
     * Get the usable facts, not including 'N/A', in a List of Strings implementation.
     * @param factCursor The cursor containing column data
     * @return The usable facts in a List.
     */
    private List<String> getNumUsableFacts(Cursor factCursor) {
        // Gets number of facts not including N/A in the current column.
        List<String> list = new ArrayList<>();
        for(factCursor.moveToFirst(); !factCursor.isAfterLast(); factCursor.moveToNext()) {
            String s = factCursor.getString(0);
            if(!(s.equals("N/A"))) {
                list.add(s);
            } else {
                // The rest will be N/A once it has come across it once
                // Once it starts returning N/A, just quit (no more usable facts after first N/A)
                factCursor.moveToPosition(factCursor.getCount() + 1);
                return list;
            }
        }
        return list;
    }
}

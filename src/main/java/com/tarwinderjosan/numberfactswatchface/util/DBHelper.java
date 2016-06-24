package com.tarwinderjosan.numberfactswatchface.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.tarwinderjosan.numberfactswatchface.facts.Spreadsheet;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DBHelper  {

    /**
     * Storing facts into the database.
     * @param db The new database to store the facts into.
     * @param tableName The name of the table to create
     * @param tempStorage Where to retrieve the facts from
     * @param sheet Reference to Spreadsheet
     */
    public static void storeInDatabase(SQLiteDatabase db, String tableName,
            List<List<String>> tempStorage, Spreadsheet sheet) {
        try {
            db.beginTransaction();

            // Create table
            db.execSQL("CREATE TABLE " + tableName + "( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ");");
            // Add 60 columns to the table (labelled 1 - 60)
            // Column naming scheme
            for(int i = 1; i<=60;i++) {
                db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + tableName + i + " TEXT");
            }

            ContentValues cv = new ContentValues();

            // 60 columns to fill
            // Loop through each index of each the individual ArrayLists
            // First index 0, then index 1, then index 2
            // Index 0 represents first row, index 1 represents second row

            // Each column has sheet.getMax() amount of data in it (including N/A empty data)

            for(int i = 0; i < sheet.getMax(); i++) {
                ArrayList<String> currentRow = new ArrayList<>();
                // Largest containing size for a single column becomes the size of each ArrayList
                for(int j= 0; j < 60; j++) {
                    // 60 columns to fill
                    // String currentVal = tempStorage.get(j).get(i);
                    currentRow.add(tempStorage.get(j).get(i));
                    cv.put(tableName+(j + 1), currentRow.get(j));
                }
                db.insert(tableName, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            sheet.reset();
        }
    }

}


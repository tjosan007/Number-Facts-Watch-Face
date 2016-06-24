package com.tarwinderjosan.numberfactswatchface.facts;

import android.database.sqlite.SQLiteDatabase;

/**
 * Manages retrieval and loading of a single spreadsheet.
 * Embeds the Apache POI Framework to assist in reading the excel sheet.
 */
public class SpreadsheetManager {
    private static Spreadsheet mSS;
    private static SpreadsheetManager mSSManager;
    private static String mFileName;

    private SpreadsheetManager(String mSheetName) {
        this.mFileName = mSheetName;
    }

    public static SpreadsheetManager getInstance(String fileName) {
        mSSManager = new SpreadsheetManager(fileName);
        mSS = new Spreadsheet(fileName);
        return mSSManager;
    }

    public Spreadsheet getSpreadsheet() {
        return mSS;
    }

    /**
     * Load a new sub-sheet from the Excel sheet and store in a database.
     * @param db The database to store the facts into
     * @param tableName The name of the table to create
     * @param sheetName The name of the sheet being loaded
     */
    public void loadNew(SQLiteDatabase db, String tableName,
                               String sheetName) {
        mSS.setSheetName(sheetName);
        mSS.load();
        mSS.storeInDatabase(db, tableName);
    }
}

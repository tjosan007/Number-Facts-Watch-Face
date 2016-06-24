package com.tarwinderjosan.numberfactswatchface.facts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tarwinderjosan.numberfactswatchface.util.Choices;
import com.tarwinderjosan.numberfactswatchface.util.Randomizer;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

import java.util.Random;

/**
 * Manages the loading and retrieval of facts.
 */
public class FactManager extends SQLiteOpenHelper {

    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    private SpreadsheetManager mSManager;
    private Randomizer randomizer;


    private static FactManager onlyInstance;

    public static FactManager getInstance(Context context) {
        if(onlyInstance == null)
            onlyInstance = new FactManager(context);
        return onlyInstance;
    }

    private FactManager(Context context) {
        super(context, Utils.DB_NAME, null, Utils.DB_VERSION);
        readableDB = this.getReadableDatabase();
        writableDB = this.getWritableDatabase();
        randomizer = new Randomizer(readableDB);
    }

    /**
     * Return a random fact located from the three columns, from the specified minute and from the General
     * fact sheet, given the minute and the user choice of facts (default general)
     * @return String containing a random fact.
     * @param choice The database choice to query
     */
    public String getRandomFact(int minute, Choices choice) {
        if (minute == 0)
            minute = 60;

        String fact = randomizer.getRandomFact(minute, choice);
        return fact;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // User gets 60 free facts and has to buy the rest
        mSManager = SpreadsheetManager.getInstance("First.xls");
        mSManager.loadNew(db, Utils.GENERAL_TABLE, "GENERAL");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

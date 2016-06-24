package com.tarwinderjosan.numberfactswatchface.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.tarwinderjosan.numberfactswatchface.R;
import com.tarwinderjosan.numberfactswatchface.facts.FactManager;
import com.tarwinderjosan.numberfactswatchface.text.Balloon;
import com.tarwinderjosan.numberfactswatchface.text.Paints;
import com.tarwinderjosan.numberfactswatchface.text.Placement;
import com.tarwinderjosan.numberfactswatchface.text.Coordinates;
import com.tarwinderjosan.numberfactswatchface.text.FactTokenizer;
import com.tarwinderjosan.numberfactswatchface.text.HalfBoundary;
import com.tarwinderjosan.numberfactswatchface.util.Choices;
import com.tarwinderjosan.numberfactswatchface.util.CustomTime;
import com.tarwinderjosan.numberfactswatchface.util.Prefs;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

public class Painter {
    private Context mContext;
    private FactManager factManager;
    private CustomTime time;
    private HalfBoundary hbBottom;
    private Coordinates c;
    private FactTokenizer factTokenizer;
    private Paints paints;
    private Balloon balloon;

    private String mCurrentFact;
    private String initMessage = "Please open the companion app.";

    private boolean mIsRound;
    private Prefs prefs;
    // If the user has configured by opening the companion app.
    private boolean isInitialized;

    public Painter(Context context, boolean isRound) {
        mContext = context;
        mIsRound = isRound;

        paints = Paints.get();
        prefs = Prefs.init(context);

        // True if first time user is running
       // isInitialized = prefs.getBoolean("inited");

        isInitialized = false;
        factManager = FactManager.getInstance(mContext);
        time = CustomTime.getInstance();
        time.setToNow();
        // Width, height, radius, subsections, height offset for each subsection, offset from the center line
        c = Coordinates.getInstance();
        Placement.init(isRound);
        HalfBoundary.initialize();

        balloon = new Balloon(paints);
        if(!isInitialized) {
            // Set the "fact" to a message
            // Ok, not really a fact, but FactTokenizer. can tokenizer anything to be displayed
            // in the bottom half of the screen. It should be abstract.
            factTokenizer = new FactTokenizer(paints.getFactPaint(), initMessage);
        } else {
            mCurrentFact = factManager.getRandomFact(time.getMinute(), Choices.GENERAL);
            // Must be called after Paints class
            factTokenizer = new FactTokenizer(paints.getFactPaint(), mCurrentFact);
        }

    }
    public void paint(Canvas canvas, boolean isRound, boolean isAmbientMode, Rect bounds) {
        if(isAmbientMode) {
            canvas.drawColor(paints.getBackgroundAmbient());
            drawAmbient(canvas, isRound, bounds);
        } else {
            canvas.drawColor(paints.getBackgroundAmbient());
            drawInteractive(canvas, isRound, bounds);

        }
    }

    /**
     * Called each minute in overridden onTimeTick() method
     * Updates time, balloon color, current fact, and sets fact tokenizer
     */
    public void update() {
        time.setToNow();
        paints.setBalloonColor();
        if(isInitialized) {
            mCurrentFact = factManager.getRandomFact(time.getMinute(), Choices.GENERAL);
            factTokenizer.setFact(mCurrentFact);
        }
    }

    private void drawAmbient(Canvas canvas, boolean isRound, Rect bounds) {

        // Format to width of 2 characters
        String minute = time.getFormattedMinute();
        String hour = time.getFormattedHour();
        String date = time.getFormattedDate();

        if(isInitialized) {
            Placement.drawFacts(factTokenizer.getSentences(), c, canvas, paints.getFactPaint());
            Placement.drawDate(date, canvas, bounds, paints.getDatePaint());
            Placement.drawHour(hour, canvas, bounds, paints.getHourPaint());
            Placement.drawMinute(minute, canvas, bounds, paints.getMinutePaint());
            Placement.drawPolygonAmbient(balloon, canvas, bounds, minute, paints.getMinutePaint());
        } else {
            // User has not opened companion app yet!
            Placement.drawSolidBackground(minute, canvas);
            Placement.drawDate(time.getFormattedDate(), canvas, bounds, paints.getDatePaint());
            Placement.drawHour(hour, canvas, bounds, paints.getHourPaint());
            Placement.drawPolygonAmbient(balloon, canvas, bounds, minute, paints.getMinutePaint());
            Placement.drawMinute(minute, canvas, bounds, paints.getMinutePaint());
            Placement.drawMessage(factTokenizer.getSentences(),c,canvas,paints.getFactPaint());

        }
    }

    private void drawInteractive(Canvas canvas, boolean isRound, Rect bounds) {

        // Format to width of 2 characters
        // 5, becomes 05
        String minute = time.getFormattedMinute();
        String hour = time.getFormattedHour();
        String date = time.getFormattedDate();

        if(isInitialized) {
            Placement.drawImage(paints, canvas);
            Placement.drawFacts(factTokenizer.getSentences(), c, canvas, paints.getFactPaint());
            Placement.drawDate(date, canvas, bounds, paints.getDatePaint());
            Placement.drawHour(hour, canvas, bounds, paints.getHourPaint());
            Placement.drawPolygonInteractive(balloon, canvas, bounds, minute, paints.getMinutePaint());
            Placement.drawMinute(minute, canvas, bounds, paints.getMinutePaint());
        } else {
            // User has not opened companion app yet!
            Placement.drawSolidBackground(minute, canvas);
            Placement.drawDate(time.getFormattedDate(), canvas, bounds, paints.getDatePaint());
            Placement.drawHour(hour, canvas, bounds, paints.getHourPaint());
            Placement.drawPolygonInteractive(balloon, canvas, bounds, minute, paints.getMinutePaint());
            Placement.drawMinute(minute, canvas, bounds, paints.getMinutePaint());
            Placement.drawMessage(factTokenizer.getSentences(),c,canvas,paints.getFactPaint());
        }
    }

    public CustomTime getTime() {return time;}

    public Paints getPaints() {
        return paints;
    }

    public boolean isDeviceRound() {
        return mIsRound;
    }
}

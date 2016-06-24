package com.tarwinderjosan.numberfactswatchface.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.tarwinderjosan.numberfactswatchface.image.ImageFactory;
import com.tarwinderjosan.numberfactswatchface.util.CustomTime;
import com.tarwinderjosan.numberfactswatchface.util.Randomizer;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

public class Paints {
    private Paint mHourPaint, mFactPaint, mMinutePaint, mDatePaint, mBoxPaint, mTrianglePaint,mTrianglePaintAmbient,
    mBoxPaintAmbient, mLineAmbient, mImagePaint;

    private int mHourColor, mMinuteColor, mFactColor, mBackgroundAmbient, mForegroundAmbient,
    mDateColor, mBalloonColor;

    private Context mContext;
    private Typeface mMainFont;
    private boolean isInAmbientMode;
    private static Paints paint;

    private CustomTime theTime;

    public static Paints get() {
        if(paint == null) {
            paint = new Paints();
        }
        return paint;
    }

    private Paints() {
        mContext = Utils.CONTEXT;
        theTime = CustomTime.getInstance();
        int minuteSize = getTextSize(45);
        int hourSize = getTextSize(20);
        int factSize = getTextSize(15);
        int dateSize = getTextSize(10);

        initFonts();
        initColors();

        mMinutePaint = new Paint();
        mMinutePaint.setTextSize(minuteSize);
        mMinutePaint.setAntiAlias(true);
        mMinutePaint.setColor(mMinuteColor);
        mMinutePaint.setTypeface(mMainFont);
        mMinutePaint.setStyle(Paint.Style.FILL);
        mMinutePaint.setTextAlign(Paint.Align.CENTER);

        mHourPaint = new Paint();
        mHourPaint.setAntiAlias(true);
        mHourPaint.setTextSize(hourSize);
        mHourPaint.setColor(mHourColor);
        mHourPaint.setTypeface(mMainFont);
        mHourPaint.setStyle(Paint.Style.FILL);
        mHourPaint.setTextAlign(Paint.Align.CENTER);

        mFactPaint = new Paint();
        mFactPaint.setAntiAlias(true);
        mFactPaint.setTextSize(factSize);
        mFactPaint.setColor(mFactColor);
        mFactPaint.setTypeface(mMainFont);
        mFactPaint.setStyle(Paint.Style.FILL);

        mDatePaint = new Paint();
        mDatePaint.setAntiAlias(true);
        mDatePaint.setTextSize(dateSize);
        mDatePaint.setColor(mDateColor);
        mDatePaint.setTypeface(mMainFont);
        mDatePaint.setStyle(Paint.Style.FILL);
        mDatePaint.setTextAlign(Paint.Align.CENTER);

        mBoxPaint = new Paint();
        mBoxPaint.setAntiAlias(true);
        mBoxPaint.setColor(mBalloonColor);
        // Fills the inside in too, for interactive mode
        mBoxPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBoxPaint.setStrokeWidth(1f);
        mBoxPaint.setTextAlign(Paint.Align.CENTER);
        mBoxPaint.setStrokeCap(Paint.Cap.ROUND);
        mBoxPaint.setStrokeJoin(Paint.Join.ROUND);

        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        // Note: Same outline color in ambient or background in interactive as the balloon
        mTrianglePaint.setColor(mBalloonColor);
        mTrianglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTrianglePaint.setStrokeWidth(1f);
        mTrianglePaint.setTextAlign(Paint.Align.CENTER);

        // Ambient paints

        mTrianglePaintAmbient = new Paint();
        mTrianglePaintAmbient.setColor(mForegroundAmbient);
        mTrianglePaintAmbient.setStyle(Paint.Style.STROKE);
        mTrianglePaintAmbient.setAntiAlias(false);
        mTrianglePaintAmbient.setStrokeWidth(1f);

        mBoxPaintAmbient = new Paint();
        mBoxPaintAmbient.setAntiAlias(false);
        mBoxPaintAmbient.setColor(mForegroundAmbient);
        mBoxPaintAmbient.setStyle(Paint.Style.STROKE);
        mBoxPaintAmbient.setStrokeWidth(1f);
        mBoxPaintAmbient.setTextAlign(Paint.Align.CENTER);
        mBoxPaintAmbient.setStrokeCap(Paint.Cap.ROUND);
        mBoxPaintAmbient.setStrokeJoin(Paint.Join.ROUND);

        mLineAmbient = new Paint();
        mLineAmbient.setColor(mBackgroundAmbient);
        mLineAmbient.setStyle(Paint.Style.STROKE);
        mLineAmbient.setAntiAlias(false);
        mLineAmbient.setStrokeWidth(1f);

        mImagePaint = new Paint();


     }

    private void initColors() {
        mBackgroundAmbient = Color.BLACK;
        mForegroundAmbient = Color.WHITE;

        // Text always white, only balloon background changes color
        mMinuteColor = Color.WHITE;
        mHourColor = Color.WHITE;
        mFactColor = Color.WHITE;
        mDateColor = Color.WHITE;

        mBalloonColor = ColorFactory.getBalloonColor(theTime.getMinute());

    }

    private void initFonts() {
        /* See LICENSE.txt */
        mMainFont = Typeface.createFromAsset(mContext.getAssets(), "Viga-Regular.otf");
    }

    private int getTextSize(int baseSize) {
        return (int) (mContext.getResources().getDisplayMetrics().density * baseSize);
    }

    public void setIsInAmbientMode(boolean isInAmbientMode) {
        this.isInAmbientMode = isInAmbientMode;
        if(isInAmbientMode) {
            mHourPaint.setAntiAlias(false);
            mMinutePaint.setAntiAlias(false);
            mFactPaint.setAntiAlias(false);

        } else {
            mHourPaint.setAntiAlias(true);
            mMinutePaint.setAntiAlias(true);
            mFactPaint.setAntiAlias(true);

            // Set triangle and box colors to the interactive colors
            // TO-DO
        }
    }



    public Paint getFactPaint() {
        return mFactPaint;
    }
    public Paint getHourPaint() {return mHourPaint; }
    public Paint getMinutePaint() {return mMinutePaint; }
    public int getBackgroundAmbient() {
        return mBackgroundAmbient;
    }

    public Paint getDatePaint() {
        return mDatePaint;
    }

    public Paint getBoxPaint() {
        return mBoxPaint;
    }
    public Paint getTrianglePaint() {
        return mTrianglePaint;
    }
    public Paint getBoxPaintAmbient() {
        return mBoxPaintAmbient;
    }
    public Paint getTrianglePaintAmbient() {
        return mTrianglePaintAmbient;
    }
    public Paint getLineAmbient() {return mLineAmbient;}
    public void setBalloonColor() {
        mBalloonColor = ColorFactory.getBalloonColor(theTime.getMinute());
        mBoxPaint.setColor(mBalloonColor);
        mTrianglePaint.setColor(mBalloonColor);
    }

    public Bitmap getImage() {
        return ImageFactory.getCurrentImage(theTime.getFormattedMinute(), Randomizer.getFactChar());
    }

    public Paint getImagePaint() {
        return mImagePaint;
    }
}

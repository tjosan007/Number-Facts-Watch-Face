package com.tarwinderjosan.numberfactswatchface.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.tarwinderjosan.numberfactswatchface.util.Utils;

/**
 * Manages the boundary rectangles of the class and the main boundary rectangle parent.
 * The boundary rectangles in a device help manage alignment.
 * Square device and circular devices have different methods of
 * creating the boundary to house the text.
 *
 * This class also manages the bound creation for square and circular devices
 * Date: May 22, 2015
 */
public class Boundary {

    private boolean mIsRound;
    private Context mContext;
    private Rect mBounds;

    private Rect[] mSubSections;

    public Boundary(Context context, boolean isRound, int divisions) {
        mIsRound = isRound;
        mContext = context;

        mBounds = setBounds(isRound);
        setCanvasRects(divisions, mBounds);
    }

    /**
     * Get the Canvas sub rects
     * @param divisions # of divisions to be made
     * @param rect The original parent Boundary Rect
     * @return Rect array containing the subsection rects, in descending order
     */
    private void setCanvasRects(int divisions, Rect rect) {
        // Have to -1 for some reason for it to perfectly fit
        int deviceHeight = rect.height() - 1;
        int deviceWidth = rect.width() - 1;

        Rect[] subSections = new Rect[divisions];
        for(int i = 0; i < divisions; i++) {

        }
    }

    /**
     * Draws the subsection rectangles.
     * Used solely for debugging and viewing the subsection Rects.
     * @param canvas The canvas to draw the sub sections on
     */
    public void drawSubSections(Canvas canvas) {
        Paint r1 = new Paint();
        r1.setColor(Color.RED);
        r1.setStyle(Paint.Style.STROKE);
        r1.setStrokeWidth(1);
        for(int i = 0; i < mSubSections.length; i++) {
            canvas.drawRect(mSubSections[i], r1);
            canvas.drawCircle(mSubSections[i].centerX(), mSubSections[i].centerY(), 1, r1);
        }
    }

    public void drawBounds(Canvas canvas) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);
        p.setStrokeWidth(1);
        canvas.drawRect(mBounds, p);
    }


    public void drawRectText(String text, Canvas canvas, int rect, Paint textPaint) {
        canvas.drawText(text, mSubSections[rect].left, mSubSections[rect].bottom, textPaint);

    }
    public void drawRectText(String text, Canvas canvas, int rect, Paint textPaint, float xOffset) {
        canvas.drawText(text, mSubSections[rect].left + xOffset, mSubSections[rect].bottom, textPaint);
    }

    public void drawRectText(String text, Canvas canvas, int rect, Paint textPaint, float xOffset, float yOffset) {
        canvas.drawText(text, mSubSections[rect].left + xOffset, mSubSections[rect].bottom + yOffset, textPaint);
    }

    private Rect setBounds(boolean isRound) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        if (isRound) {
            if(metrics.heightPixels == 290) {
                metrics.heightPixels = 320;
            }
            float radius = metrics.heightPixels / 2;
            double lengthOppYQuad2 = metrics.heightPixels / 2 - radius * Math.sin(Math.PI / 4);
            double lengthOppYQuad3 = metrics.heightPixels / 2 + radius * Math.sin(Math.PI / 4);
            double lengthOppXQuad1 = metrics.widthPixels / 2 + (radius * Math.sin(Math.PI / 4));
            double lengthOppXQuad2 = metrics.heightPixels / 2 - (radius * Math.sin(Math.PI / 4));
            Rect rect = new Rect((int) lengthOppXQuad2, (int) lengthOppYQuad2, (int) lengthOppXQuad1, (int) lengthOppYQuad3);
            return rect;
        } else {
            Rect rect = new Rect(0, 0, metrics.widthPixels, metrics.heightPixels);
            return rect;
        }
    }

    public Rect getBounds() {return mBounds;}
    public Rect[] getSubSections() {
        return mSubSections;
    }
    public float getBoundaryWidth() {
        return mBounds.width();
    }
    public float getBoundaryLeft() {return mBounds.left;}
}

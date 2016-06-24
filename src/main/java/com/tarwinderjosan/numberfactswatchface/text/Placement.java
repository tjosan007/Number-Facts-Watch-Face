package com.tarwinderjosan.numberfactswatchface.text;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

import java.util.ArrayList;

/**
 * Draws text.
 */
public class Placement {

    private static float dateX, dateY;
    private static float hourX, hourY;
    private static float minuteX, minuteY;

    private static boolean mIsRound;

    private static final float DENSITY = Utils.CONTEXT.getResources().getDisplayMetrics().density;

    public static void init(boolean isRound) {
        // Draw placements differently if it is round
        mIsRound = isRound;

        DisplayMetrics main = Utils.CONTEXT.getResources().getDisplayMetrics();
        dateX = main.widthPixels / 2;
        dateY = DENSITY * 35;

        hourX = main.widthPixels / 2;
        hourY = DENSITY * 60;

        minuteX = main.widthPixels / 2;
        minuteY = DENSITY * 105;
    }


    public static void drawImage(Paints paint, Canvas canvas) {
        canvas.drawBitmap(paint.getImage(), 0, 0, paint.getImagePaint());
    }

    public static void drawDate(String string, Canvas canvas, Rect rect, Paint paint) {
        canvas.drawText(string, dateX, dateY, paint);
    }

    public static void drawHour(String hour, Canvas canvas, Rect rect, Paint paint) {
        canvas.drawText(""+hour, hourX, hourY, paint);
    }

    public static void drawMinute(String minute, Canvas canvas, Rect rect, Paint paint) {
        canvas.drawText(minute+"", minuteX, minuteY, paint);
    }

    public static void drawPolygonAmbient(Balloon b, Canvas canvas, Rect rect, String minute,
                                          Paint minutePaint) {
        b.drawAmbient(canvas, rect, minute, minutePaint);
    }

    public static void drawPolygonInteractive(Balloon b, Canvas canvas, Rect rect, String minute,
                                              Paint minutePaint) {
        b.drawInteractive(canvas, rect, minute, minutePaint);
    }

    public static void drawFacts(ArrayList<String> sentences, Coordinates c, Canvas canvas, Paint paint) {
        for(int i = 0; i < sentences.size(); i++) {
            canvas.drawText(sentences.get(i), c.getStartXOffset(i)[0], c.getStartXOffset(i)[1],
                    paint);

        }
    }

    public static float getDateX() {
        return dateX;
    }

    public static float getDateY() {
        return dateY;
    }

    public static float getHourX() {
        return hourX;
    }

    public static float getHourY() {
        return hourY;
    }

    public static float getMinuteX() {
        return minuteX;
    }

    public static float getMinuteY() {
        return minuteY;
    }

    /**
     * Draw a solid background. Usually if this runs the user has not opened companion app.
     * @param minute The minute to decide the color
     * @param canvas The canvas to draw on.
     */
    public static void drawSolidBackground(String minute, Canvas canvas) {
        int color = ColorFactory.getColorNoPack(minute.charAt(1));
        canvas.drawColor(color);

    }

    /**
     * Message telling the user to open companion app draw instead of fact.
     */
    public static void drawMessage(ArrayList<String> t, Coordinates c, Canvas d, Paint paint) {
        for(int i = 0; i < t.size(); i++) {
            d.drawText(t.get(i), c.getStartXOffset(i)[0], c.getStartXOffset(i)[1],
                    paint);
        }
    }
}

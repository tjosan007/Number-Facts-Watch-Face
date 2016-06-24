package com.tarwinderjosan.numberfactswatchface.text;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.tarwinderjosan.numberfactswatchface.util.Utils;

/**
 * Represents the Balloon, where minute value resides in,
 */
public class Balloon {

    private final float HEIGHTDP = 45;
    private final float WIDTHDP = 65;

    private float mDensity;
    private Coord[] coords;
    private Path mainPath;
    private Path trianglePath;
    private Path linePath;
    private Path leftPath;


    private float radius;

    private Paint boxPaint;
    private Paint trianglePaint;

    private Paint lineAmbient;
    private Paint boxPaintAmbient;
    private Paint trianglePaintAmbient;

    public Balloon(Paints paints) {
        radius = 10.0f;
        mDensity = Utils.density(Utils.CONTEXT);

        boxPaint = paints.getBoxPaint();
        trianglePaint = paints.getTrianglePaint();

        boxPaintAmbient = paints.getBoxPaintAmbient();
        trianglePaintAmbient = paints.getTrianglePaintAmbient();
        lineAmbient = paints.getLineAmbient();

        // Setup the coordinates relative to the balloon for drawing a Path rect
        coords = new Coord[5];
        coords[0] = new Coord(0, 0);
        coords[1] = new Coord(mDensity * WIDTHDP, 0);
        coords[2] = new Coord(mDensity * WIDTHDP, mDensity * HEIGHTDP);
        // Starting from origin going down
        coords[3] = new Coord(0, mDensity * HEIGHTDP);
        coords[4] = new Coord(0, 0);

        // The main path, aka the Balloon Rect
        CornerPathEffect corEffect = new CornerPathEffect(radius);

        mainPath = new Path();
        mainPath.setFillType(Path.FillType.EVEN_ODD);
        mainPath.moveTo(coords[0].getX(), coords[0].getY());
        // Set the Paint for both ambient and interactive to be round radius 10
        boxPaint.setPathEffect(corEffect);
        boxPaintAmbient.setPathEffect(corEffect);

        for(int j= 1; j < coords.length; j++) {
            mainPath.lineTo(coords[j].getX(), coords[j].getY());
        }
        mainPath.close();

        // The triangle which extrudes from the bottom
        trianglePath = new Path();
        trianglePath.moveTo(((WIDTHDP / 2) + 10) * mDensity, HEIGHTDP * mDensity);
        trianglePath.lineTo(WIDTHDP / 2 * mDensity, (HEIGHTDP + 10) * mDensity);
        trianglePath.lineTo(((WIDTHDP / 2) - 10) * mDensity, HEIGHTDP * mDensity);
        trianglePath.close();

        linePath = new Path();
        linePath.moveTo( ( ((WIDTHDP / 2) + 10)) * mDensity, HEIGHTDP * mDensity);
        linePath.lineTo((((WIDTHDP / 2) - 10)) * mDensity, HEIGHTDP * mDensity);
        linePath.close();

        leftPath = new Path();
        leftPath.moveTo(0, HEIGHTDP * mDensity);
        leftPath.lineTo( ( ( (WIDTHDP / 2) - 10) + (1.5f * mDensity)) * mDensity, HEIGHTDP * mDensity);
        leftPath.close();

    }

    private class Coord {
        private float x, y;
        public Coord(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    public void drawAmbient(Canvas canvas, Rect rect, String minute, Paint minutePaint) {
        // Calculate X coordinate of top left
        float xVal = getBalloonX(minute, minutePaint);
        float yVal = getBalloonY(minute, minutePaint);

        canvas.translate(xVal, yVal);
        canvas.drawPath(mainPath, boxPaintAmbient);
        canvas.drawPath(trianglePath, trianglePaintAmbient);
        canvas.drawPath(linePath, lineAmbient);
        canvas.translate(0, 0);
    }

    public void drawInteractive(Canvas canvas, Rect rect, String minute, Paint minutePaint) {
        float xVal = getBalloonX(minute, minutePaint);
        float yVal = getBalloonY(minute, minutePaint);

        canvas.save();
        canvas.translate(xVal, yVal);
        canvas.drawPath(mainPath, boxPaint);
        canvas.drawPath(trianglePath, trianglePaint);
        canvas.translate(0, 0);
        canvas.restore();
    }

    private float getBalloonX(String minute, Paint minutePaint) {
        float totalWidth = WIDTHDP * mDensity;
        float txtSize = minutePaint.measureText(minute);
        float remainingWidth = totalWidth - txtSize;
        float paddingEach = remainingWidth / 2;
        float xVal = Placement.getMinuteX() - paddingEach - txtSize / 2;
        return xVal;
    }

    private float getBalloonY(String minute, Paint minutePaint) {
        float totalHeight = HEIGHTDP * mDensity;
        Rect bounds = new Rect();
        minutePaint.getTextBounds(minute, 0, minute.length(), bounds);
        float remainingHeight = totalHeight - bounds.height();
        float paddingEach2 = remainingHeight / 2;
        float yVal = Placement.getMinuteY()  - paddingEach2 - bounds.height();
        return yVal;
    }
}

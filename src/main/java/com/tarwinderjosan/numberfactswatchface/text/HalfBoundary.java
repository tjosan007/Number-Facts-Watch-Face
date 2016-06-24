package com.tarwinderjosan.numberfactswatchface.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.tarwinderjosan.numberfactswatchface.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates and deposits (X,Y) coordinates in the Coordinates class.
 */
public class HalfBoundary {

    public static void initialize() {
        new HalfBoundary(Utils.width(Utils.CONTEXT), Utils.height(Utils.CONTEXT),
                Utils.width(Utils.CONTEXT)/2, 4, (int)Utils.density(Utils.CONTEXT) * 20, 0, HalfBoundary.Position.FACT);
    }

    public enum Position {
        // Doesn't influence anything atm
        FACT
    }

    private int mWidth, mHeight;
    private int mSubsections;
    double mRadius;
    private Coordinates c;
    private double mStartingAngle = 0;
    private float mHeightOffset;

    private List<ArrayList<String>> myList;

    private HalfBoundary(int width, int height, int radius, int subsections,
                        int heightOffset, double topOffset, Position pos) {
        c = Coordinates.getInstance();
        if(pos == Position.FACT) {
            myList = new ArrayList<>();
            mWidth = width;
            mHeight = height;
            mSubsections = subsections;
            mRadius = radius;
            mHeightOffset = heightOffset;


            mStartingAngle = getAngleForTopOffset(topOffset);
            setCoordinates();
        }
    }


    private double getAngleForTopOffset(double offset) {
        return Math.toDegrees(Math.asin(offset / mRadius));

    }

    private void setCoordinates() {
        // Set the coordinates (X,Y) of all the subsections
        // Each subsection has to be certain degrees to maintain the proper margin
        for(int i = 0; i < mSubsections;i++) {
            double angle = getCurrentDegrees(i);
            double yCoord = getYCoord(i) + mRadius;
            double xCoord = getXCoord(angle);
            double xCoord2 = mWidth - xCoord;
            double yCoord2 = yCoord;
            c.add(xCoord, yCoord, xCoord2, yCoord2);
        }
    }

    /**
     * Get the reference angle for the current subsection.
     */
    private double getCurrentDegrees(int subsection) {
        // Get the Y coordinate for the specific subsection
        double yCoord = getYCoord(subsection);
        // get the angle
        double angle = Math.asin(yCoord / mRadius);
        return angle;
    }

    /**
     * Get the Y coordinate for this subsection in relation to the initial subsection and the height offset.
     * @param subsection The subsection from 0 (first) - length - 1
     * @return
     */
    private double getYCoord(int subsection) {
        return mRadius*Math.sin(Math.toRadians(mStartingAngle)) + subsection * mHeightOffset;
    }

    private double getXCoord(double angle) {
        return mRadius - (mRadius * Math.cos(angle));
    }

}

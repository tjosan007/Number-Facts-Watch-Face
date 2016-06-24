package com.tarwinderjosan.numberfactswatchface.text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Coordinates {

    private StringTokenizer tokenizer;
    private static Coordinates coordinates;

    private static List<ArrayList<String>> holderActual;
    private static List<ArrayList<String>> holderTemp;

    private Coordinates() {
    }

    public static Coordinates getInstance() {
        if(coordinates == null)
            coordinates = new Coordinates();
        return coordinates;
    }

    public float[] getStart(int pos) {
        float[] coordHolder = new float[2];
        tokenizer = new StringTokenizer(holderActual.get(pos).get(0), ",");
        coordHolder[0] = Float.parseFloat(tokenizer.nextToken());
        coordHolder[1] = Float.parseFloat(tokenizer.nextToken());
        return coordHolder;

    }

    public float[] getStartXOffset(int pos) {
        float[] coordHolder = new float[2];
        tokenizer = new StringTokenizer(holderTemp.get(pos).get(0), ",");
        coordHolder[0] = Float.parseFloat(tokenizer.nextToken());
        coordHolder[1] = Float.parseFloat(tokenizer.nextToken());
        return coordHolder;

    }

    public float[] getEnd(int pos) {
        float[] coordHolder = new float[2];
        tokenizer = new StringTokenizer(holderActual.get(pos).get(1), ",");
        coordHolder[0] = Float.parseFloat(tokenizer.nextToken());
        coordHolder[1] = Float.parseFloat(tokenizer.nextToken());
        return coordHolder;
    }

    public void drawOutline(Canvas canvas) {

        Paint outlinePaint = new Paint();
        outlinePaint.setStrokeWidth(1f);
        outlinePaint.setColor(Color.RED);
        outlinePaint.setStyle(Paint.Style.STROKE);
        for(int i = 0; i < holderActual.size(); i++) {
            float[] start = coordinates.getStart(i);
            float[] end = coordinates.getEnd(i);
            canvas.drawLine(start[0], start[1], end[0], end[1], outlinePaint);
        }
    }

    public int getSize() {
        return holderActual.size();
    }

    public double[] getWidths() {
        double[] widths = new double[holderActual.size()];
        for(int i = 0; i < holderActual.size(); i++) {
            widths[i] = getEnd(i)[0] - getStart(i)[0];
        }
        return widths;
    }

    public void printWidths() {
        double[] widths = new double[holderActual.size()];
        for(int i = 0; i < holderActual.size(); i++) {
            widths[i] = getEnd(i)[0] - getStart(i)[0];
        }
    }

    public void add(double x1, double y1, double x2, double  y2) {
        if(holderActual == null) {
            holderActual = new ArrayList<>();
        }
        ArrayList<String> temp = new ArrayList<>();
        temp.add(x1 + "," + y1);
        temp.add(x2 + "," + y2);
        holderActual.add(temp);
    }


    public void resetTempHolder() {
        holderTemp = new ArrayList<>(holderActual.size());
        for(int i = 0; i < holderActual.size(); i++) {
            ArrayList<String> myList = new ArrayList<String>();
            for(int j = 0; j < holderActual.get(i).size(); j++) {
                myList.add(holderActual.get(i).get(j));
            }
            holderTemp.add(myList);
        }
    }
    public List<ArrayList<String>> getHolder() {
        return holderActual;
    }


    public void offsetX(int boundaryIndex, double leftPadding) {
        String value = holderTemp.get(boundaryIndex).get(0);
        StringTokenizer nSt = new StringTokenizer(value, ",");
        float[] f = new float[2];
        f[0] = Float.parseFloat(nSt.nextToken());
        f[0] += leftPadding;
        f[1] = Float.parseFloat(nSt.nextToken());
        holderTemp.get(boundaryIndex).set(0, f[0] + "," + f[1]);
    }
}

package com.tarwinderjosan.numberfactswatchface.text;

import android.graphics.Paint;

import com.tarwinderjosan.numberfactswatchface.text.Coordinates;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Custom fact tokenizer used to separate a String fact into
 * values in a String array which fit into the Half Boundary widths.
 */
public class FactTokenizer {



    private Paint mFactPaint;
    private ArrayList<String> mSentences;
    private ArrayList<String> mFactWords;
    private Coordinates c;
    private double[] mWidths;
    private int sidePadding = 0;

    public FactTokenizer(Paint factPaint, String mCurrentFact) {

        mFactPaint = factPaint;
        mSentences = new ArrayList<>();
        mFactWords = new ArrayList<>();
        c = Coordinates.getInstance();
        setFact(mCurrentFact);
    }

    public void setFact(String fact) {
        mSentences = new ArrayList<>();
        mFactWords = new ArrayList<>();

        // Reset temp holder to holder vals
        c.resetTempHolder();

        StringTokenizer st = new StringTokenizer(fact, " ");
        while(st.hasMoreTokens()) {
            mFactWords.add(st.nextToken());
        }
        mWidths = c.getWidths();
//        for(int i = 0; i < mWidths.length; i++) {
//            // 20 pixel padding l/r
//            mWidths[i] =  mWidths[i] - 40;
//        }

        setSentences();
    }

    public void setSentences() {
        String currSentence = "";
        int boundaryIndex = 0;
        // While there are more words to process
       while(mFactWords.size() > 0) {
           String currentWord = mFactWords.get(0);
           // Still room for more words in the current boundary
           if(mFactPaint.measureText(currSentence + currentWord) < mWidths[boundaryIndex]) {
               // If the last char in the word is a '.', then it is the last word in the fact
               if(Character.toString(currentWord.charAt(currentWord.length() - 1)).equals(".")) {
                   // Without final " "
                   currSentence += mFactWords.get(0);
                   centerHorizontally(currSentence, mWidths[boundaryIndex], boundaryIndex);
                   mSentences.add(currSentence);
                   mFactWords.remove(0);
                   return;
               } else {
                   currSentence += mFactWords.get(0) + " ";
               }

               mFactWords.remove(0);
           } else {
               // When more room left, go to next boundary.
               // Center horizontally first
               centerHorizontally(currSentence, mWidths[boundaryIndex], boundaryIndex);
               boundaryIndex++;
               mSentences.add(currSentence);
               currSentence = "";
           }
       }

    }

    private void centerHorizontally(String currSentence, double mWidth, int boundaryIndex) {
        double widthLeft = mWidth - mFactPaint.measureText(currSentence);
        double leftPadding = widthLeft / 2.0;
        offsetX(boundaryIndex, leftPadding);
    }

    public ArrayList<String> getSentences() {
        return mSentences;
    }

    public void offsetX(int boundaryIndex, double leftPadding) {
       c.offsetX(boundaryIndex, leftPadding);

    }

    public void setPadding(int padding) {
        this.sidePadding = padding;
    }
}

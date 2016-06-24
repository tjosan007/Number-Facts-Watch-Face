package com.tarwinderjosan.numberfactswatchface.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.tarwinderjosan.numberfactswatchface.debug.DebugManager;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

public class ImageFactory {

    /**
     * Return current image to use relating to the minute, and the prefix of the fact.
     * 'a' prefix for fact in first column, 'b' for second, etc.
     */
    public static Bitmap getCurrentImage(String minute, char prefix) {
        Log.d("TAG", "Prefix is: " + prefix);
        if(!(prefix == 'a' || prefix  == 'b' || prefix == 'c')) {
            prefix = 'a';
        }
        Context context = Utils.CONTEXT;
        Resources r = context.getResources();

        // Debugging - Use "a" prefix for now (only images for that!)
        int id = r.getIdentifier(prefix+minute, "drawable", context.getPackageName());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = true;
        Bitmap image = BitmapFactory.decodeResource(r, id, options);
        return image;

    }

    public static Bitmap getResizedBitmap(Bitmap old, int newWidth, int newHeight) {
        int width = old.getWidth();
        int height = old.getHeight();
        float scaleWidth = ((float)newWidth) / width;
        float scaleHeight = ((float)newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(old, 0, 0, width, height, matrix, false);
        old.recycle();
        return resizedBitmap;
    }
}

package com.tarwinderjosan.numberfactswatchface.main;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import com.tarwinderjosan.numberfactswatchface.util.Boundary;
import com.tarwinderjosan.numberfactswatchface.util.Prefs;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

public class NumberFactsMainService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new MainEngine();
    }

    private class MainEngine extends CanvasWatchFaceService.Engine {

        private boolean isRound;
        private Painter painter;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            Utils.CONTEXT = getApplicationContext();
            Prefs.init(getApplicationContext());
            setWatchFaceStyle(
                    // OuterClassName.this to reference outer class from inner class
                    new WatchFaceStyle.Builder(NumberFactsMainService.this)
                            .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                            .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                            .setHotwordIndicatorGravity(Gravity.CENTER | Gravity.BOTTOM)
                            .setStatusBarGravity(Gravity.TOP | Gravity.RIGHT)
                            .setShowSystemUiTime(false)
                            .build()
            );
            Log.d("TAG", "onCreate!!!");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);


        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            super.onApplyWindowInsets(insets);
            isRound = insets.isRound();
            Log.d("TAG", "Window insets!!");
            painter = new Painter(Utils.CONTEXT, isRound);

        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            // When properties of device are determined
            super.onPropertiesChanged(properties);

        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            if(painter.getTime().isChanged()) {
                painter.update();
            }
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            painter.getPaints().setIsInAmbientMode(inAmbientMode);

        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            painter.paint(canvas, isRound, isInAmbientMode(), bounds);

        }
    }
}

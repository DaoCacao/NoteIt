package com.example.legion.noteit;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


public class Utils {

    private static final Point displaySize = new Point();
    private static final DisplayMetrics displayMetrics = new DisplayMetrics();
    private static float densityFactor = 1;

    public static void getDisplaySizeAndDensity() {
        WindowManager manager = (WindowManager) AppLoader.appContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (display != null) {
            display.getSize(displaySize);
            display.getMetrics(displayMetrics);
            densityFactor = displayMetrics.density;
        }
    }

    public static int dp(float pixels) {
        return (int) Math.abs(densityFactor * pixels);
    }
}

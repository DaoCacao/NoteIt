package core.legion.noteit;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class Utils {

    private static final Point displaySize = new Point();
    private static final DisplayMetrics displayMetrics = new DisplayMetrics();
    private static float densityFactor = 1;

    static void getDisplaySizeAndDensity() {
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

    public static void hideKeyboard(View v) {
        if (v == null) return;
        try {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("...", e.getMessage());
        }
    }

    public static void showKeyboard(View v) {
        if (v == null) return;
        try {
            InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            Log.e("...", e.getMessage());
        }
    }
}

package org.esn_spain.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP;
    }
    public static boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float a = (Color.alpha(color1) * ratio) + (Color.alpha(color2) * inverseRation);
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    public static void setTabLayoutIndicatorColor(TabLayout tabs, int color) {
        try {
            Field field = TabLayout.class.getDeclaredField("mTabStrip");
            field.setAccessible(true);
            Object value = field.get(tabs);

            Method method = value.getClass().getDeclaredMethod("setSelectedIndicatorColor", Integer.TYPE);
            method.setAccessible(true);
            method.invoke(value, color);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static int modColor(int color, float sFactor, float vFactor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] *= sFactor; // value component
        hsv[2] *= vFactor;
        return Color.HSVToColor(hsv);
    }

    public static GradientDrawable createGradient(GradientDrawable.Orientation orientation, int... colors) {
        return new GradientDrawable(orientation, colors);
    }

    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT < 16) view.setBackgroundDrawable(background);
        else view.setBackground(background);
    }

    public static void setHeight(View view, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public static boolean isPortrait(Context c) {
        return c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static int dp(Context c, int dp) {
        return (int)(dp * c.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    public static void setMarginsDp(View v, int l, int t, int r, int b) {
        Context c = v.getContext();
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(Utils.dp(c,l), Utils.dp(c,t), Utils.dp(c,r), Utils.dp(c,b));
            v.requestLayout();
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return (network!=null && network.isConnected());
    }

    public static boolean isKitKatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static void changeBackgroundColor(View v, int newColor, int time) {
        if (isKitKatOrHigher()) changeBackgroundColorKitKat(v, newColor, time);
        else changeBackgroundColorKitKat(v, newColor, time);
    }
    private static void changeBackgroundColorKitKat(View v, int newColor, int time) {
        Drawable oldBackground = v.getBackground();
        Drawable color = new ColorDrawable(newColor);
        LayerDrawable ld = new LayerDrawable(new Drawable[] { color });
        if (oldBackground == null) v.setBackgroundDrawable(ld);
        else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
            v.setBackgroundDrawable(td);
            td.startTransition(time);
        }
    }
    private static void changeBackgroundColorPreKitKat(final View v, int newColor, int time) {
        Drawable oldBackground = v.getBackground();
        Drawable.Callback drawableCallback = new Drawable.Callback() {
            Handler handler = new Handler();
            @Override
            public void invalidateDrawable(Drawable who) {
                v.setBackgroundDrawable(who); }
            @Override
            public void scheduleDrawable(Drawable who, Runnable what, long when) {
                handler.postAtTime(what, when); }
            @Override
            public void unscheduleDrawable(Drawable who, Runnable what) {
                handler.removeCallbacks(what); }
        };
        Drawable color = new ColorDrawable(newColor);
        LayerDrawable ld = new LayerDrawable(new Drawable[] { color });
        if (oldBackground == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) ld.setCallback(drawableCallback);
            else v.setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) td.setCallback(drawableCallback);
            else v.setBackgroundDrawable(td);
            td.startTransition(time);
        }
    }

}

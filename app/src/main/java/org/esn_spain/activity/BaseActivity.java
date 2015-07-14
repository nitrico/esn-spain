package org.esn_spain.activity;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import org.esn_spain.R;
import java.util.ArrayList;
import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void setTaskDescriptionColor(int color) {
        setTaskDescriptionColor(color, R.mipmap.ic_launcher);
    }

    protected void setTaskDescriptionColor(int color, int iconResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), iconResource);
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, color);
            setTaskDescription(td);
            bm.recycle();
        }
    }

    /**
     * It's important to set overflowDescription atribute in styles, so we can grab
     * the reference to the overflow icon. Check: res/values/styles.xml
     */
    protected void setOverflowButtonColor(int color) {
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        final String overflowDescription = getString(R.string.abc_action_menu_overflow_description);
        final ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ArrayList<View> outViews = new ArrayList<>();
                decorView.findViewsWithText(outViews, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                if (outViews.isEmpty()) return;
                TintImageView overflow = (TintImageView) outViews.get(0);
                overflow.setColorFilter(colorFilter);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) //noinspection deprecation
                    decorView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}

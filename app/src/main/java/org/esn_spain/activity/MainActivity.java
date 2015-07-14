package org.esn_spain.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.esn_spain.R;
import org.esn_spain.fragment.EventsFragment;
import org.esn_spain.fragment.MembersFragment;
import org.esn_spain.fragment.PartnersFragment;
import org.esn_spain.helper.C;
import org.esn_spain.helper.Utils;
import org.esn_spain.model.SectionItem;
import org.esn_spain.model.simple.Data;
import org.esn_spain.network.DataManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener,
        DataManager.OnSectionContentLoadedListener {

    public static boolean SHOW_KEYLINES;

    @Bind(R.id.appbar)    AppBarLayout mAppbar;
    @Bind(R.id.toolbar)   Toolbar mToolbar;
    @Bind(R.id.tabs)      TabLayout mTabs;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Nullable @Bind(R.id.section) LinearLayout mSection;
    @Nullable @Bind(R.id.title) TextView mTitle;
    @Nullable @Bind(R.id.subtitle) TextView mSubtitle;

    private SharedPreferences mPreferences;
    private TabsAdapter mTabsAdapter;
    public static String mSectionUrl;

    private static SectionItem currentSection;

    public static SectionItem getCurrentSection() {
        return currentSection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataManager.from(this).requestGalaxy(this);
        mSectionUrl = "http://www.esnmurcia.es";
        loadPreferences();
        setContentView(SHOW_KEYLINES ? R.layout.activity_main_keylines : R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayShowTitleEnabled(false);

        if (currentSection != null) {
            if (mTitle != null) mTitle.setText(currentSection.getName());
            if (mSubtitle != null) mSubtitle.setText(currentSection.getUniversity());
        }
        else {
            if (mTitle != null) mTitle.setText("ESN Murcia");
            if (mSubtitle != null) mSubtitle.setText("Universidad de Murcia");
        }

        if (mSection != null) {
            mSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(MainActivity.this, SectionChooserActivity.class), 1);
                }
            });
        }

        initTabs();
    }

    @Override
    public void onSectionContentLoaded(Data data) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.show_keylines).setChecked(SHOW_KEYLINES);
        return true; // return super.onCreateOptionsMenu(menu);
    }

    @Override @SuppressLint("CommitPrefEdits")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_section_chooser:
                startActivityForResult(new Intent(this, SectionChooserActivity.class), 1);
                return true;*/
            case R.id.show_keylines:
                item.setChecked(!item.isChecked());
                mPreferences.edit().putBoolean("SHOW_KEYLINES", item.isChecked()).commit();
                recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Bundle b = data.getExtras();
            SectionItem selectedSection = (SectionItem) b.getSerializable("SECTION_SELECTED");
            if (selectedSection != null && currentSection != selectedSection) {
                currentSection = selectedSection;
                if (mTitle != null) mTitle.setText(currentSection.getName());
                if (mSubtitle != null) mSubtitle.setText(currentSection.getUniversity());
                // cambiar también aquí la url
                if (mTabsAdapter != null) {
                    for (int i=0; i<mTabsAdapter.getCount(); i++) {
                        SwipeRefreshLayout.OnRefreshListener f
                                = (SwipeRefreshLayout.OnRefreshListener) mTabsAdapter.getItem(i);
                        f.onRefresh();
                    }
                }
            }
        }
    }

    private void loadPreferences() {
        if (mPreferences == null) mPreferences = getPreferences(MODE_PRIVATE);
        SHOW_KEYLINES = mPreferences.getBoolean("SHOW_KEYLINES", false);
    }

    private void initTabs() {
        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mTabsAdapter.addFragment(new EventsFragment(), "Actividades");
        mTabsAdapter.addFragment(new PartnersFragment(), "Descuentos");
        mTabsAdapter.addFragment(new EventsFragment(), "Proyectos");
        mTabsAdapter.addFragment(new MembersFragment(), "Información");
        //mTabsAdapter.addFragment(new SettingsFragment(), "Ajustes");
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mTabsAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabs.setupWithViewPager(mViewPager);
        if (Utils.isPortrait(this)) {
            for (int i=0; i < mTabsAdapter.getCount(); i++) {
                TabLayout.Tab tab = mTabs.getTabAt(i);
                tab.setContentDescription(mTabs.getTabAt(i).getText());
                tab.setText("");
                tab.setIcon(getIconResource(i));
                tab.getIcon().setColorFilter(getTabIconColor(0, i), PorterDuff.Mode.SRC_IN);
            }
        }
        /*if (mTitle != null && mSubtitle != null) {
            mTitle.setText("ESN Murcia");
            mSubtitle.setText(mTabsAdapter.getPageTitle(0));
        }
        else mToolbar.setTitle(mTabsAdapter.getPageTitle(0)); */
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int newColor = getNewColor(position, positionOffset);
        mAppbar.setBackgroundColor(newColor);
    }

    @Override
    public void onPageSelected(int position) {
        /*if (mSubtitle != null) {
            mSubtitle.setText(mTabsAdapter.getPageTitle(position));
        }
        else mToolbar.setTitle(mTabsAdapter.getPageTitle(position)); */
        if (Utils.isPortrait(MainActivity.this)) {
            for (int i=0; i<mTabsAdapter.getCount(); i++) {
                Drawable tabIcon = mTabs.getTabAt(i).getIcon();
                tabIcon.setColorFilter(getTabIconColor(position, i), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int getTabIconColor(int selected, int position) {
        return position == selected ? C.WHITE : C.WHITE_TRANS;
    }

    private int getNewColor(int position, float offset) {
        int next;
        if (offset > 0) next = (position < mTabsAdapter.getCount()) ? position+1 : position;
        else next = (position > 0) ? position-1 : 0;
        return Utils.blendColors(C.COLORS[next], C.COLORS[position], offset);
    }

    private static int getIconResource(int position) {
        switch (position) {
            case 0: return R.drawable.ic_event_white_24dp;
            case 1: return R.drawable.ic_credit_card_white_24dp;
            case 2: return R.drawable.ic_favorite_border_white_24dp;
            case 3: return R.drawable.ic_info_outline_white_24dp;
            case 4: return R.drawable.ic_settings_white_24dp;
        }
        return 0;
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }
        @Override public int getCount() {
            return mFragments.size();
        }
        @Override public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}

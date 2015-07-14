package org.esn_spain.activity;

import android.annotation.SuppressLint;
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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import org.esn_spain.R;
import org.esn_spain.fragment.EventsFragment;
import org.esn_spain.fragment.MembersFragment;
import org.esn_spain.fragment.PartnersFragment;
import org.esn_spain.helper.C;
import org.esn_spain.helper.Utils;
import org.esn_spain.model.simple.Data;
import org.esn_spain.model.simple.esn.Galaxy;
import org.esn_spain.model.simple.esn.Section;
import org.esn_spain.network.DataManager;
import org.esn_spain.spinner.TwoLinesSpinnerAdapter;
import org.esn_spain.spinner.TwoLinesSpinnerItem;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icicle;

public class MainActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener,
        DataManager.OnGalaxyAvailableListener,
        DataManager.OnSectionContentLoadedListener {

    public static boolean SHOW_KEYLINES;

    @Bind(R.id.appbar)    AppBarLayout mAppbar;
    @Bind(R.id.toolbar)   Toolbar mToolbar;
    @Bind(R.id.tabs)      TabLayout mTabs;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Nullable @Bind(R.id.section_spinner) AppCompatSpinner mSpinner;

    private Galaxy mGalaxy;
    private SharedPreferences mPreferences;
    private TabsAdapter mTabsAdapter;
    @Icicle protected String mSectionId;
    @Icicle protected Section mSection;
    @Icicle protected String mSectionUrl;

    public String getBaseUrl() {
        return mSectionUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.from(this).requestGalaxy(this);
        mSectionUrl = "http://www.esnmurcia.es";
        loadPreferences();
        setContentView(SHOW_KEYLINES ? R.layout.activity_main_keylines : R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayShowTitleEnabled(false);

        initTabs();
        //mToolbar.setTitle(mTabsAdapter.getPageTitle(0));
    }

    @Override
    public void onGalaxyAvailable(Galaxy galaxy) {
        mGalaxy = galaxy;
        if (mSpinner != null) {
            mSpinner.setAdapter(getTwoLinesSpinnerAdapter());
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TwoLinesSpinnerItem selected = (TwoLinesSpinnerItem) mSpinner.getAdapter().getItem(position);
                    mSectionId = selected.getSectionId();
                    mSection = mGalaxy.findSectionById(mSectionId);
                    if (mSection.getFile() != null)
                        DataManager.from(MainActivity.this).loadSectionContent(mSection.getFile(), MainActivity.this);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mSectionId = null;
                    mSection = null;
                }
            });
        }
    }

    @Override
    public void onSectionContentLoaded(Data data) {

    }


    private TwoLinesSpinnerAdapter getTwoLinesSpinnerAdapter() {
        List<TwoLinesSpinnerItem> items = new ArrayList<>();
        for (Section section: mGalaxy.getCountry(0).getActiveSections()) {
            TwoLinesSpinnerItem item = new TwoLinesSpinnerItem(
                    section.getName(),
                    section.getUniversity().getName(),
                    section.getId()
            );
            items.add(item);
        }
        return new TwoLinesSpinnerAdapter(this, R.layout.spinner_item_two_lines, items);
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
            case R.id.show_keylines:
                item.setChecked(!item.isChecked());
                mPreferences.edit().putBoolean("SHOW_KEYLINES", item.isChecked()).commit();
                recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int newColor = getNewColor(position, positionOffset);
        mAppbar.setBackgroundColor(newColor);
    }

    @Override
    public void onPageSelected(int position) {
        //mToolbar.setTitle(mTabsAdapter.getPageTitle(position));
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

package org.esn_spain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import org.esn_spain.R;
import org.esn_spain.model.SectionHeaderView;
import org.esn_spain.model.SectionItem;
import org.esn_spain.model.SectionItemHeader;
import org.esn_spain.model.SectionView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

public class SectionChooserActivity extends AppCompatActivity implements ViewEventListener<SectionItem> {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.sections_list) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_chooser);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_clear_mtrl_alpha);
        }
        mTitle.setText("Elige tu sección de ESN");

        displayAsPopUp();
        populateList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateList() {
        List<Object> items = new ArrayList<>();

        items.add(new SectionItemHeader("Granada"));
        items.add(new SectionItem("ESN Granada", "Universidad de Granada"));

        items.add(new SectionItemHeader("Madrid"));
        items.add(new SectionItem("ESN UAH", "Universidad de Alcalá"));
        items.add(new SectionItem("ESN UAM", "Universidad Autónoma de Madrid"));
        items.add(new SectionItem("ESN UAX", "Universidad Alfonso X el Sabio"));
        items.add(new SectionItem("ESN UC3M", "Universidad Carlos 3 de Madrid"));
        items.add(new SectionItem("ESN UCM", "Universidad Complutense de Madrid"));
        items.add(new SectionItem("ESN URJC", "Universidad Rey Juan Carlos"));

        items.add(new SectionItemHeader("Murcia"));
        items.add(new SectionItem("ESN Murcia", "Universidad de Murcia"));

        items.add(new SectionItemHeader("Valladolid"));
        items.add(new SectionItem("ESN Valladolid", "Universidad de Valladolid"));

        SmartAdapter.items(items)
                .map(SectionItem.class, SectionView.class)
                .map(SectionItemHeader.class, SectionHeaderView.class)
                .listener(this)
                .into(mListView);
    }

    private void displayAsPopUp() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.dimAmount = 0.75f;
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setAttributes(params);
        getWindow().setLayout((int)(width * .75), (int)(height * .75));
    }

    @Override
    public void onViewEvent(int i, SectionItem sectionItem, int i1, View view) {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("SECTION_SELECTED", sectionItem);
        intent.putExtras(b);
        setResult(1, intent);
        finish();
    }

}

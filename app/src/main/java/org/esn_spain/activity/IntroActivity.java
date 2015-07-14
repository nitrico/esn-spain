package org.esn_spain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import org.esn_spain.R;
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

public class IntroActivity extends BaseActivity implements DataManager.OnGalaxyAvailableListener {

    //@Bind(R.id.country_spinner) AppCompatSpinner countrySpinner;
    @Bind(R.id.section_spinner) AppCompatSpinner sectionSpinner;
    @Bind(R.id.start)           Button startButton;

    @Icicle protected Galaxy mGalaxy;
    //@Icicle protected int countryPositionSelected;
    @Icicle protected int sectionPositionSelected;

    //private ArrayAdapter<String> countryAdapter;
    private TwoLinesSpinnerAdapter sectionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        DataManager.from(this).requestGalaxy(this);
    }

    private TwoLinesSpinnerAdapter getTwoLinesSpinnerAdapter() {
        List<TwoLinesSpinnerItem> items = new ArrayList<>();
        for (Section section: mGalaxy.getCountry(0).getSections()) {
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
    public void onGalaxyAvailable(Galaxy galaxy) {
        mGalaxy = galaxy;
        //sectionSpinner.setEnabled(false);

        //countryAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        //countryAdapter.addAll(mGalaxy.getCountryNames());
        //countrySpinner.setAdapter(countryAdapter);
        //countrySpinner.setOnItemSelectedListener(countryListener);

        //sectionSpinner.setEnabled(false);
        sectionSpinner.setAdapter(getTwoLinesSpinnerAdapter());
        sectionSpinner.setOnItemSelectedListener(cityListener);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                //bundle.putInt("COUNTRY_SELECTED", countryPositionSelected);
                bundle.putInt("SECTION_SELECTED", sectionPositionSelected);
                intent.putExtras(bundle);
                intent.setClass(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private AdapterView.OnItemSelectedListener countryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //countryPositionSelected = position;
            sectionSpinner.setAdapter(getTwoLinesSpinnerAdapter());
            sectionSpinner.setEnabled(true);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            sectionSpinner.setEnabled(false);
        }
    };

    private AdapterView.OnItemSelectedListener cityListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sectionPositionSelected = position;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}

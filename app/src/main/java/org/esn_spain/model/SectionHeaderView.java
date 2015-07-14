package org.esn_spain.model;

import android.content.Context;
import android.widget.TextView;
import org.esn_spain.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class SectionHeaderView extends BindableLayout<SectionItemHeader> {

    @Bind(R.id.city) TextView city;

    public SectionHeaderView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section_header;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(SectionItemHeader sectionItemHeader) {
        city.setText(sectionItemHeader.getCity());
    }

}

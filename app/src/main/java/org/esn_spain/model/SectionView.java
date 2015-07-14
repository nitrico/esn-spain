package org.esn_spain.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.esn_spain.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class SectionView extends BindableLayout<SectionItem> {

    @Bind(R.id.line1) TextView section;
    @Bind(R.id.line2) TextView university;

    public SectionView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(final SectionItem sectionItem) {
        section.setText(sectionItem.getName());
        university.setText(sectionItem.getUniversity());

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(0);
            }
        });
    }

}

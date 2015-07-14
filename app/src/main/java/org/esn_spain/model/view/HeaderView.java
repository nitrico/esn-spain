package org.esn_spain.model.view;

import android.content.Context;
import android.widget.TextView;
import org.esn_spain.R;
import org.esn_spain.helper.Utils;
import org.esn_spain.model.simple.Header;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class HeaderView extends BindableLayout<Header> {

    @Bind(R.id.header) TextView header;

    public HeaderView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_header;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Header item) {
        header.setText(item.getName());
        if (item.getColor()  != 0) header.setTextColor(item.getColor());
        if (item.getMargin() != 0) Utils.setMarginsDp(header, item.getMargin(), 0, item.getMargin(), 0);
    }

}

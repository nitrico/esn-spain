package org.esn_spain.model.view;

import android.content.Context;
import android.view.View;
import org.esn_spain.R;
import org.esn_spain.model.simple.Space;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class SpaceView extends BindableLayout<Space> {

    @Bind(R.id.space) View space;

    public SpaceView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_space;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Space item) {
        if (item.getColor() != 0) space.setBackgroundColor(item.getColor());
    }

}

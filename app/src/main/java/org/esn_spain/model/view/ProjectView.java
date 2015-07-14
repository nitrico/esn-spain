package org.esn_spain.model.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.esn_spain.R;
import org.esn_spain.activity.MainActivity;
import org.esn_spain.helper.C;
import org.esn_spain.model.simple.Project;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class ProjectView extends BindableLayout<Project> {

    @Bind(R.id.project_image)    ImageView image;
    @Bind(R.id.project_title)    TextView title;
    @Bind(R.id.project_subtitle) TextView subtitle;

    public ProjectView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_project;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Project item) {
        subtitle.setTextColor(C.COLORS[2]);
        title.setTextColor(/*MainActivity.isDarkBackground() ? C.WHITE :*/ C.BLACK_SELECTED);
        title.setText(item.name);
        setTextOrHide(subtitle, item.from, true);
        Glide.with(getContext()).load(item.image).into(image);
        //ImageLoader.getInstance().displayImage(item.image, image);
    }

    private void setTextOrHide(TextView textView, String text, boolean conditionToShow) {
        if (text != null && conditionToShow) {
            textView.setText(text);
            textView.setVisibility(VISIBLE);
        }
        else textView.setVisibility(GONE);
    }

}

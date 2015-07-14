package org.esn_spain.model.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.esn_spain.R;
import org.esn_spain.helper.C;
import org.esn_spain.model.web.Member;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class MemberView extends BindableLayout<Member> {

    @Bind(R.id.member_image) ImageView image;
    @Bind(R.id.member_name)  TextView name;
    @Bind(R.id.member_title) TextView title;

    public MemberView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_member;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(final Member item) {
        title.setTextColor(C.COLORS[3]);
        name.setTextColor(/*MainActivity.isDarkBackground() ? C.WHITE :*/ C.BLACK_SELECTED);
        name.setText(item.member_name);
        setTextOrHide(title, item.member_text, true);
        Glide.with(getContext()).load(item.member_image).into(image);
        //ImageLoader.getInstance().displayImage(item.image, image);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(0);
            }
        });


        /*
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("SELECTED_MEMBER", item);
                i.putExtras(b);
                i.setClass(getContext(), FloatingActivity.class);
                getContext().startActivity(i, getActivityOptionsCompat().toBundle());
            }
        });
        */
    }

    private void setTextOrHide(TextView textView, String text, boolean conditionToShow) {
        if (text != null && conditionToShow) {
            textView.setText(text);
            textView.setVisibility(VISIBLE);
        }
        else textView.setVisibility(GONE);
    }

}

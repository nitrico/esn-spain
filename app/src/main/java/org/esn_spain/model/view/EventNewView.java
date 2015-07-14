package org.esn_spain.model.view;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.esn_spain.R;
import org.esn_spain.model.simple.Event;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class EventNewView extends BindableLayout<Event> {

    @Bind(R.id.event_image) ImageView image;
    @Bind(R.id.event_title) TextView title;
    @Bind(R.id.event_date)  TextView date;
    /*@Bind(R.id.event_time)  TextView time;
    @Bind(R.id.event_place) TextView place;
    @Bind(R.id.event_price) TextView price;*/

    Typeface bold = Typeface.createFromAsset(getResources().getAssets(), "fonts/Montserrat-Bold.otf");
    Typeface regular = Typeface.createFromAsset(getResources().getAssets(), "fonts/Montserrat-Regular.otf");

    public EventNewView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_event_new;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(final Event item) {
        //date.setTextColor(C.COLORS[0]);
        title.setText(item.name);
        title.setTypeface(bold);
        date.setTypeface(regular);
        setTextOrHide(date,  item.getDate(), true);
        /*setTextOrHide(time,  item.time,  true);
        setTextOrHide(place, item.place, true);
        setTextOrHide(price, item.price, false); */

        //image.setImageURI(Uri.parse(item.image));
        Glide.with(getContext())
             .load(item.image)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             //.animate(R.anim.abc_fade_in)
             .into(image);

        //ImageLoader.getInstance().displayImage(item.image, image);
        /*
        if (item.isNext()) {
            title.setTextColor(0xFF333333);
            layout.setBackgroundColor(0xFFFFFFFF);
        }
        else {
            title.setTextColor(0xFF888888);
            layout.setBackgroundColor(0xFFFFFFFF);
        }
        */
        /*setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("SELECTED_ACTIVITY", item);
                i.putExtras(b);
                i.setClass(getContext(), ActivityActivity.class);
                getContext().startActivity(i);
            }
        });*/
    }

    private void setTextOrHide(TextView textView, String text, boolean conditionToShow) {
        if (text != null && conditionToShow) {
            textView.setText(text);
            textView.setVisibility(VISIBLE);
        }
        else textView.setVisibility(GONE);
    }

}

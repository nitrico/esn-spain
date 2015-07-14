package org.esn_spain.model.view;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.esn_spain.R;
import org.esn_spain.helper.TagHandler;
import org.esn_spain.model.web.Partner;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

public class PartnerView extends BindableLayout<Partner> {

    @Bind(R.id.partner_image)    ImageView image;
    @Bind(R.id.partner_title)    TextView title;
    //@Bind(R.id.partner_subtitle) TextView subtitle;
    @Bind(R.id.partner_text)     TextView text;
    //@Bind(R.id.partner_location) Button location;
    //@Bind(R.id.partner_share)    Button       share;
    @Bind(R.id.partner_offers) LinearLayout offers;

    public PartnerView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_partner_card;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
    }

    @Override
    public void bind(final Partner item) {
        //subtitle.setTextColor(C.COLORS[1]);
        title.setText(Html.fromHtml(item.partner_name));
        text.setText(Html.fromHtml(item.partner_offers, null, new TagHandler()));
        //subtitle.setText(item.address);

        /*if (item.body != null) {
            text.setText(Html.fromHtml(item.body));
            text.setLinksClickable(true);
            text.setVisibility(View.VISIBLE);
        }
        else text.setVisibility(View.GONE);*/

        /*if (item.color != null) {
            int color = Color.parseColor(item.color);
            //share.setTextColor(color);
            //location.setTextColor(color);
        }*/

        Glide.with(getContext()).load(item.partner_image).into(image);

        /*if (item.offers != null && item.offers.size() > 0) {
            offers.setVisibility(View.VISIBLE);
            offers.removeAllViews();
            View view;
            for (String offer: item.offers) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_partner_offer, offers, false);
                TextView textView = (TextView) view.findViewById(R.id.partner_offer);
                textView.setText(Html.fromHtml(offer));
                textView.setLinksClickable(true);
                offers.addView(view);
            }
        }
        else offers.setVisibility(View.GONE);
        */


        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT,
                        getResources().getString(R.string.discounts_share_text) + " " + item.name + " " + item.link);
                i.setType("text/plain");
                getContext().startActivity(Intent.createChooser(i,
                        getResources().getString(R.string.discounts_share_summary) + " " + item.name));
            }
        });

        if (item.searchmap != null && item.searchmap.toUpperCase().equals("NO")) location.setVisibility(View.GONE);
        else {
            location.setVisibility(View.VISIBLE);
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (item.searchmap == null)
                        i.setData(Uri.parse("geo:0,0?q=" + item.address + "+" + "Murcia" + " (" + item.name + ")"));
                    else
                        i.setData(Uri.parse("geo:0,0?q=" + item.searchmap));
                    getContext().startActivity(i);
                }
            });
        }
        */

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}

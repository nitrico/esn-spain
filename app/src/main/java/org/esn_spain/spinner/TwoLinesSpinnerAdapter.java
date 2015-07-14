package org.esn_spain.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.esn_spain.R;
import java.util.List;

public class TwoLinesSpinnerAdapter extends ArrayAdapter<TwoLinesSpinnerItem> {

    private List<TwoLinesSpinnerItem> mItems;
    private Context mContext;

    public TwoLinesSpinnerAdapter(Context context, int resource, List<TwoLinesSpinnerItem> items) {
        super(context, resource, items);
        mContext = context;
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //@SuppressLint("ViewHolder")
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_two_lines, parent, false);

        TextView line1 = (TextView) view.findViewById(R.id.line1);
        TextView line2 = (TextView) view.findViewById(R.id.line2);

        //line1.setTextColor(0xFFFFFFFF);
        //line2.setTextColor(0xBBFFFFFF);

        line1.setText(mItems.get(position).getLine1());
        line2.setText(mItems.get(position).getLine2());

        if (mItems.get(position).getLine2().equals("")) {
            //line2.setVisibility(View.GONE);
            line2.setText("Todas las secciones");
        }
        //else line2.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_two_lines, parent, false);

        TextView line1 = (TextView) view.findViewById(R.id.line1);
        TextView line2 = (TextView) view.findViewById(R.id.line2);

        line1.setText(mItems.get(position).getLine1());
        line2.setText(mItems.get(position).getLine2());

        if (mItems.get(position).getLine2().equals("")) {
            line2.setText("Todas las secciones");
            //line2.setVisibility(View.GONE);
            //view.setBackgroundColor(0x14000000);
        }
        else {
            //line2.setVisibility(View.VISIBLE);
            //view.setBackgroundColor(0x08000000);
        }

        return view;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int findPositionById(String id) {
        for (int i=0; i< mItems.size(); i++) {
            if (mItems.get(i).getSectionId().toUpperCase().equals(id.toUpperCase())) return i;
        }
        return -1;
    }

}

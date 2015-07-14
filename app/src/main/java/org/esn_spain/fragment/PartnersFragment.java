package org.esn_spain.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.esn_spain.R;
import org.esn_spain.activity.MainActivity;
import org.esn_spain.helper.Utils;
import org.esn_spain.model.view.PartnerView;
import org.esn_spain.model.web.Partner;
import org.esn_spain.model.web.Partners;
import org.esn_spain.network.DataManager;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;

public class PartnersFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        DataManager.PartnersListener {

    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    private RecyclerMultiAdapter mAdapter;

    public PartnersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_green, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Activity activity = getActivity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setLayoutManager(Utils.isPortrait(activity)
                ? new LinearLayoutManager(activity)
                : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = SmartAdapter.empty()
                .map(Partner.class, PartnerView.class)
                .into(mRecyclerView);

        onRefresh();
        return view;
    }

    @Override
    public void onPartnersLoaded(Partners items) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);

        List<Partner> mItems = items.get();
        if (mAdapter != null) {
            mAdapter.clearItems();
            mAdapter.addItems(mItems);
        }
    }

    @Override
    public void onRefresh() {
        MainActivity activity = (MainActivity) getActivity();
        DataManager.from(activity).loadPartners(MainActivity.mSectionUrl + "/partners/xml", this);
    }

}

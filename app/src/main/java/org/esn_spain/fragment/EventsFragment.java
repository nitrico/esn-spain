package org.esn_spain.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.esn_spain.R;
import org.esn_spain.activity.MainActivity;
import org.esn_spain.helper.Utils;
import org.esn_spain.model.simple.Header;
import org.esn_spain.model.view.EventView;
import org.esn_spain.model.view.HeaderView;
import org.esn_spain.model.web.Event;
import org.esn_spain.model.web.Events;
import org.esn_spain.network.DataManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;

public class EventsFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        DataManager.EventsListener {

    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    private RecyclerMultiAdapter mAdapter;

    public EventsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_cyan, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.cyan, R.color.blue, R.color.cyan);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Activity activity = getActivity();
        mRecyclerView.setLayoutManager(Utils.isPortrait(activity)
                ? new LinearLayoutManager(activity)
                : new GridLayoutManager(activity, 2));

        mAdapter = SmartAdapter.empty()
                .map(Event.class, EventView.class)
                .map(Header.class, HeaderView.class)
                .into(mRecyclerView);

        onRefresh();
        return view;
    }

    @Override
    public void onEventsLoaded(Events events) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);

        List<Object> mItems = new ArrayList<>();
        mItems.add(new Header("Próximos eventos"));
        mItems.addAll(events.getNext());
        mItems.add(new Header("Eventos pasados"));
        mItems.addAll(events.getPast());

        if (mAdapter != null) {
            mAdapter.clearItems();
            mAdapter.addItems(mItems);
        }
    }

    @Override
    public void onRefresh() {
        MainActivity activity = (MainActivity) getActivity();
        DataManager.from(activity).loadEvents(MainActivity.mSectionUrl +"/events/xml", this);
    }

}

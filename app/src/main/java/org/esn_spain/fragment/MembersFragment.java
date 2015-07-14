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
import org.esn_spain.model.view.MemberView;
import org.esn_spain.model.web.Member;
import org.esn_spain.model.web.Members;
import org.esn_spain.network.DataManager;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;

public class MembersFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        DataManager.MembersListener {

    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    private RecyclerMultiAdapter mAdapter;

    public MembersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_pink, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.pink, R.color.blue, R.color.pink);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Activity activity = getActivity();
        mRecyclerView.setLayoutManager(Utils.isPortrait(activity)
                ? new LinearLayoutManager(activity)
                : new GridLayoutManager(activity, 2));

        mAdapter = SmartAdapter.empty()
                .map(Member.class, MemberView.class)
                .into(mRecyclerView);

        onRefresh();
        return view;
    }

    @Override
    public void onMembersLoaded(Members items) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);

        List<Member> mItems = items.get();
        if (mAdapter != null) {
            mAdapter.clearItems();
            mAdapter.addItems(mItems);
        }
    }

    @Override
    public void onRefresh() {
        MainActivity activity = (MainActivity) getActivity();
        DataManager.from(activity).loadMembers(MainActivity.mSectionUrl + "/members/xml", this);
    }

}

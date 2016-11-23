package com.bobomee.blogdemos.tab_head_list_vp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.bobomee.android.common.util.LogUtil;
import com.bobomee.blogdemos.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bobomee on 16/3/9.
 */
public class HostFragment extends BaseTabFragmentHolder {
    ViewPager viewPager;
    ListPagerAdapter adapter;

    @Override
    public View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        List<String> types = new ArrayList<>();

        types.add("types1");
        types.add("types2");
        types.add("types3");

        viewPager.setAdapter(adapter = new ListPagerAdapter(getChildFragmentManager(), types, this));

        viewPager.setOnPageChangeListener(this);


    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        BaseTabFragmentHolder item = (BaseTabFragmentHolder) adapter.getTabFragmentHolder(position);

        if (null != item) {
            item.onPageSelected(position);

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

        LogUtil.e(getScrollY(view) + "");

//        ToastUtil.show(baseA, "this :" + this + "view :" + view + ",firstVisibleItem :" + firstVisibleItem + ",visibleItemCount :" + visibleItemCount + ",totalItemCount :" + totalItemCount);
    }

    public int getScrollY(AbsListView mListView) {

        int padding = mListView.getPaddingTop();

        View c = mListView.getChildAt(0);

        if (c == null) {

            return 0;

        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();

        int top = c.getTop();

        return -top + firstVisiblePosition * c.getHeight() + padding;

    }
}

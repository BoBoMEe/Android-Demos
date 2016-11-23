package com.bobomee.blogdemos.tab_head_list_vp;

import android.widget.AbsListView;

import com.bobomee.blogdemos.base.BaseFragment;

/**
 * Created by bobomee on 16/3/9.
 */
public abstract class BaseTabFragmentHolder extends BaseFragment implements TabFragmentHolder {

    public TabFragmentHolder tabFragmentHolder;

    public void setTabFragmentHolder(TabFragmentHolder holder) {
        this.tabFragmentHolder = holder;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}

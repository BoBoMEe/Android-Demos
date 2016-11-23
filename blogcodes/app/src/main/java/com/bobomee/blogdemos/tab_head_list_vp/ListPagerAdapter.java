package com.bobomee.blogdemos.tab_head_list_vp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by bobomee on 16/3/9.
 */
public class ListPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTypes;

    private TabFragmentHolder tabFragmentHolder;

    private SparseArray<TabFragmentHolder> tabFragmentHolderSparseArray = new SparseArray<>();

    public ListPagerAdapter(FragmentManager fm, List<String> types, TabFragmentHolder tabFragmentHolder) {
        super(fm);
        this.mTypes = types;
        this.tabFragmentHolder = tabFragmentHolder;
    }

    @Override
    public Fragment getItem(int position) {
        BaseTabFragmentHolder tabFragment = FillFragment.newInstance(mTypes.get(position));
        tabFragment.setTabFragmentHolder(tabFragmentHolder);
        tabFragmentHolderSparseArray.append(position,tabFragment);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return mTypes.size();
    }

    public TabFragmentHolder getTabFragmentHolder(int position){
        return tabFragmentHolderSparseArray.get(position);
    }
}

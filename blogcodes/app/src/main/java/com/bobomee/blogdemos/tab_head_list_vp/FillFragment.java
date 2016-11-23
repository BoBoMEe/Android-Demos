package com.bobomee.blogdemos.tab_head_list_vp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bobomee.blogdemos.R;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bobomee on 16/3/9.
 */
public class FillFragment extends BaseTabFragmentHolder {

    ListView listView;
    String mType;

    List<String> mData = new ArrayList<>();

    public static FillFragment newInstance(String mType) {
        FillFragment fillFragment = new FillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", mType);
        fillFragment.setArguments(bundle);
        return fillFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString("type");

        for (int i = 0; i < 100; ++i) {
            mData.add("this type is " + mType + " ,this position is" + i);
        }
    }

    @Override
    public View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fill_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(android.R.id.list);

        listView.setAdapter(new QuickAdapter<String>(baseA, android.R.layout.simple_list_item_1, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        });

        listView.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        if (null != tabFragmentHolder){
            tabFragmentHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }


    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
//        ToastUtil.show(baseA, "this :" + this + "position :" + position);
    }
}

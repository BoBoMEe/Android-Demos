package com.bobomee.blogdemos.tab_head_list_vp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bobomee.blogdemos.base.BaseActivity;

/**
 * Created by bobomee on 16/3/9.
 */
public class ClipViewPagerListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HostFragment hostFragment = (HostFragment) Fragment.instantiate(this, HostFragment.class.getName());
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, hostFragment).commitAllowingStateLoss();
    }
}

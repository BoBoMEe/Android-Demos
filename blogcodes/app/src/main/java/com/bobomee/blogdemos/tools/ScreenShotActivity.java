package com.bobomee.blogdemos.tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;

/**
 * @author：BoBoMEe Created at 2016/1/5.
 */
public class ScreenShotActivity extends BaseActivity {

    private android.widget.Button shotlist;
    private android.widget.Button shotrecycler;
    private android.widget.Button shotscroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_shot_layout);
        ButterKnife.bind(this);
        this.shotscroll = (Button) findViewById(R.id.shot_scroll);
        this.shotrecycler = (Button) findViewById(R.id.shot_recycler);
        this.shotlist = (Button) findViewById(R.id.shot_list);
    }

    @OnClick(R.id.shot_scroll) void scroll() {
        start(ScrollViwShotActivity.class);
    }

    @OnClick(R.id.shot_list) void list() {
        start(ListViewShotActivity.class);
    }

    @OnClick(R.id.shot_recycler) void onClick() {
        start(RecyclerViewShotActivity.class);
    }

    private void start(Class a) {
        Intent intent = new Intent(this, a);
        startActivity(intent);
    }
}

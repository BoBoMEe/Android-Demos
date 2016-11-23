package com.bobomee.blogdemos.view.switcher3d;

import android.os.Bundle;

import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;


/**
 * Created by bobomee on 16/3/5.
 */
public class Image3DSwitchActivity extends BaseActivity {
    Image3DSwitchView image3DSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switcher_3d_layout);
        image3DSwitchView = (Image3DSwitchView) findViewById(R.id.image_switch_view);
        image3DSwitchView.startAutoScroll();
    }
}

package com.bobomee.android.retrofit2demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bobomee.android.retrofit2demo.R;
import com.bobomee.android.retrofit2demo.ui.fragment.SimpleGetFragment;
import com.bobomee.android.retrofit2demo.ui.fragment.SimplePostFragmeent;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.simple_get)
    public void simple_get() {
        attachFragment(new SimpleGetFragment());
    }

    @OnClick(R.id.simple_post)
    public void simple_post() {
        attachFragment(new SimplePostFragmeent());
    }


    private void attachFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.root, fragment)
                .commitAllowingStateLoss();

    }


}



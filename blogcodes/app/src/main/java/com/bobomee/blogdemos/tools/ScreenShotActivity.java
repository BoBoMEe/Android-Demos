package com.bobomee.blogdemos.tools;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bobomee.android.common.util.ScreenUtil;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;


/**
 * @author：BoBoMEe Created at 2016/1/5.
 */
public class ScreenShotActivity extends BaseActivity {

    private ScrollView root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_shot_layout);
        root = (ScrollView) findViewById(R.id.root);

    }

    public void click(View v){
        Bitmap bitmap = ScreenUtil.getViewBitmap(ScreenShotActivity.this, root);
        showDialog(bitmap);
    }

    private void showDialog(Bitmap b){
        ImageView v = new ImageView(ScreenShotActivity.this);
        v.setImageBitmap(b);
        AlertDialog.Builder builder = new AlertDialog.Builder(ScreenShotActivity.this).setView(v);
        builder.create().show();
    }

}

package com.bobomee.blogdemos.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bobomee.android.colorstatus.StatusBarUtils;
import com.bobomee.android.common.util.UIUtil;
import com.bobomee.blogdemos.IConstant;
import com.bobomee.blogdemos.R;


/**
 * @author：BoBoMEe Created at 2016/1/4.
 */
public class BaseActivity extends AppCompatActivity implements IConstant {

    private Unbinder mBind;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mBind = ButterKnife.bind(this);
        setStatusBarColor();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mBind = ButterKnife.bind(this);
        setStatusBarColor();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mBind = ButterKnife.bind(this);
        setStatusBarColor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mBind)mBind.unbind();
        //StatusBarUtils.instance(this).clear();
    }

    public void setStatusBarColor(){
        StatusBarUtils.instance(this).setColor(UIUtil.getColor(R.color.colorAccent)).setStyle(StatusBarUtils.TYPE.FILL).init();
    }

}

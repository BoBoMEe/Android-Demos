package com.bobomee.android.androidanimations;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 16/9/7.下午11:04.
 *
 * @author bobomee.
 * @description:
 */
public class BaseActivity
extends AppCompatActivity{
  protected BaseActivity mActivity;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = this;

  }

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    setupToolbar();
  }

  @Override public void setContentView(View view) {
    super.setContentView(view);
    setupToolbar();
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    setupToolbar();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
          fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
          onBackPressed();
        }
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void setupToolbar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    ActivityCompat.finishAfterTransition(this);
  }

  @Override public void overridePendingTransition(int enterAnim, int exitAnim) {
    super.overridePendingTransition(enterAnim, exitAnim);
  }
}

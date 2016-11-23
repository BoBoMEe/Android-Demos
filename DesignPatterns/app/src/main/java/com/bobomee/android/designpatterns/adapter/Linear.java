package com.bobomee.android.designpatterns.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created on 16/8/6.下午6:02.
 *
 * @author bobomee.
 * @description:
 */
public class Linear extends LinearLayout {
  private ItemAdapter mItemAdapter;

  private DataSetObserver mDataSetObserver = new DataSetObserver() {
    @Override public void onChanged() {
      super.onChanged();

      onChange();
    }

    @Override public void onInvalidated() {
      super.onInvalidated();
    }
  };

  private void onChange() {

    if (null == mItemAdapter) return;
    int count = mItemAdapter.getCount();

    for (int i = 0; i < count; ++i) {

      View view = mItemAdapter.getView(this, i, mItemAdapter.getItem(i));

      addView(view);
    }
  }

  public Linear(Context context) {
    this(context, null);
  }

  public Linear(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Linear(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public Linear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setAdapter(ItemAdapter itemAdapter) {
    if (mItemAdapter != null && mDataSetObserver != null) {
      mItemAdapter.unregisterDataSetObserver(mDataSetObserver);
    }
    this.mItemAdapter = itemAdapter;
    if (null != mItemAdapter) {
      mItemAdapter.registerDataSetObserver(mDataSetObserver);
      mItemAdapter.notifyDataSetChanged();
    }
  }
}

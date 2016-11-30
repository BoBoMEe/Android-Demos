package com.bobomee.android.annotationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.bobomee.android.layout.Layout;
import com.bobomee.android.layout.LayoutBinder;

@Layout(R.layout.layout_view) public class MainView extends LinearLayout {
  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutBinder.bind(this);
  }
}

package com.bobomee.blogdemos.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;

/**
 * Created on 16/8/20.下午5:45.
 *
 * @author bobomee.
 * @description: ScrollTo || ScrollBy
 */
public class View_st_sb extends BaseActivity {

  @BindView(R.id.btn_scroll_to) Button mBtnScrollTo;
  @BindView(R.id.btn_scroll_by) Button mBtnScrollBy;
  @BindView(R.id.scroll_layout) View mScrollLayout;

  private int DISTANCE_X = -50;
  private int DISTANCE_Y = -50;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.view_scrollto_scrollby_layout);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.btn_scroll_to, R.id.btn_scroll_by }) public void setBtnScroll(View view) {
    switch (view.getId()) {
      case R.id.btn_scroll_to:
        mScrollLayout.scrollTo(DISTANCE_X, DISTANCE_Y);
        break;
      case R.id.btn_scroll_by:
        mScrollLayout.scrollBy(DISTANCE_X, DISTANCE_Y);
        break;
    }
  }
}

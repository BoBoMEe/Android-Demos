package com.bobomee.blogdemos.recycler.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.bobomee.blogdemos.R;

/**
 * Created on 16/7/23.下午3:23.
 *
 * @author bobomee.
 * @description:
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
  private int margin;

  public MarginDecoration(Context context) {
    margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.set(margin, margin, margin, margin);
  }
}

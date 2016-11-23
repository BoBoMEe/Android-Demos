package com.bobomee.blogdemos.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bobomee.blogdemos.R;

/**
 * Created on 16/7/23.下午3:25.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerViewholder extends RecyclerView.ViewHolder {

  public TextView textView;

  public RecyclerViewholder(View itemView) {
    super(itemView);
    textView = (TextView) itemView.findViewById(R.id.text);
  }
}

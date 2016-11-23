package com.bobomee.blogdemos.recycler.adapter;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.RecyclerViewholder;
import java.util.List;

/**
 * Created on 16/7/23.下午3:25.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerStaggeredAdapter extends BaseRecyclerAdapter {

  public RecyclerStaggeredAdapter(List<String> datas) {
    super(datas);
  }

  @Override public void onBindViewHolder(RecyclerViewholder holder, int position) {

    holder.textView.setText(datas.get(position));

    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();

    int h = position * 10 + 100;

    layoutParams.height = h;

    if (isSelected(position)) {
      holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.c));
    }
  }
}

package com.bobomee.blogdemos.recycler.adapter;

import android.view.View;
import android.view.ViewGroup;
import com.bobomee.blogdemos.recycler.RecyclerViewholder;
import java.util.List;

/**
 * Created on 16/7/23.下午3:25.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerWithHeaderAdapter extends BaseRecyclerAdapter {

  private final View header;

  private static final int ITEM_VIEW_TYPE_HEADER = 0;
  private static final int ITEM_VIEW_TYPE_ITEM = 1;

  public boolean isHeader(int position) {
    return position == 0;
  }

  @Override public int getItemViewType(int position) {
    return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
  }

  public RecyclerWithHeaderAdapter(List<String> datas, View header) {
    super(datas);
    this.header = header;
  }

  @Override public RecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

    if (viewType == ITEM_VIEW_TYPE_HEADER) {
      return new RecyclerViewholder(header);
    }

    return super.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(RecyclerViewholder holder, int position) {
    if (isHeader(position)) return;

    holder.textView.setText(datas.get(position - 1));
  }

  @Override public int getItemCount() {
    return datas.size() + 1;
  }
}

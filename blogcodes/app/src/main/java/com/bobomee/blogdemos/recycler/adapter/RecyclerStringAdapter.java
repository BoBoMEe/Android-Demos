package com.bobomee.blogdemos.recycler.adapter;

import com.bobomee.blogdemos.recycler.RecyclerViewholder;
import java.util.List;

/**
 * Created on 16/7/23.下午3:25.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerStringAdapter extends BaseRecyclerAdapter {

  public RecyclerStringAdapter(List<String> datas) {
    super(datas);
  }

  @Override public void onBindViewHolder(RecyclerViewholder holder, int position) {
    holder.textView.setText(datas.get(position));
  }
}

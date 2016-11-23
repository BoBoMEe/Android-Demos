package com.bobomee.blogdemos.recycler.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.RecyclerViewholder;

/**
 * Created on 16/7/23.下午3:25.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerViewholder> {

  private final Datas.Item[] datas;

  public RecyclerMainAdapter(Datas.Item[] datas) {
    this.datas = datas;
  }

  @Override public RecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

    return new RecyclerViewholder(view);
  }

  @Override public void onBindViewHolder(RecyclerViewholder holder, int position) {

    holder.textView.setText(datas[position].title);
  }

  @Override public int getItemCount() {
    return datas.length;
  }
}

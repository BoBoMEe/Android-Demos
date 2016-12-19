/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.designpatterns.expand;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bobomee.android.designpatterns.R;
import com.bobomee.android.recyclerviewhelper.expandable.TreeNode;
import com.bobomee.android.recyclerviewhelper.expandable.TreeViewBinder;

/**
 * Created on 2016/12/19.上午9:51.
 *
 * @author bobomee.
 */

public class ItemBinder extends TreeViewBinder<ItemBinder.ViewHolder> {

  @Override public ViewHolder provideViewHolder(View itemView) {
    return new ViewHolder(itemView);
  }

  @Override public void bindView(ViewHolder holder, int position, TreeNode node) {

    Item folder = (Item) node.getContent();
    holder.mTextView.setText(folder.getName());
    int height = node.getHeight();//获取高度

    holder.itemView.setPadding(60 * height, 3, 3, 3);//设置高度，方便看出层级
  }

  @Override public int getLayoutId() {
    return R.layout.child_layout;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;

    public ViewHolder(View itemView) {
      super(itemView);

      mTextView = (TextView) itemView.findViewById(android.R.id.text1);
    }
  }
}

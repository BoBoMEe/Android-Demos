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

import com.bobomee.android.designpatterns.R;
import com.bobomee.android.recyclerviewhelper.expandable.interfaces.LayoutItemType;

/**
 * Created on 2016/12/19.上午9:48.
 *
 * @author bobomee.
 */

public class Folder implements LayoutItemType {
  @Override public int getLayoutId() {
    return R.layout.parent_layout_item;
  }

  private String name;

  public String getName() {
    return name;
  }

  public Folder(String _name) {


    name = _name;
  }
}

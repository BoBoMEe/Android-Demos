/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
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

package com.bobomee.blogdemos.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bobomee.android.common.util.ViewFindUtils;
import com.bobomee.blogdemos.R;

/**
 * Created on 2017/1/18.下午11:53.
 *
 * @author bobomee.
 */

public class Utils {

  public static void showDialog(Context pContext, Bitmap pBitmap) {

    LayoutInflater inflater = LayoutInflater.from(pContext);
    View view = inflater.inflate(R.layout.screen_shot_alert_show, null, false);
    ImageView lImageView = ViewFindUtils.find(view, R.id.image);
    lImageView.setImageBitmap(pBitmap);

    new AlertDialog.Builder(pContext).setView(view).show();
  }
}

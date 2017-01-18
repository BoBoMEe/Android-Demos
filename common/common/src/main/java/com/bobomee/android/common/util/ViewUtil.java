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

package com.bobomee.android.common.util;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created on 2017/1/18.上午11:33.
 *
 * @author bobomee.
 */

public class ViewUtil {

  public static boolean canScrollUp(View pView) {
    return ViewCompat.canScrollVertically(pView, 1);
  }

  public static boolean canScrollDown(View pView) {
    return ViewCompat.canScrollVertically(pView, -1);
  }

  public static boolean canScrollLeft(View pView) {
    return ViewCompat.canScrollHorizontally(pView, 1);
  }

  public static boolean canScrollRight(View pView) {
    return ViewCompat.canScrollHorizontally(pView, -1);
  }
}

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

package com.bobomee.android.common.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import java.util.List;

public class BackHandlerHelper {

  /**
   * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
   * 没有处理back事件，则尝试 FragmentManager.popBackStack()
   *
   * @return 如果处理了back键则返回 <b>true</b>
   * @see #handleBackPress(Fragment)
   * @see #handleBackPress(FragmentActivity)
   */
  public static boolean handleBackPress(FragmentManager fragmentManager) {
    List<Fragment> fragments = fragmentManager.getFragments();

    if (fragments == null) return false;

    for (int i = fragments.size() - 1; i >= 0; i--) {
      Fragment child = fragments.get(i);

      if (isFragmentBackHandled(child)) {
        return true;
      }
    }

    if (fragmentManager.getBackStackEntryCount() > 0) {
      fragmentManager.popBackStack();
      return true;
    }
    return false;
  }

  /**
   * 将back事件分发给Fragment中的子Fragment,
   * 该方法调用了 {@link #handleBackPress(FragmentManager)}
   *
   * @return 如果处理了back键则返回 <b>true</b>
   */
  public static boolean handleBackPress(Fragment fragment) {
    return handleBackPress(fragment.getChildFragmentManager());
  }

  /**
   * 将back事件分发给Activity中的子Fragment,
   * 该方法调用了 {@link #handleBackPress(FragmentManager)}
   *
   * @return 如果处理了back键则返回 <b>true</b>
   */
  public static boolean handleBackPress(FragmentActivity fragmentActivity) {
    return handleBackPress(fragmentActivity.getSupportFragmentManager());
  }

  /**
   * 判断Fragment是否处理了Back键
   *
   * @return 如果处理了back键则返回 <b>true</b>
   */
  public static boolean isFragmentBackHandled(Fragment fragment) {
    return fragment != null
        && fragment.isVisible()
        && fragment.getUserVisibleHint()
        //for ViewPager
        && fragment instanceof FragmentBackHandler
        && ((FragmentBackHandler) fragment).onBackPressed();
  }
}

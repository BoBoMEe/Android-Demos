package com.bobomee.android.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 2016/11/23.下午8:43.
 *
 * @author bobomee.
 */

public class LayoutBinder {
  /**
   * 根据类型获取 layout 文件
   */
  public static Integer getLayout(Class clazz) {
    return Mapper.getLayout(clazz);
  }

  public static void bind(Activity activity) {
    Integer layout = getLayout(activity.getClass());
    if (layout != null) {
      activity.setContentView(layout);
    }
  }

  public static View inflate(Fragment fragment, ViewGroup container, boolean attachToRoot) {
    return inflate(fragment.getActivity(), fragment, container, attachToRoot);
  }

  public static View inflate(Fragment fragment, ViewGroup container) {
    return inflate(fragment, container, container != null);
  }

  public static View inflate(Fragment fragment) {
    return inflate(fragment, null);
  }

  public static View inflate(android.support.v4.app.Fragment fragment, ViewGroup container,
      boolean attachToRoot) {
    return inflate(fragment.getActivity(), fragment, container, attachToRoot);
  }

  public static View inflate(android.support.v4.app.Fragment fragment, ViewGroup container) {
    return inflate(fragment, container, container != null);
  }

  public static View inflate(android.support.v4.app.Fragment fragment) {
    return inflate(fragment, null);
  }

  public static View inflate(Context context, Object source, ViewGroup container,
      boolean attachToRoot) {
    Integer layout = getLayout(source.getClass());
    if (layout == null) {
      return null;
    }
    return LayoutInflater.from(context).inflate(layout, container, attachToRoot);
  }

  public static View inflate(Context context, Object source, ViewGroup container) {
    return inflate(context, source, container, container != null);
  }

  public static View inflate(Context context, Object source) {
    return inflate(context, source, null);
  }

  public static View inflate(View view, ViewGroup container, boolean attachToRoot) {
    return inflate(view.getContext(), view, container, attachToRoot);
  }

  public static View inflate(View view, ViewGroup container) {
    return inflate(view, container, container != null);
  }

  public static View inflate(View view) {
    return inflate(view, null);
  }

  public static void bind(ViewGroup view) {
    // TODO
    // 获取 view
    // 获取 children
    // 拷贝 root view 属性
    inflate(view, view, true);
  }
}

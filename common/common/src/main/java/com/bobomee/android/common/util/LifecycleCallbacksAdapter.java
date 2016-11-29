package com.bobomee.android.common.util;

/**
 * Created on 2016/11/16.下午1:44.
 *
 * @author bobomee.
 * @description
 */

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/** Helper to avoid implementing all lifecycle callback methods. */
public class LifecycleCallbacksAdapter implements Application.ActivityLifecycleCallbacks {
  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void onActivityStarted(Activity activity) {

  }

  @Override public void onActivityResumed(Activity activity) {

  }

  @Override public void onActivityPaused(Activity activity) {

  }

  @Override public void onActivityStopped(Activity activity) {

  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override public void onActivityDestroyed(Activity activity) {

  }
}

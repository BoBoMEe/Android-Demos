package com.bobomee.android.designpatterns.state;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 16/8/17.下午10:25.
 *
 * @author bobomee.
 * @description:
 */
public class LogoutState implements UserState {
  @Override public void doAction(Context context) {
    Toast.makeText(context, "您还没有登录", Toast.LENGTH_SHORT).show();
  }
}

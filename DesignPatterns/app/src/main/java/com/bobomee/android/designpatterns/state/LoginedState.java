package com.bobomee.android.designpatterns.state;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 16/8/17.下午10:24.
 *
 * @author bobomee.
 * @description:
 */
public class LoginedState implements UserState {
  @Override public void doAction(Context context) {
    Toast.makeText(context, "登录状态", Toast.LENGTH_SHORT).show();
  }
}

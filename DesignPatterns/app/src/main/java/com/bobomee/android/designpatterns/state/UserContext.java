package com.bobomee.android.designpatterns.state;

import android.content.Context;

/**
 * Created on 16/8/17.下午10:26.
 *
 * @author bobomee.
 * @description:
 */
public class UserContext {

  // 默认未登录状态
  UserState mUserState = new LoginedState();

  static UserContext sUserContext = new UserContext();

  private UserContext() {
  }

  public static UserContext getUserContext() {
    return sUserContext;
  }

  public void setUserState(UserState userState) {
    mUserState = userState;
  }

  public void doAction(Context context){
    mUserState.doAction(context);
  }

  public UserState getUserState() {
    return mUserState;
  }
}

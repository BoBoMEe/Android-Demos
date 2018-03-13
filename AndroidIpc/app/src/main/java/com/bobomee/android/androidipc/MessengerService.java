package com.bobomee.android.androidipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * @author BoBoMEe
 * @since 2018/3/11
 */

public class MessengerService extends Service {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  private static final String TAG = "MessengerService";

  private static final class MessengerHandler extends Handler {
    @Override public void handleMessage(Message msg) {

      switch (msg.what) {
        case Constants.MSG_FROM_CLIENT:

          break;
        default:
          super.handleMessage(msg);
          break;
      }
    }
  }
}

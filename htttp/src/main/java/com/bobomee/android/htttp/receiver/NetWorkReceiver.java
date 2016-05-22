package com.bobomee.android.htttp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bobomee.android.htttp.util.HttpNetUtil;


/**
 * Created by bobomee on 2016/5/18.
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        HttpNetUtil.INSTANCE.setConnected(context);
    }
}

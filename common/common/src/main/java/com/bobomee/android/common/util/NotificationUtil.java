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

package com.bobomee.android.common.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

public class NotificationUtil {

    /**
     * 初始化通知服务
     */
    public static NotificationManagerCompat initService(Context mContext) {
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(mContext);
        return mNotificationManager;
    }

    public static NotificationCompat.Builder invalidate(NotificationManagerCompat mNotificationManager,
                                                        NotificationCompat.Builder mBuilder, int id) {
        mNotificationManager.notify(id, mBuilder.build());
        return mBuilder;
    }

    /**
     * 清除当前创建的通知栏
     */
    public static void clearNotify(NotificationManagerCompat mNotificationManager,
                                   int notifyId) {
        mNotificationManager.cancel(notifyId);// 删除一个特定的通知ID对应的通知
    }

    /**
     * 清除所有通知栏
     */
    public static void clearAllNotify(NotificationManagerCompat mNotificationManager) {
        mNotificationManager.cancelAll();// 删除你发的所有通知
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除：Notification.FLAG_AUTO_CANCEL
     */
    private static PendingIntent getDefalutIntent(Context context, int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
                new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 初始化通知栏
     */
    public static NotificationCompat.Builder initBuilder(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setContentIntent(getDefalutIntent(context, 0))
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)// ture，设置他为一个正在进行的通知。
        // 他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        // .setNumber(number)//显示数量
        // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
        // .setDefaults(Notification.DEFAULT_VIBRATE)//requires VIBRATE permission
        // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音
        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性.
        ;
        return mBuilder;
    }

    public static NotificationCompat.Builder setNotify(NotificationCompat.Builder mBuilder,
                                                       String title, int iconId,
                                                       String content, String ticker) {
        setContentTitle(mBuilder, title);
        setSmallIcon(mBuilder, iconId);
        setContentText(mBuilder, content);
        setTicker(mBuilder, ticker);
        return mBuilder;
    }

    public static NotificationCompat.Builder setContentTitle(NotificationCompat.Builder mBuilder,
                                                             String title) {
        if (!TextUtils.isEmpty(title))
            mBuilder.setContentTitle(title);
        return mBuilder;
    }

    public static NotificationCompat.Builder setSmallIcon(NotificationCompat.Builder mBuilder,
                                                          int iconId) {
        //注意，不设置icon，notification 不显示
        mBuilder.setSmallIcon(iconId);
        return mBuilder;
    }

    public static NotificationCompat.Builder setContentText(NotificationCompat.Builder mBuilder,
                                                            String content) {
        if (!TextUtils.isEmpty(content))
            mBuilder.setContentText(content);
        return mBuilder;
    }

    public static NotificationCompat.Builder setTicker(NotificationCompat.Builder mBuilder,
                                                       String ticker) {
        if (!TextUtils.isEmpty(ticker))
            mBuilder.setTicker(ticker);
        return mBuilder;
    }

    public static NotificationCompat.Builder setActivityIntent(NotificationCompat.Builder mBuilder,
                                                               Context context, Intent intent) {
        // 点击的意图ACTION是跳转到Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        return mBuilder;
    }

    public static NotificationCompat.Builder setBroadCastIntent(NotificationCompat.Builder mBuilder,
                                                                Context context, Intent intent) {
        // 点击的意图ACTION是跳转到Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        return mBuilder;
    }
}

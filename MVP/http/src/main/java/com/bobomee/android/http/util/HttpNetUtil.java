/*
 *
 *    Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bobomee.android.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.bobomee.android.http.receiver.NetWorkReceiver;
import java.util.ArrayList;
import java.util.List;

public enum HttpNetUtil {

    INSTANCE;

    private List<Networkreceiver> networkreceivers;

    public interface Networkreceiver {

        void onConnected(boolean collect);
    }

    public void addNetWorkListener(Networkreceiver networkreceiver) {
        if (null == networkreceivers) {
            networkreceivers = new ArrayList<>();
        }
        networkreceivers.add(networkreceiver);
    }

    public void removeNetWorkListener(NetWorkReceiver listener) {
        if (networkreceivers != null) {
            networkreceivers.remove(listener);
        }
    }

    public void clearNetWorkListeners() {
        if (networkreceivers != null) {
            networkreceivers.clear();
        }
    }


    private boolean isConnected = true;

    /**
     * 获取是否连接
     */
    public boolean isConnected() {
        return isConnected;
    }

    private void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 判断网络连接是否存在
     *
     * @param context
     */
    public void setConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            setConnected(false);


            if (networkreceivers != null) {
                for (int i = 0, z = networkreceivers.size(); i < z; i++) {
                    Networkreceiver listener = networkreceivers.get(i);
                    if (listener != null) {
                        listener.onConnected(false);
                    }
                }
            }

        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        boolean connected = info != null && info.isConnected();
        setConnected(connected);

        if (networkreceivers != null) {
            for (int i = 0, z = networkreceivers.size(); i < z; i++) {
                Networkreceiver listener = networkreceivers.get(i);
                if (listener != null) {
                    listener.onConnected(connected);
                }
            }
        }

    }
}

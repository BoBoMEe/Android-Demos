package com.bobomee.android.designpatterns.flyweight;

import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 16/8/29.下午11:04.
 *
 * @author bobomee.
 * @description:
 */
public class TicketFactory {

  static Map<String, Ticket> sTicketMap = new ConcurrentHashMap<>();
  private static final String TAG = "TicketFactory";

  public static Ticket getTicket(String from, String to) {
    String key = from + "-" + to;

    if (sTicketMap.containsKey(key)) {
      Log.d(TAG, "getTicket: -->" + "使用缓存" + key);
      return sTicketMap.get(key);
    } else {
      Log.d(TAG, "getTicket: -->" + "创建对象" + key);
      Ticket ticket = new TrainTicket(from, to);
      sTicketMap.put(key, ticket);
      return ticket;
    }
  }
}

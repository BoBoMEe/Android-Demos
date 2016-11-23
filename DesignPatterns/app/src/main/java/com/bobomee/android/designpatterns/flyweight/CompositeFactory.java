package com.bobomee.android.designpatterns.flyweight;

import android.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 16/8/31.下午11:23.
 *
 * @author bobomee.
 * @description:
 */
public class CompositeFactory {

  private static final String TAG = "CompositeFactory";

  private Map<String, Ticket> mStringTicketMap = new HashMap<>();

  public Ticket factory(List<String> fromtos) {
    CompositeTicket concreteCompositeTicket = new CompositeTicket();

    for (String s : fromtos) {
      concreteCompositeTicket.add(s, this.factory(s));
    }

    return concreteCompositeTicket;
  }

  public Ticket factory(String _s) {
    Ticket ticket = mStringTicketMap.get(_s);
    if (null == ticket) {
      String[] split = _s.split("-");
      ticket = new TrainTicket(split[0], split[1]);
      mStringTicketMap.put(_s, ticket);
      Log.d(TAG, "getTicket: -->" + "创建对象" + _s);
    }else {
      Log.d(TAG, "getTicket: -->" + "使用缓存" + _s);
    }

    return ticket;
  }
}

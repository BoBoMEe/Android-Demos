package com.bobomee.android.designpatterns.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 16/8/31.下午11:17.
 *
 * @author bobomee.
 * @description:
 */
public class CompositeTicket implements Ticket {

  private Map<String, Ticket> mStringTicketMap = new HashMap<>();

  public void add(String fromto, Ticket _ticket) {
    mStringTicketMap.put(fromto, _ticket);
  }

  @Override public void showTicketInfo(String bunk) {
    Ticket ticket;
    for (String s : mStringTicketMap.keySet()) {
      ticket = mStringTicketMap.get(s);
      ticket.showTicketInfo(bunk);
    }
  }
}

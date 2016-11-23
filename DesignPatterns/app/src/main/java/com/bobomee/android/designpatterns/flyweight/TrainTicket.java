package com.bobomee.android.designpatterns.flyweight;

import android.util.Log;
import java.util.Random;

/**
 * Created on 16/8/29.下午10:59.
 *
 * @author bobomee.
 * @description:
 */
public class TrainTicket implements Ticket {

  public String from;//始发地
  public String to;//目的地

  public int price;//价钱

  private static final String TAG = "TrainTicket";

  public TrainTicket(String _from, String _to) {
    from = _from;
    to = _to;
  }

  @Override public void showTicketInfo(String bunk) {
    price = new Random().nextInt(300);
    Log.d(TAG, "showTicketInfo: -->"+ from + "--" + to + "--" + bunk + "--" + price);
  }
}

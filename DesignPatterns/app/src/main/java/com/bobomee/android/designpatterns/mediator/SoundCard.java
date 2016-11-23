package com.bobomee.android.designpatterns.mediator;

import android.util.Log;

/**
 * Created on 16/8/27.下午6:19.
 *
 * @author bobomee.
 * @description:
 */
public class SoundCard extends Colleague {
  private static final String TAG = "SoundCard";

  public SoundCard(Mediator _mediator) {
    super(_mediator);
  }

  public void soundPlay(String data) {
    Log.d(TAG, "soundPlay: -->" + data);
  }
}

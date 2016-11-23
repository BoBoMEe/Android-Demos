package com.bobomee.android.designpatterns.mediator;

/**
 * Created on 16/8/27.下午6:11.
 *
 * @author bobomee.
 * @description:
 */
public class MainBoard extends Mediator {
  private Cpu mCpu;
  private SoundCard mSoundCard;

  @Override public void changed(Colleague _colleague) {
    if (_colleague == mCpu) {
      handleCpu((Cpu) _colleague);
    }
  }

  private void handleCpu(Cpu _colleague) {
    mSoundCard.soundPlay(_colleague.getDataSound());
  }

  public void setCpu(Cpu _cpu) {
    mCpu = _cpu;
  }

  public void setSoundCard(SoundCard _soundCard) {
    mSoundCard = _soundCard;
  }
}

package com.bobomee.android.designpatterns.mediator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/27.下午6:28.
 *
 * @author bobomee.
 * @description:
 */
public class MediatorActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    MainBoard mainBoard = new MainBoard();
    Cpu cpu = new Cpu(mainBoard);
    SoundCard soundCard = new SoundCard(mainBoard);

    mainBoard.setCpu(cpu);
    mainBoard.setSoundCard(soundCard);

    cpu.load();
  }
}

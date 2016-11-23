package com.bobomee.android.designpatterns.state;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/17.下午10:30.
 *
 * @author bobomee.
 * @description:
 */
public class StateActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);

  }

  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    UserContext.getUserContext().doAction(StateActivity.this);
    UserState userState = UserContext.getUserContext().getUserState();
    boolean equals = userState.getClass().getName().equals(LoginedState.class.getName());
    UserContext.getUserContext().setUserState(equals ? new LogoutState() : new LoginedState());
  }
}

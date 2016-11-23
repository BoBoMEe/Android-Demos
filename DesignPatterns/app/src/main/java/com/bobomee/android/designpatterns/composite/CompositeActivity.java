package com.bobomee.android.designpatterns.composite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/28.上午11:37.
 *
 * @author bobomee.
 * @description:
 */
public class CompositeActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    Dir diskC = new Folder("C");
    diskC.addDir(new File("sss.text"));

    Dir dirW = new Folder("Windows");
    dirW.addDir(new File("explorer.exe"));
    Dir dirP = new Folder("PerfLogs");
    dirP.addDir(new File("bbb.text"));
    Dir dirF = new Folder("Program File");
    dirF.addDir(new File("ftp.text"));

    diskC.addDir(dirW);
    diskC.addDir(dirP);
    diskC.addDir(dirF);

    diskC.print();
  }
}

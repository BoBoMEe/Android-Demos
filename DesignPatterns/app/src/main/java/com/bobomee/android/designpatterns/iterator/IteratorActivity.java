package com.bobomee.android.designpatterns.iterator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/26.下午9:32.
 *
 * @author bobomee.
 * @description:
 */
public class IteratorActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    Aggregate<String> aggregate = new ConcreteAggregate<>();
    aggregate.add("Aige");
    aggregate.add("Studio");
    aggregate.add("SM");
    aggregate.add("Brother");
    aggregate.add("MM");

    Iterator<String> iterator = aggregate.iterator();

    while (iterator.hasNext()) {
      Toast.makeText(IteratorActivity.this, "it next-->" + iterator.next(), Toast.LENGTH_SHORT)
          .show();
    }
  }
}

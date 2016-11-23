package com.bobomee.android.designpatterns.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/6.下午6:19.
 *
 * @author bobomee.
 * @description:
 */
public class AdapterActivity extends AppCompatActivity {

  @BindView(R.id.linear) Linear mLinear;
  LayoutInflater mInflater;

  private String[] mVals = new String[]
      {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
          "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
          "Android", "Weclome Hello", "Button Text", "TextView"};


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.adapter_layout);
    ButterKnife.bind(this);
    mInflater = LayoutInflater.from(this);

    mLinear.setAdapter(new ItemAdapter<String>(mVals) {
      @Override protected View getView(ViewGroup parent, int position, String s) {
        View v =  mInflater.inflate(R.layout.adapter_item,
            parent, false);

        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText(s);
        return v;
      }
    });
  }
}

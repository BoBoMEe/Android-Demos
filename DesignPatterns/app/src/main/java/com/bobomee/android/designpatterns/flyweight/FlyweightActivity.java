package com.bobomee.android.designpatterns.flyweight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/29.下午11:08.
 *
 * @author bobomee.
 * @description:
 */
public class FlyweightActivity extends AppCompatActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    flyweight();

    //flyweight1();
  }

  private void flyweight1() {
    List<String> composite = new ArrayList<>();
    composite.add("北京-广州");
    composite.add("北京-深证");
    composite.add("北京-上海");

    CompositeFactory compositeFactory = new CompositeFactory();
    Ticket composite1 = compositeFactory.factory(composite);
    Ticket composite2 = compositeFactory.factory(composite);
    Ticket composite3 = compositeFactory.factory(composite);
    composite1.showTicketInfo("上铺");
    composite2.showTicketInfo("下铺");
    composite3.showTicketInfo("坐票");

    String fromto = "北京-广州";

    Ticket ticket1 = compositeFactory.factory(fromto);
    Ticket ticket2 = compositeFactory.factory(fromto);
    ticket1.showTicketInfo("上铺");
    ticket2.showTicketInfo("下铺");
  }

  private void flyweight() {
    Ticket ticket01 = TicketFactory.getTicket("北京","广州");
    ticket01.showTicketInfo("上铺");

    Ticket ticket02 = TicketFactory.getTicket("北京","广州");
    ticket02.showTicketInfo("下铺");

    Ticket ticket03 = TicketFactory.getTicket("北京","广州");
    ticket03.showTicketInfo("坐票");
  }
}

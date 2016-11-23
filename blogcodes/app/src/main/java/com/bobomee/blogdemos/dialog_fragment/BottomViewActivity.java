package com.bobomee.blogdemos.dialog_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;


/**
 * Created by bobomee on 16/1/11.
 */
public class BottomViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_view_layout);
    }

    public void pop(View view) {
        switch (view.getId()) {
            case R.id.dialog_fragment:

                //BottomDialogFragment bottomDialogFragment = (BottomDialogFragment) Fragment.instantiate(this, BottomDialogFragment.class.getName());
                //getSupportFragmentManager().beginTransaction().add(bottomDialogFragment, "bottomDialogFragment").commitAllowingStateLoss();

                BottomDialogFragment.showDialog(this);

                break;
            case R.id.dialog_bottomview:
//                new BottomSheet.Builder(this).title("title").sheet(R.menu.bottom_list).listener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case R.id.help:
//                                ToastUtil.show(BottomViewActivity.this, "Help me!");
//                                break;
//                        }
//                    }
//                }).show();
                break;
            case R.id.alert_dialog:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(R.layout.dialog_bottom_item);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }
}

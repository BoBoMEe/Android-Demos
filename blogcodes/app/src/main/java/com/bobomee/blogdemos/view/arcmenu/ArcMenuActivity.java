package com.bobomee.blogdemos.view.arcmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;

/**
 * @author：BoBoMEe Created at 2016/1/4.
 */
public class ArcMenuActivity extends BaseActivity {

    private ArcMenu mArcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arc_menu_layout);

        initViews();
    }

    private void initViews() {
        mArcMenu = (ArcMenu) findViewById(R.id.id_arcmenu);

        initArcMenu();
    }

    private void initArcMenu() {

        ImageView people = new ImageView(this);
        people.setImageResource(R.mipmap.composer_sleep);
        people.setTag("Sleep");
        mArcMenu.addView(people);

        mArcMenu.setOnMenuItemClickListener((view, pos) -> {
            Toast.makeText(this, view.getTag() + "; position :" + pos, Toast.LENGTH_LONG).show();
        });

        mArcMenu.setStatusChange(mStatus -> mArcMenu.setBackgroundColor(
            mStatus == ArcMenu.Status.OPEN ? Color.LTGRAY : Color.TRANSPARENT));
    }
}

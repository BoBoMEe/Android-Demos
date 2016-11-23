package com.bobomee.blogdemos.view.arcmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseFragment;


/**
 * @author：BoBoMEe Created at 2016/1/4.
 */
public class ArcMenuFragment extends BaseFragment {
    private ArcMenu mArcMenu;

    public static ArcMenuFragment newInstance() {
         Bundle args = new Bundle();
         ArcMenuFragment fragment = new ArcMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.arc_menu_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mArcMenu = (ArcMenu) view.findViewById(R.id.id_arcmenu);

        initArcMenu();
    }

    private void initArcMenu() {

        ImageView people = new ImageView(baseA);
        people.setImageResource(R.mipmap.composer_sleep);
        people.setTag("Sleep");
        mArcMenu.addView(people);


        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {

                Toast.makeText(baseA, view.getTag() + "; position :" + pos, Toast.LENGTH_LONG).show();
            }
        });

        mArcMenu.setStatusChange(new ArcMenu.StatusChange() {
            @Override
            public void arcMenuStatus(ArcMenu.Status mStatus) {

                mArcMenu.setBackgroundColor(mStatus == ArcMenu.Status.OPEN ? Color.LTGRAY : Color.TRANSPARENT);
            }
        });

    }

}

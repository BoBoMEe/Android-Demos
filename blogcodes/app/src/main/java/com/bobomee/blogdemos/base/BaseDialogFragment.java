package com.bobomee.blogdemos.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bobomee on 16/1/11.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected BaseActivity baseA;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseA = (BaseActivity) getActivity();
    }

    protected View fragmentRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == fragmentRoot) {
            fragmentRoot = initFragmentView(inflater, container, savedInstanceState);
        }

        if (null != fragmentRoot) {
            ViewGroup parent = (ViewGroup) fragmentRoot.getParent();
            if (null != parent)
                parent.removeAllViews();
        }else {
            fragmentRoot = super.onCreateView(inflater, container, savedInstanceState);
        }

        if (null != fragmentRoot)mUnbinder = ButterKnife.bind(this,fragmentRoot);

        return fragmentRoot;
    }

    public abstract View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder)mUnbinder.unbind();
    }
}

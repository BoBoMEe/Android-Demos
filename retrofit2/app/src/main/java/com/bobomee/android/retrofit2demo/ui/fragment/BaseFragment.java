package com.bobomee.android.retrofit2demo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bobomee on 16/5/15.
 */
public abstract class BaseFragment extends Fragment {

    CompositeSubscription mCompositeSubscription;

    public Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    public void unsubscribe() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.clear();
        }
    }

    public abstract int provideLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(provideLayoutId(), container, false);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unsubscribe();
    }
}

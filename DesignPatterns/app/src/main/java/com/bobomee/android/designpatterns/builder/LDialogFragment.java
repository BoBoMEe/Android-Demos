/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.designpatterns.builder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/13.
 */
public  class LDialogFragment extends DialogFragment {
    public View fragmentRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == fragmentRootView) {
            fragmentRootView = initFragmentView(inflater, container, savedInstanceState);
        }
        if (null != fragmentRootView) {
            ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
            if (null != parent)
                parent.removeAllViews();
        } else {
            fragmentRootView = super.onCreateView(inflater, container, savedInstanceState);
        }

        return fragmentRootView;
    }
    public  View initFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }
}

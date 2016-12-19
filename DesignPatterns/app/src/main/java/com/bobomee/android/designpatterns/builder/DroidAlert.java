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

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

/**
 * Created on 16/8/13.下午3:40.
 *
 * @author bobomee.
 * @description:
 */
public class DroidAlert extends LDialogFragment {

  private static String sDialogData = "dialogData";
  private DialogData dialogData;

  private static final String TAG = "DroidAlert";

  public static DroidAlert newInstance(DialogData d) {
    Bundle args = new Bundle();
    DroidAlert fragment = new DroidAlert();
    args.putParcelable(sDialogData, d);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialogData = getArguments().getParcelable(sDialogData);
  }

  public static DroidAlert showDialog(AppCompatActivity appCompatActivity, DialogData dialogData) {

    FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();

    DroidAlert uialert = newInstance(dialogData);

    if (!appCompatActivity.isFinishing() && !uialert.isAdded()) {
      fragmentManager.beginTransaction().add(uialert, TAG).commitAllowingStateLoss();
    }

    return uialert;
  }

  public static DroidAlert showDialog(FragmentManager _fragmentManager, DialogData _dialogData) {
    DroidAlert fragmentByTag = (DroidAlert) _fragmentManager.findFragmentByTag(TAG);
    if (null == fragmentByTag) {
      fragmentByTag = newInstance(_dialogData);
    }
    if (!fragmentByTag.isAdded()) {
      _fragmentManager.beginTransaction().add(fragmentByTag, TAG).commitAllowingStateLoss();
    }

    return fragmentByTag;
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {

    Dialog dialog = super.onCreateDialog(savedInstanceState);

    if (null != dialogData) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

      if (-1 != dialogData.iTitle) {
        builder.setTitle(dialogData.iTitle);
      } else if (!TextUtils.isEmpty(dialogData.sTitle)) {
        builder.setTitle(dialogData.sTitle);
      }

      if (-1 != dialogData.iContent) {
        builder.setMessage(dialogData.iContent);
      } else if (!TextUtils.isEmpty(dialogData.sContent)) {
        builder.setMessage(dialogData.sContent);
      }

      if (-1 != dialogData.iPos && null != dialogData.sPosClick) {
        builder.setPositiveButton(dialogData.iPos, dialogData.sPosClick);
      } else if (!TextUtils.isEmpty(dialogData.sPos) && null != dialogData.sPosClick) {
        builder.setPositiveButton(dialogData.sPos, dialogData.sPosClick);
      }

      if (-1 != dialogData.iNega && null != dialogData.sNegaClick) {
        builder.setNegativeButton(dialogData.iNega, dialogData.sNegaClick);
      } else if (!TextUtils.isEmpty(dialogData.sNega) && null != dialogData.sNegaClick) {
        builder.setNegativeButton(dialogData.sNega, dialogData.sNegaClick);
      }

      if (-1 != dialogData.iContentView) {
        builder.setView(dialogData.iContentView);
      } else if (null != dialogData.vContentView) {
        builder.setView(dialogData.vContentView);
      }

      dialog = builder.create();
    }

    return dialog;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String sTitle;
    private int iTitle = -1;
    private String sContent;
    private int iContent = -1;
    private String sPos;
    private int iPos = -1;
    private String sNega;
    private int iNega = -1;
    private int iContentView;
    private View vContentView;

    private DialogInterface.OnClickListener sPosClick;
    private DialogInterface.OnClickListener sNegaClick;

    public Builder title(int iTitle) {
      this.iTitle = iTitle;
      return this;
    }

    public Builder title(String sTitle) {
      this.sTitle = sTitle;
      return this;
    }

    public Builder content(int iContent) {
      this.iContent = iContent;
      return this;
    }

    public Builder content(String sContent) {
      this.sContent = sContent;
      return this;
    }

    public Builder pos(int iPos, DialogInterface.OnClickListener sPosClick) {
      this.iPos = iPos;
      this.sPosClick = sPosClick;
      return this;
    }

    public Builder pos(String sPos, DialogInterface.OnClickListener sPosClick) {
      this.sPos = sPos;
      this.sPosClick = sPosClick;
      return this;
    }

    public Builder nega(int iNega, DialogInterface.OnClickListener sNegaClick) {
      this.iNega = iNega;
      this.sNegaClick = sNegaClick;
      if (this.sNegaClick == null) {
        this.sNegaClick = new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        };
      }
      return this;
    }

    public Builder nega(String sNega, DialogInterface.OnClickListener sNegaClick) {
      this.sNega = sNega;
      this.sNegaClick = sNegaClick;
      if (this.sNegaClick == null) {
        this.sNegaClick = new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        };
      }
      return this;
    }

    public Builder contentView(int _iContentView) {
      this.iContentView = _iContentView;
      return this;
    }

    public Builder contentView(View _vContentView) {
      this.vContentView = _vContentView;
      return this;
    }

    public DialogData build() {
      return new DialogData(sTitle, iTitle, sContent, iContent, sPos, iPos, sNega, iNega, sPosClick,
          sNegaClick, iContentView, vContentView);
    }
  }

  final public static class DialogData implements Parcelable {
    private String sTitle;
    private int iTitle;
    private String sContent;
    private int iContent;
    private String sPos;
    private int iPos;
    private String sNega;
    private int iNega;
    private int iContentView;
    private View vContentView;

    private DialogInterface.OnClickListener sPosClick;
    private DialogInterface.OnClickListener sNegaClick;

    protected DialogData(Parcel in) {
      sTitle = in.readString();
      iTitle = in.readInt();
      sContent = in.readString();
      iContent = in.readInt();
      sPos = in.readString();
      iPos = in.readInt();
      sNega = in.readString();
      iNega = in.readInt();
      iContentView = in.readInt();
    }

    public static final Creator<DialogData> CREATOR = new Creator<DialogData>() {
      @Override public DialogData createFromParcel(Parcel in) {
        return new DialogData(in);
      }

      @Override public DialogData[] newArray(int size) {
        return new DialogData[size];
      }
    };

    public DialogData(String sTitle, int iTitle, String sContent, int iContent, String sPos,
        int iPos, String sNega, int iNega, DialogInterface.OnClickListener sPosClick,
        DialogInterface.OnClickListener sNegaClick, int _iContentView, View _vContentView) {
      this.iTitle = iTitle;
      this.sTitle = sTitle;
      this.iContent = iContent;
      this.sContent = sContent;
      this.iPos = iPos;
      this.sPos = sPos;
      this.iNega = iNega;
      this.sNega = sNega;
      this.sPosClick = sPosClick;
      this.sNegaClick = sNegaClick;
      this.iContentView = _iContentView;
      this.vContentView = _vContentView;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(sTitle);
      dest.writeInt(iTitle);
      dest.writeString(sContent);
      dest.writeInt(iContent);
      dest.writeString(sPos);
      dest.writeInt(iPos);
      dest.writeString(sNega);
      dest.writeInt(iNega);
      dest.writeInt(iContentView);
    }
  }
}

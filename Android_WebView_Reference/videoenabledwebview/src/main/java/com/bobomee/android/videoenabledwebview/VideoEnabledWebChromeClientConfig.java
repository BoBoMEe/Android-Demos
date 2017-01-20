/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
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

package com.bobomee.android.videoenabledwebview;

import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created on 2017/1/20.下午8:21.
 *
 * @author bobomee.
 */

public class VideoEnabledWebChromeClientConfig {

  private ToggledFullscreenCallback mToggledFullscreenCallback;
  private OnPreparedListener mOnPreparedListener;
  private OnCompletionListener mOnCompletionListener;
  private OnErrorListener mOnErrorListener;

  /**
   * View that will be hidden when video goes fullscreen
   */
  private View mNonVideoLayout;
  /**
   * View where the video will be shown when video goes fullscreen
   */
  private ViewGroup mVideoLayout;
  private View mLoadingView;
  private WebView mWebView;

  private VideoEnabledWebChromeClientConfig(Builder builder) {
    mLoadingView = builder.mLoadingView;
    mToggledFullscreenCallback = builder.mToggledFullscreenCallback;
    mOnPreparedListener = builder.mOnPreparedListener;
    mOnCompletionListener = builder.mOnCompletionListener;
    mOnErrorListener = builder.mOnErrorListener;
    mNonVideoLayout = builder.mNonVideoLayout;
    mVideoLayout = builder.mVideoLayout;
    mWebView = builder.mWebView;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * {@code VideoEnabledWebChromeClientConfig} builder static inner class.
   */
  public static final class Builder {
    private OnCompletionListener mOnCompletionListener;
    private ToggledFullscreenCallback mToggledFullscreenCallback;
    private OnPreparedListener mOnPreparedListener;
    private OnErrorListener mOnErrorListener;
    private View mNonVideoLayout;
    private ViewGroup mVideoLayout;
    private WebView mWebView;
    private View mLoadingView;

    private Builder() {
    }

    /**
     * Sets the {@code mOnCompletionListener} and returns a reference to this Builder so that the
     * methods can be chained together.
     *
     * @param mOnCompletionListener
     *     the {@code mOnCompletionListener} to set
     * @return a reference to this Builder
     */
    public Builder mOnCompletionListener(OnCompletionListener mOnCompletionListener) {
      this.mOnCompletionListener = mOnCompletionListener;
      return this;
    }

    /**
     * Sets the {@code mToggledFullscreenCallback} and returns a reference to this Builder so that
     * the methods can be chained together.
     *
     * @param mToggledFullscreenCallback
     *     the {@code mToggledFullscreenCallback} to set
     * @return a reference to this Builder
     */
    public Builder mToggledFullscreenCallback(
        ToggledFullscreenCallback mToggledFullscreenCallback) {
      this.mToggledFullscreenCallback = mToggledFullscreenCallback;
      return this;
    }

    /**
     * Sets the {@code mOnPreparedListener} and returns a reference to this Builder so that the
     * methods can be chained together.
     *
     * @param mOnPreparedListener
     *     the {@code mOnPreparedListener} to set
     * @return a reference to this Builder
     */
    public Builder mOnPreparedListener(OnPreparedListener mOnPreparedListener) {
      this.mOnPreparedListener = mOnPreparedListener;
      return this;
    }

    /**
     * Sets the {@code mOnErrorListener} and returns a reference to this Builder so that the
     * methods
     * can be chained together.
     *
     * @param mOnErrorListener
     *     the {@code mOnErrorListener} to set
     * @return a reference to this Builder
     */
    public Builder mOnErrorListener(OnErrorListener mOnErrorListener) {
      this.mOnErrorListener = mOnErrorListener;
      return this;
    }

    /**
     * Sets the {@code mNonVideoLayout} and returns a reference to this Builder so that the methods
     * can be chained together.
     *
     * @param mNonVideoLayout
     *     the {@code mNonVideoLayout} to set
     * @return a reference to this Builder
     */
    public Builder mNonVideoLayout(View mNonVideoLayout) {
      this.mNonVideoLayout = mNonVideoLayout;
      return this;
    }

    /**
     * Sets the {@code mVideoLayout} and returns a reference to this Builder so that the methods can
     * be chained together.
     *
     * @param mVideoLayout
     *     the {@code mVideoLayout} to set
     * @return a reference to this Builder
     */
    public Builder mVideoLayout(ViewGroup mVideoLayout) {
      this.mVideoLayout = mVideoLayout;
      return this;
    }

    /**
     * Sets the {@code mWebView} and returns a reference to this Builder so that the methods can be
     * chained together.
     *
     * @param mWebView
     *     the {@code mWebView} to set
     * @return a reference to this Builder
     */
    public Builder mWebView(WebView mWebView) {
      this.mWebView = mWebView;
      return this;
    }

    /**
     * Returns a {@code VideoEnabledWebChromeClientConfig} built from the parameters previously
     * set.
     *
     * @return a {@code VideoEnabledWebChromeClientConfig} built with parameters of this {@code
     * VideoEnabledWebChromeClientConfig.Builder}
     */
    public VideoEnabledWebChromeClientConfig build() {
      return new VideoEnabledWebChromeClientConfig(this);
    }

    /**
     * Sets the {@code mLoadingView} and returns a reference to this Builder so that the methods can
     * be chained together.
     *
     * @param mLoadingView
     *     the {@code mLoadingView} to set
     * @return a reference to this Builder
     */
    public Builder mLoadingView(View mLoadingView) {
      this.mLoadingView = mLoadingView;
      return this;
    }
  }

  public View getLoadingView() {
    return mLoadingView;
  }

  public View getNonVideoLayout() {
    return mNonVideoLayout;
  }

  public OnCompletionListener getOnCompletionListener() {
    return mOnCompletionListener;
  }

  public OnErrorListener getOnErrorListener() {
    return mOnErrorListener;
  }

  public OnPreparedListener getOnPreparedListener() {
    return mOnPreparedListener;
  }

  public ToggledFullscreenCallback getToggledFullscreenCallback() {
    return mToggledFullscreenCallback;
  }

  public ViewGroup getVideoLayout() {
    return mVideoLayout;
  }

  public WebView getWebView() {
    return mWebView;
  }
}

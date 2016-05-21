package com.bobomee.android.retrofit2demo.ui.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bobomee.android.retrofit2demo.R;
import com.bobomee.android.retrofit2demo.github.GithubApi;
import com.bobomee.android.retrofit2demo.github.GithubService;
import com.bobomee.android.retrofit2demo.github.model.AccessTokenResp;
import com.bobomee.android.retrofit2demo.github.model.User;
import com.bobomee.android.retrofit2demo.util.PreferencesHelper;
import com.bobomee.android.retrofit2demo.util.WebViewSetttings;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bobomee on 16/5/20.
 */
public class SimplePostFragmeent extends BaseFragment {
    @Bind(R.id.loading)
    ProgressBar loading;
    @Bind(R.id.login_info_web)
    WebView loginInfoWeb;
    @Bind(R.id.login_info_text)
    TextView loginInfoText;
    @Bind(R.id.login_info_layout)
    ScrollView loginInfoLayout;

    private GithubApi _githubService;
    private PreferencesHelper preferencesHelper;

    @Override
    public int provideLayoutId() {
        return R.layout.simple_post_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _githubService = GithubService.INSTANCE.getGithubApi();
        preferencesHelper = new PreferencesHelper(activity);
    }

    @OnClick(R.id.login_to_github)
    public void simple_post(View view) {
        view.setVisibility(View.GONE);
        loginInfoLayout.setVisibility(View.VISIBLE);

        WebViewSetttings.basic(loginInfoWeb);

        String accessToken = preferencesHelper.getAccessToken();

        if (TextUtils.isEmpty(accessToken)) {

            loginInfoWeb.loadUrl(GithubApi.AUTH_URL);
            loginInfoWeb.requestFocus(View.FOCUS_DOWN);

            loginInfoWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    loading.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    loading.setVisibility(View.GONE);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                    Uri uri = Uri.parse(url);
                    if (uri.getScheme().equals("http")) {
                        Logger.d("### get url %s", url);

                        String code = uri.getQueryParameter("code");

                        if (!TextUtils.isEmpty(code)) {

                            addSubscription(
                                    //get accessToken
                                    getOAuthToken(code)
                                            .flatMap(new Func1<AccessTokenResp, Observable<User>>() {
                                                @Override
                                                public Observable<User> call(AccessTokenResp accessTokenResp) {
                                                    return getUserInfo(accessTokenResp.getAccessToken());
                                                }
                                            })
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(getSubscriber())
                            );

                        }

                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });


        } else {

            loginInfoWeb.loadUrl(accessToken);
            loginInfoWeb.requestFocus(View.FOCUS_DOWN);

            loginInfoText.setText(accessToken);

            loginInfoWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    loading.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    loading.setVisibility(View.GONE);
                }

            });

        }

    }

    @NonNull
    private Subscriber<User> getSubscriber() {
        return new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Logger.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("onError" + e.toString());
            }

            @Override
            public void onNext(User user) {
                loginInfoWeb.loadUrl(user.html_url);
                loginInfoWeb.requestFocus(View.FOCUS_DOWN);

                loginInfoText.setText(user.html_url);
                preferencesHelper.putAccessToken(user.html_url);
            }
        };
    }


    private Observable<AccessTokenResp> getOAuthToken(String code) {
        return _githubService
                .getOAuthToken(GithubApi.CLIENT_ID,
                        GithubApi.CLIENT_SECRET,
                        code, GithubApi.redirect_uri
                        , GithubApi.state)
                .subscribeOn(Schedulers.io())
                ;
    }

    private Observable<User> getUserInfo(String accessTokenResp) {
        return _githubService.getUserInfo(accessTokenResp)
                .subscribeOn(Schedulers.io());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                //code here
                activity.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

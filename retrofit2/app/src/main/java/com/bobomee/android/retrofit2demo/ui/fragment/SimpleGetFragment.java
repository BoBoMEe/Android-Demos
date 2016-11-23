package com.bobomee.android.retrofit2demo.ui.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bobomee.android.retrofit2demo.R;
import com.bobomee.android.retrofit2demo.github.model.Contributor;
import com.bobomee.android.retrofit2demo.github.GithubApi;
import com.bobomee.android.retrofit2demo.github.GithubService;
import com.bobomee.android.retrofit2demo.github.model.User;
import com.bobomee.android.retrofit2demo.ui.adapter.LogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;

/**
 * Created by bobomee on 16/5/15.
 */
public class SimpleGetFragment extends BaseFragment {

    @Bind(R.id.owner)
    EditText owner;
    @Bind(R.id.reponame)
    EditText reponame;
    @Bind(R.id.contributors)
    Button contributors;
    @Bind(R.id.fullinfo)
    Button fullinfo;
    @Bind(R.id.loading)
    ProgressBar loading;
    @Bind(R.id.list)
    ListView list;

    private GithubApi _githubService;
    private LogAdapter _adapter;

    @Override
    public int provideLayoutId() {
        return R.layout.simple_get_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _githubService = GithubService.INSTANCE.getGithubApi();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _setupLogger();
    }

    private void _setupLogger() {
        _adapter = new LogAdapter(activity, new ArrayList<String>());
        list.setAdapter(_adapter);
    }

    private boolean _isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


    @OnClick(R.id.contributors)
    public void contributors() {

        addSubscription(
                _githubService.contributors(owner.getText().toString().trim(), reponame.getText().toString().trim())
                        .subscribeOn(Schedulers.io())
                        .debounce(150, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<List<Contributor>, Boolean>() {
                            @Override
                            public Boolean call(List<Contributor> contributors) {
                                _adapter.clear();
                                return null != contributors && contributors.size() > 0;
                            }
                        })
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loading.setVisibility(View.VISIBLE);
                            }
                        })
                        .observeOn(Schedulers.io())
                        .switchMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                            @Override
                            public Observable<Contributor> call(List<Contributor> contributors) {
                                return Observable.from(contributors);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Contributor>() {
                            @Override
                            public void onCompleted() {
                                loading.setVisibility(View.GONE);
                                _adapter.add("onCompleted");
                                Logger.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setVisibility(View.GONE);
                                _adapter.add("onError" + e.toString());
                                Logger.i("onError" + e.toString());
                            }

                            @Override
                            public void onNext(Contributor contributor) {
                                String s = String.format("%s has made %d contributions to %s",
                                        contributor.login,
                                        contributor.contributions,
                                        reponame.getText().toString());
                                _adapter.add(s);
                                Logger.i(s);
                            }
                        })
        );

    }


    @OnClick(R.id.fullinfo)
    public void fullinfo() {
        _adapter.clear();
        addSubscription(
                _githubService.contributors(owner.getText().toString().trim(), reponame.getText().toString().toString())
                        .subscribeOn(Schedulers.io())
                        .debounce(150, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<List<Contributor>, Boolean>() {
                            @Override
                            public Boolean call(List<Contributor> contributors) {
                                _adapter.clear();
                                return null != contributors && contributors.size() > 0;
                            }
                        })
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loading.setVisibility(View.VISIBLE);
                            }
                        })
                        .observeOn(Schedulers.io())
                        .switchMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                            @Override
                            public Observable<Contributor> call(List<Contributor> contributors) {
                                return Observable.from(contributors);
                            }
                        })
                        .flatMap(new Func1<Contributor, Observable<Pair<User, Contributor>>>() {
                            @Override
                            public Observable<Pair<User, Contributor>> call(Contributor contributor) {

                                //get User
                                Observable<User> _usersObservable = getUsers(contributor);
                                Observable<Contributor> _contributorObservable = Observable.just(contributor);

                                return Observable.zip(_usersObservable, _contributorObservable, new Func2<User, Contributor, Pair<User, Contributor>>() {
                                    @Override
                                    public Pair<User, Contributor> call(User user, Contributor contributor) {
                                        return new Pair<User, Contributor>(user, contributor);
                                    }
                                });
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Pair<User, Contributor>>() {
                            @Override
                            public void onCompleted() {
                                loading.setVisibility(View.GONE);
                                _adapter.add("onCompleted");
                                Logger.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setVisibility(View.GONE);
                                _adapter.add("onError" + e.toString());
                                Logger.i("onError" + e.toString());
                            }

                            @Override
                            public void onNext(Pair<User, Contributor> userContributorPair) {

                                User user = userContributorPair.first;
                                Contributor contributor = userContributorPair.second;

                                String s = String.format("%s(%s) has made %d contributions to %s",
                                        user.name,
                                        user.email,
                                        contributor.contributions,
                                        reponame.getText().toString());

                                _adapter.add(s);

                                Logger.i(s);
                            }
                        })
        );
    }

    public Observable<User> getUsers(Contributor contributor) {
        return _githubService.user(contributor.login)
                .filter(new Func1<User, Boolean>() {
                    @Override
                    public Boolean call(User user) {
                        return !isEmpty(user.name) && !isEmpty(user.email);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

package com.bobomee.android.retrofit2demo;

import android.os.Bundle;
import android.os.Looper;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bobomee.android.retrofit2demo.github.Contributor;
import com.bobomee.android.retrofit2demo.github.GithubApi;
import com.bobomee.android.retrofit2demo.github.GithubService;
import com.bobomee.android.retrofit2demo.github.User;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static java.lang.String.format;

public class MainActivity extends BaseActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _githubService = GithubService.getInstance().getGithubApi();
        _setupLogger();
    }

    private void _setupLogger() {
        _adapter = new LogAdapter(this, new ArrayList<String>());
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
                                _adapter.add("onCompleted");
                                Logger.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                _adapter.add("onError" + e.toString());
                                Logger.i("onError" + e.toString());
                            }

                            @Override
                            public void onNext(Contributor contributor) {
                                String s = format("%s has made %d contributions to %s",
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
                                _adapter.add("onCompleted");
                                Logger.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                _adapter.add("onError" + e.toString());
                                Logger.i("onError" + e.toString());
                            }

                            @Override
                            public void onNext(Pair<User, Contributor> userContributorPair) {

                                User user = userContributorPair.first;
                                Contributor contributor = userContributorPair.second;

                                String s = format("%s(%s) has made %d contributions to %s",
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

}



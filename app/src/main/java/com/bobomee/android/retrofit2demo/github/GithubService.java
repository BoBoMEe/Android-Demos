package com.bobomee.android.retrofit2demo.github;

import com.bobomee.android.retrofit2demo.RetrofitClient;

import retrofit2.Retrofit;

public class GithubService {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (null == retrofit) {
            retrofit = new RetrofitClient().getRetrofit();
        }
        return retrofit;
    }

    private static GithubService githubService;

    private GithubApi githubApi;

    private GithubService() {
        githubApi = getRetrofit().create(GithubApi.class);
    }

    public GithubApi getGithubApi(){
        return githubApi;
    }

    public static GithubService getInstance(){
        if (null == githubService)githubService = new GithubService();

        return githubService;
    }

}

package com.bobomee.android.retrofit2demo.github;

import com.bobomee.android.htttp.retrofit2.Retrofit2Client;

public enum GithubService {
    INSTANCE;

    private GithubApi githubApi;

    GithubService() {
        githubApi = Retrofit2Client.INSTANCE
                .getRetrofitBuilder()
                .baseUrl(GithubApi.BASE_URL).build()
                .create(GithubApi.class);
    }

    public GithubApi getGithubApi() {
        return githubApi;
    }


}

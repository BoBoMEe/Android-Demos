package com.bobomee.android.retrofit2demo.github;

import com.bobomee.android.retrofit2demo.github.model.AccessTokenResp;
import com.bobomee.android.retrofit2demo.github.model.Contributor;
import com.bobomee.android.retrofit2demo.github.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GithubApi {

    String BASE_URL = "https://api.github.com/";

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @Headers("Cache-Control: public, max-age=6")
    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(@Path("owner") String owner,
                                               @Path("repo") String repo);

    /**
     * See https://developer.github.com/v3/users/
     */
    @Headers("Cache-Control: public, max-age=6")
    @GET("users/{user}")
    Observable<User> user(@Path("user") String user);


    ///////////////////////////auth3//////////////////////////////
    /**
     * step 1
     * Create Application and get id and secret
     * See https://github.com/settings/applications/new
     */
    String CLIENT_ID = "70853a38156f022c98cf";
    String CLIENT_SECRET = "0ba62e3e82456dd97c40b42f2f045f9a0fd97028";
    String redirect_uri = "http://bobomee.github.io/";
    String scope = "user,public_repo,repo";
    String state = "retrofit2_login_state";

    /**
     * step 2
     * <p/>
     * get code
     * See https://github.com/login/oauth/authorize
     */

    String AUTH_URL = "https://www.github.com/login/oauth/authorize?" +
            "client_id=" + CLIENT_ID + "&"
            + "scope=" + scope + "&"
            + "redirect_uri=" + redirect_uri + "&"
            + "state=" + state + "&"
            + "allow_signup=true";


    /*******
     * step 3
     * oauth. different endPoint, user override url
     ******/
    @Headers({
            "Accept: application/json"
    })
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Observable<AccessTokenResp> getOAuthToken(@Field("client_id") String client,
                                              @Field("client_secret") String clientSecret,
                                              @Field("code") String code,
                                              @Field("redirect_uri") String redirect_uri,
                                              @Field("state") String state);

    /**
     * step 4
     * sent in a header "Authorization: token OAUTH-TOKEN" https://api.github.com
     * sent as a parameter https://api.github.com/user?access_token=OAUTH-TOKEN
     */
    @Headers({
            "Authorization: token OAUTH-TOKEN"
    })
    @GET("user")
    Observable<User> getUserInfo(
            @Query("access_token") String access_token
    );
}
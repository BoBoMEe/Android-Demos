## 概述

Retrofit是由[Square](https://github.com/square)公司出品的针对于Android和Java的类型安全的Http客户端，网络服务基于OkHttp 。 

## 变化

如果之前使用过Retrofit1，2.0后的API会有一些变化，
比如创建方式，拦截器，错误处理，转换器等，参考：[更新到Retrofit2的一些技巧](http://blog.csdn.net/tiankong1206/article/details/50720758)

1. 在Retrofit1中使用的是RestAdapter，而Retrofit2中使用的Retrofit实例，之前的setEndpoint变为了baseUrl。
2. Retrofit1中使用setRequestInterceptor设置拦截器，对http请求进行相应等处理。
3. Retrofit2通过OKHttp的拦截器拦截http请求进行监控，重写或重试等，包括日志打印等。
4. converter，Retrofit1中的setConverter，换以addConverterFactory，用于支持Gson转换。

更多变化参考：[官方CHANGELOG.md](https://github.com/square/retrofit/blob/master/CHANGELOG.md)

## 配置

## 1.9实例：

```java
//gson converter
       final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

// client
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(client))
               .setLogLevel(RestAdapter.LogLevel.FULL)
               .setEndpoint("https://api.github.com")
               .setConverter(new GsonConverter(gson))
               .setErrorHandler(new ErrorHandler() {
                           @Override
                           public Throwable handleError(RetrofitError cause) {
                               return null;
                           }
                       })
               .setRequestInterceptor(authorizationInterceptor)
        RestAdapter restAdapter = builder.build();
        apiService = restAdapter.create(ApiService.class);
```

## 2.0配置

引入依赖

```java
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'

    compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'
```


OkHttp配置

```java
   HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(authorizationInterceptor)
                .build();
```

其中 level 为 BASIC / HEADERS / BODY，BODY等同于1.9中的FULL
retryOnConnectionFailure:错误重联
addInterceptor:设置应用拦截器，可用于设置公共参数，头信息，日志拦截等
addNetworkInterceptor：网络拦截器，可以用于重试或重写，对应与1.9中的setRequestInterceptor。
详见：[Interceptors](https://github.com/square/okhttp/wiki/Interceptors)
中文翻译：[Okhttp-wiki 之 Interceptors 拦截器](http://www.jianshu.com/p/2710ed1e6b48)

Retrofit配置

```java
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
        apiService = retrofit.create(ApiService.class);
```

其中baseUrl相当于1.9中的setEndPoint,addCallAdapterFactory提供RxJava支持，addConverterFactory提供Gson支持

## 使用

retrofit2.0后：BaseUrl要以/结尾；@GET 等请求不要以/开头；@Url: 可以定义完整url，不要以 / 开头。

### API介绍：

#### 简单Http请求

```java
//定以接口
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}

//获取实例
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);

//同步请求
Call<List<Repo>> call = service.listRepos("octocat");
List<Repo> repos = call.execute();

//异步请求
call.enqueue(new Callback<List<Repo>>() {
        @Override
        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
            // Get result bean from response.body()
        }

        @Override
        public void onFailure(Call<List<Repo>> call, Throwable t) {

        }
    });
    
// 取消
call.cancel();
```


```java
//rxjava support
public interface GitHubService {
  @GET("users/{user}/repos")
  Observable<List<Repo>> listRepos(@Path("user") String user);
}

// 获取实例
// Http request
Observable<List<Repo>> call = service.listRepos("octocat");
```

### retrofit注解：

方法注解，内置5种，分别是GET、POST、PUT、DELETE和HEAD。

参数注解，retrofit如下注解配合可以很方便的进行url处理

@Path：URL占位符，用于替换和动态更新,相应的参数必须使用相同的字符串被@Path进行注释

```java
@GET("group/{id}/users")
Call<List<User>> groupList(@Path("id") int groupId);
//--> http://baseurl/group/groupId/users
```

@Query:查询参数,@QueryMap:多个查询参数

```java
@GET("group/users")
Call<List<User>> groupList(@Query("id") int groupId);
//--> http://baseurl/group/users?id=groupId
```

@Body:用于POST请求体，将实例对象根据转换方式转换为对应的json字符串参数，这个转化方式是GsonConverterFactory定义的。

```java
 @POST("add")
 Call<List<User>> addUser(@Body User user);
```

@Field:Post方式传递简单的键值对,需要添加@FormUrlEncoded表示表单提交

```java
@FormUrlEncoded
@POST("user/edit")
Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
```

@Part:但文件上传，@PartMap：多文件上传
其中@Part MultipartBody.Part代表文件，@Part("key") RequestBody代表参数

```java
 @Multipart
    @POST("register")
    Call<User> registerUser(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);
```

```java
File file = new File(Environment.getExternalStorageDirectory(), "icon.png");
RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);

Call<User> call = userBiz.registerUser(photo, RequestBody.create(null, "abc"), RequestBody.create(null, "123"));
```

参考：[ Retrofit2 完全解析 探索与okhttp之间的关系](http://blog.csdn.net/lmj623565791/article/details/51304204)

@Header：header处理，header不能被互相覆盖：

```java
@GET("user")
Call<User> getUser(@Header("Authorization") String authorization)
```

等同于 :
```java
@Headers("Authorization: authorization")//这里authorization就是上面方法里传进来变量的值
@GET("widget/list")
Call<User> getUser()
```

多个设置 ：

```java
@Headers({
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit-Sample-App"
})
```

## 缓存策略

Retrofit的低层依赖的是OkHttp，因此设置缓存就需要用到OkHttp的interceptors，
参考：[Retrofit2.0+okhttp3缓存机制以及遇到的问题](http://blog.csdn.net/picasso_l/article/details/50579884)
[How Retrofit with OKHttp use cache data when offline](http://stackoverflow.com/questions/31321963/how-retrofit-with-okhttp-use-cache-data-when-offline)
[使用Retrofit和Okhttp实现网络缓存。无网读缓存，有网根据过期时间重新请求](http://www.jianshu.com/p/9c3b4ea108a7)
如果想要弄清楚缓存机制，则需要了解一下HTTP语义，缓存的设置需要靠请求和响应头。有如下需求

- 没有网或者网络较差的时候要使用缓存
- 有网络的时候，为了保证不同的需求，实时性数据，不用缓存。

OkHttp3中有一个Cache类是用来定义缓存的，此类详细介绍了几种缓存策略,具体可看此类源码。

>noCache ：不使用缓存，全部走网络
 noStore ：  不使用缓存，也不存储缓存
 onlyIfCached ： 只使用缓存
 maxAge  ：设置最大失效时间，失效则不使用 
 maxStale ：设置最大失效时间，失效则不使用 
 minFresh ：设置最小有效时间，失效则不使用
 FORCE_NETWORK ： 强制走网络
 FORCE_CACHE ：强制走缓存
 
### 配置目录 

```java
 private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
 
  private Cache cache() {
         //设置缓存路径
         final File baseDir = AppUtil.getAvailableCacheDir(sContext);
         final File cacheDir = new File(baseDir, "HttpResponseCache");
         //设置缓存 10M
         return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
     }
```

其中获取cacahe目录的代码

```java
  public static File getAvailableCacheDir(Context context) {
  
          File cacheDir = context.getCacheDir();
  
          //判断SD卡是否可用
          if (hasSDCardMounted()) {
  
              // 获取SD卡可用空间
              File externalCacheDir = getExternalCacheDir(context);
  
              long externalUsableSpace = getUsableSpace(externalCacheDir);
              long cacheDirUsableSpace = getUsableSpace(cacheDir);
  
              if (externalUsableSpace < cacheDirUsableSpace) {
                  return cacheDir;
              } else {
                  return externalCacheDir;
              }
  
          } else {
              return cacheDir;
          }
      }
      
//getExternalCacheDir(context) 
   
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File path = context.getExternalCacheDir();

            // In some case, even the sd card is mounted,
            // getExternalCacheDir will return null
            // may be it is nearly full.
            if (path != null) {
                return path;
            }
        }

        // Before Froyo or the path is null,
        // we need to construct the external cache folder ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
      
```

### 第一种类型

配置单个请求的@Headers，设置此请求的缓存策略。

```java
// 设置 单个请求的 缓存时间
@Headers("Cache-Control: max-age=640000")
@GET("widget/list")
Call<List<Widget>> widgetList();
```

### 第二种类型

有网和没网都先读缓存，统一缓存策略，降低服务器压力。

```java
private Interceptor cacheInterceptor() {
      Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };
      }
```

### 第三种类型

结合前两种，离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)。

```java
private Interceptor cacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!AppUtil.isNetworkReachable(sContext)) {
                    request = request.newBuilder()
                            //强制使用缓存
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if (AppUtil.isNetworkReachable(sContext)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    Logger.i("has network ,cacheControl=" + cacheControl);
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    Logger.i("network error ,maxStale="+maxStale);
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

            }
        };
    }
```

其中获取是否有网络：

```java
 public static boolean isNetworkReachable(Context context) {
         if (context != null) {
             ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
             return mNetworkInfo != null && mNetworkInfo.isAvailable();
         }
         return false;
     }
```

最终OkHttp设置：

```java
private OkHttpClient client() {

        //Log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                               .connectTimeout(15, TimeUnit.SECONDS)
                               .addNetworkInterceptor(cacheInterceptor())
                               .addInterceptor(cacheInterceptor())
                               .cache(cache())
                               .build();

        return client;
    }
```

Retrofit2+RxJava 使用Demo：[Retrofit2Demo](https://github.com/BoBoMEe/Retrofit2Demo/)

参考：
[Retrofit 2.0 + OkHttp 3.0 配置](https://drakeet.me/retrofit-2-0-okhttp-3-0-config)
[官方文档](http://square.github.io/retrofit/#restadapter-configuration)
[更新到Retrofit2的一些技巧](http://blog.csdn.net/tiankong1206/article/details/50720758)
[Effective OkHttp](http://omgitsmgp.com/2015/12/02/effective-okhttp/)
[Okhttp-wiki 之 Interceptors 拦截器](http://www.jianshu.com/p/2710ed1e6b48)
[Retrofit2.0+okhttp3缓存机制以及遇到的问题](http://blog.csdn.net/picasso_l/article/details/50579884)
[How Retrofit with OKHttp use cache data when offline](http://stackoverflow.com/questions/31321963/how-retrofit-with-okhttp-use-cache-data-when-offline)
[使用Retrofit和Okhttp实现网络缓存。无网读缓存，有网根据过期时间重新请求](http://www.jianshu.com/p/9c3b4ea108a7)
[用 Retrofit 2 简化 HTTP 请求](https://realm.io/cn/news/droidcon-jake-wharton-simple-http-retrofit-2/)
[Retrofit请求参数注解字段说明](http://www.loongwind.com/archives/242.html)
[Retrofit2 完全解析 探索与okhttp之间的关系](http://blog.csdn.net/lmj623565791/article/details/51304204)




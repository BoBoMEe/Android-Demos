Retrofit2

---

关于retrofit2与okhttp封装,缓存,错误处理,及常用注解

具体介绍:
[Retrofit2.0使用总结及注意事项](http://blog.csdn.net/wbwjx/article/details/51379506)

参考:[Retrofit 2和Rx Java调用适配器错误处理](http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html)


依赖库:

```groovy
    //rx + retrofit + okhttp
    compile 'io.reactivex:rxjava:1.1.0'
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'
    //common
    compile 'com.bobomee.android:common:1.4.8'
```
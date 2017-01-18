Android Common tools
--------------------
Android 开发中个人常用工具类整理,慢慢增加中..

UsAge
-----

```groovy
compile 'com.bobomee.android:common:1.4.6`
```


Note
----

- BaseApplication: 
 
 **自定义`Application`需要继承的类**

- ObservableManager：

**方便`Activity`与`Fragment`通信的类**

> 参考：

> [使用观察者模式完美解决activity与fragment通信问题](http://blog.csdn.net/wbwjx/article/details/51587887?locationNum=1&fps=1)

- UIUtil: 

**方便UI操作的工具类，getString(int resId),inflate(resId) ...**

- WebViewUtil:

**`WebView`重定向判断,`HTTP/HTTPS`混合使用,`WebView`文件下载,`WebView`硬件加速方案**

> 参考：

> [Android Webview实现文件下载功能](http://blog.csdn.net/fenglibing/article/details/6921160)

> [Android5.0 WebView中Http和Https混合问题](http://blog.csdn.net/luofen521/article/details/51783914)

> [WebView重定向问题的解决方案](http://blog.csdn.net/qq_33689414/article/details/51111691)

> [谈谈android的硬件加速](http://blog.csdn.net/fishmai/article/details/52398498)

> [Android：WebView开发笔记（二）](http://blog.alexwan1989.com/2016/01/21/Android%EF%BC%9AWebView%E5%BC%80%E5%8F%91%E7%AC%94%E8%AE%B0%EF%BC%88%E4%BA%8C%EF%BC%89/)

- IMMLeaks:

**`InputMethodManager`内存泄漏**

> 参考： [Android InputMethodManager 导致的内存泄露及解决方案](https://zhuanlan.zhihu.com/p/20828861?refer=zmywly8866)

....

- BackHandledFragment： 

**Fragment优雅的监听 `back` 事件**

> 参考： [两步搞定Fragment的返回键](http://www.jianshu.com/p/fff1ef649fc0)

- CommonAdapter

> Thanks to [tianzhijiexian/CommonAdapter](https://github.com/tianzhijiexian/CommonAdapter)

- Utils

> Build.VERSION_CODES :such as hasHoneycomb,hasIceCreamSandwich...
> InputMethodManager : hide and show
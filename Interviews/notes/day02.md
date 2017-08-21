## 广播相关

## 广播实现原理



QA ：两种广播的区别

 > Normal broadcasts无序广播，会异步的发送给所有的Receiver，接受顺序不定
 > Ordered broadcasts有序广播,会按优先级(android:priority)的顺序发送，并且前面的Receiver有权拦截广播的继续下发

 QA：静态和动态注册方式的区别

 > 通过代码或者 AndroidManifest
 > 静态注册 当应用处于非活动状态也可以监听

 QA：还有没有其他类型的广播

 > Context.sendBroadcast()、Context.sendOrderedBroadcast()或者Context.sendStickyBroadcast()

 QA: LocalBroadcastManager

 参考文章：[LocalBroadcastManager 的实现原理，还是 Binder？](http://www.trinea.cn/android/localbroadcastmanager-impl/)

 内部采用了Handler实现。

 QA: BroadcastReceiver的生命周期

 - 广播接收者的生命周期很短，在执行完`onReceive` 后就结束了，
 - 广播接收者一般需要与其他组件交互，如  `Notificaiton`或者`Service`
 - 广播接收器运行在UI线程，不能执行耗时操作（10s）

 QA：使用广播来更新界面是否合适

 - 如果不是频繁地刷新，使用广播来做也是可以的
 - 广播的发送和接收是有一定的代价的，频繁的不可采用（Binder）
 - 广播执行过程从 `发送—ActivityManagerService—ReceiverDispatcher`进行了两次Binder进程间通信,最后还要交到UI的消息队列，如果基中有一个消息的处理阻塞了UI，当然也会延迟你的onReceive的执行。

 ## 注意事项

 - 最好在Activity的onResume()注册、onPause()注销

 ## 有序广播特点

 1. filter.setPriority(10)设置优先级
 2. abortBroadcast()取消广播的传播
 3. setResultData和setResultExtras将处理的结果存入到Broadcast中，传递给下一个接收者
 4. 下一个接收者通过getResultData()和getResultExtras(true)接收高优先级的接收者存入的数据。
 

 参考文章；
 - [解析BroadcastReceiver之你需要了解的一些东东](http://www.cnblogs.com/net168/p/3980068.html)
 - [Android开发之BroadcastReceiver详解](http://blog.csdn.net/fengyuzhengfan/article/details/38414625)
 - [安卓四大控件之BroadcastReceiver详解](http://blog.csdn.net/qq_27280457/article/details/51840678)
 - [Android四大组件：BroadcastReceiver史上最全面解析](http://blog.csdn.net/carson_ho/article/details/52973504)
 - [Android随笔之——Android广播机制Broadcast详解](http://www.cnblogs.com/travellife/p/3944363.html)
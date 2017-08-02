Service 相关

参考：

- [Android Service介绍](http://www.cnblogs.com/trinea/archive/2012/11/08/2699856.html)
- [Android开发指南——绑定Service](http://yuweiguocn.github.io/android-guide-bound-service/)
- [ Android Service完全解析，关于服务你所需知道的一切(上)](http://blog.csdn.net/guolin_blog/article/details/11952435/#t2)
- [Service 学习笔记](http://dongchuan.github.io/android/2016/06/24/Android-Service.html)
- [Android AIDL应用间交互](http://www.cnblogs.com/trinea/archive/2012/11/08/2701390.html)


## 概念

ANR：Activity对事件响应不超过5秒，BroadcastReceiver执行不超过10秒
因此耗时操作需要使用Service，Service使用场景：

-  要长时间运行的操作，且不与用户交互
-  应用间数据通信，`Content Provider`多用于数据共享，`BroadcastReceiver`比较耗费系统资源

## 生命周期

### startService启动

1. 若服务未启动，会先执行onCreate函数(若服务已启动则不执行此函数)，再执行onStartCommand函数
2. 调用startService传入相同参数不会启动多个服务(onStartCommand函数会执行多次)
3. 最终只需要调用一次stopService或stopSelf函数停止服务
4. 可以将service的处理逻辑放入onStartCommand函数中
5. 服务一直运行，在程序退出后服务也不会停止，直到stopService或stopSelf函数被调用,或者被系统回收
6. 若`onStartCommand`返回START_STICKY表示服务通过显式调用启动或停止,

### bindService启动

1. 若服务未启动，会先执行Service的onCreate函数，再执行onBind函数，后执行ServiceConnection对象的onServiceConnected函数
2. 若服务已启动但尚未绑定，先执行onBind函数，再执行ServiceConnection对象的onServiceConnected函数
3. 若服务已绑定成功，则直接返回。这里不会自动调用onStartCommand函数。
4. 通过ServiceConnection对象的onServiceConnected函数为myService变量赋值
5. 通过unbindService解除绑定服务，如果绑定成功，会先执行Service的onUnbind函数，再执行onDestroy函数

## AIDL

## IntentService
和Service的区别
1. 运行在工作线程
2. 不需要手动调用停止接口
3. IntentService的onStartCommand函数根据mRedelivery属性值返回START_REDELIVER_INTENT或START_NOT_STICKY，而普通Service自定义返回

##



## 常见问题

QA: Service的onCreate与onStartCommand

> onCreate()方法只会在Service第一次被创建的时候调用，onStartCommand()每次都会调用

QA：Service有几种启动方式？

> Service是一个专门在后台处理长时间任务的Android组件，没有UI。它有两种启动方式，startService和bindService。

QA: startService和bindService 两种启动方式的区别

> startService只是启动Service，启动它的组件（如Activity）和Service并没有关联,需要手动调用 stopSelf/stopService  终止。
> bindService方法启动Service,并将启动组件和Service进行了绑定，启动方销毁时，Service也会自动进行unBind，所有绑定都进行了 unBind 后就会销毁Service。

QA：两种方式的使用场景

> `startService`启动的`Service`无法与外部进行方便的动态交互，所以适合做后台服务
> 因为有交互，多用于`应用内通信`，如`音乐播放器`
Service 相关

参考：

- [Android Service介绍](http://www.cnblogs.com/trinea/archive/2012/11/08/2699856.html)
- [Android开发指南——绑定Service](http://yuweiguocn.github.io/android-guide-bound-service/)
- [ Android Service完全解析，关于服务你所需知道的一切(上)](http://blog.csdn.net/guolin_blog/article/details/11952435/#t2)
- [Service 学习笔记](http://dongchuan.github.io/android/2016/06/24/Android-Service.html)

QA: Service的onCreate与onStartCommand

> onCreate()方法只会在Service第一次被创建的时候调用，onStartCommand()每次都会调用

QA：Service有几种启动方式？

> Service是一个专门在后台处理长时间任务的Android组件，没有UI。它有两种启动方式，startService和bindService。

QA: startService和bindService 两种启动方式的区别

> startService只是启动Service，启动它的组件（如Activity）和Service并没有关联,需要手动调用 stopSelf/stopService  终止。
> bindService方法启动Service,并将启动组件和Service进行了绑定，启动方销毁时，Service也会自动进行unBind，所有绑定都进行了 unBind 后就会销毁Service。

QA： 这两种调用方式对Service生命周期的影响

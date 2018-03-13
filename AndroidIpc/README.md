# Android 中的IPC方式

## 使用Bundle

> 三大组件（Activity，Service，Receiver）都支持Intent中传递Bundle数据（Bundle实现了Parcelable接口）

## 使用文件

> Android 由于基于 Linux，多线程对文件读写没有限制，因此容易出现一些问题。
建议在 **对数据同步要求不高** 的进程间通信，且要妥善处理并发读写问题，同理SharedPreferences属于文件共享。

## 使用Messenger

> Messenger是一种轻量级的IPC方案，底层使用的是AIDL。

### 使用Messenger的步骤

- Service 端:

操作1：
> 创建一个 Handler ，
并用 Handler 创建一个 Messenger 对象，
然后在 Service 的 onBind 中返回这个 Messenger 的 Binder 即可。

- Client 端：

操作2：
> 绑定服务端 Service，用服务端 IBinder 创建一个 Messenger
通过 Messenger 就可以向服务端发送消息了，消息类型为 Message

> 如果需要服务端的回应，客户端需要做 **操作1：**，并将 Messenger 作为 Message 的 replyTo 参数传递给服务端。

> 需要注意的是，Messenger和 Message 都实现了 Parcelable 接口

## 使用AIDL

>  Messenger 采用串行的方式来处理客户端信息，因此遇到并发请求时，就不合适了。
而且 Messenger 多用于 传递消息，跨进程调用服务端方法时 也不合适、

### AIDL使用步骤

- Service 端:
> 创建 AIDL文件，在AIDL文件中声明暴露给客户端的接口
在 Service 中实现这个 AIDL 接口。

- Client 端：
> 绑定服务端 Service，
将服务器返回的Binder对象转换成 AIDL接口所属类型，即可以调用AIDL中的方法

> 需要注意的是，AIDL的包结构在服务端和客户端要保持一致，否则运行会出错。



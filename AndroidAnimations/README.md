
AndroidViewAnimations
---------------------

系列博客

- [Android动画](http://blog.csdn.net/wbwjx/article/category/6063740)


## 触觉反馈`ripple`效果

## 布局动画

## 视图(View)动画(Animation)

### 帧动画,(DrawableAnimation)

## 属性动画(Animator)


注意: 属性必须要有get/set方法,内部使用反射.如果没有可以定义包装类,间接提供set/get


## Activity切换动画

在`API>=21`后的窗口切换:

官方默认提供了`Explode`,`Slide`,`Fade`三种变换.

## 自定义动画

继承Animation,实现 `initialize` 和 `applyTransformation`,其中 `initialize`主要做初始化操作,
applyTransformation方法的第一个参数interpolatedTime是插值器的时间因子，取值在0到1之间；
第二个参数Transformation是矩阵的封装类，一般使用它来获得当前的矩阵Matrix对象，然后对矩阵进行操作，就可以实现动画效果了。

## Camera

封装了OpenGL的3D动画,通过Camera即可实现3d动画效果.

## SVG

Android 5.X 后的矢量动画机制 .

[daimajia/AndroidViewAnimations](https://github.com/daimajia/AndroidViewAnimations)
[Android样式的开发:View Animation篇](http://keeganlee.me/post/android/20151003)
[Android样式的开发:Property Animation篇](http://keeganlee.me/post/android/20151026)

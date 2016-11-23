
AndroidViewAnimations
---------------------
## 触觉反馈

在Android L上实现`ripple`效果

* 在视图范围内展示波纹效果
` android:background="?android:attr/selectableItemBackground"`

* 波纹在接触点开始，之后填充整个视图背景
` android:background="?android:attr/selectableItemBackgroundBorderless"`

## 布局动画
LayoutAnimation: 初始化时设置给ViewGroup的动画

LayoutTransition : ViewGroup变化的时候的动画.
通过`mTransitioner.addTransitionListener(LayoutTransition.TransitionListener)`来设置监听器

## 视图(View)动画(Animation)

透明度(AlphaAnimation)、
旋转(RotateAnimation)、
缩放(ScaleAnimation)、
位移(TranslateAnimation)
动画集合(AnimationSet),其中android:shareInterpolator表示集合中的动画是否共享同一个插值器.若集合不指定,则子动画需单独指定,不指定则使用默认值.
动画监听器(AnimationListener)

原理: ViewGroup的drawChild方法获取该View的Transformation值,然后调用`canvas.concat(transformationToApply.getMatrix())`通过矩阵运算完成动画,如果动画尚未完成,继续调用`invalidate`启动下次绘制,从而完成动画的绘制.
缺点: 元素发生视图动画后,响应事件的位置还停留在原来的地方

帧动画,(DrawableAnimation)

## 属性动画(Animator)

属性动画默认时间间隔是300ms，默认帧率是10ms/帧.
android:repeatMode,repeat:连续重复播放，reverse:逆向重复播放
Interpolator: 插值器和估值器
属性动画需要运行在有Looper的线程中，反射调用get/set方法

### ObjectAnimator:
通过静态工程直接返回一个ObjectAnimator对象

```java
ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 300);
animator.setDuration(1000);
animator.start();
```

#### API:

位移(translationX,translationY)
旋转(rotation、rotationX,rotationY)
缩放(scaleX,scaleY)
支点(pivotX,pivotY)
坐标(x,y)
透明度(alpha:默认是1(不透明))
动画集合(AnimatorSet)

控制动画播放顺序(animatorSet.play().with().before().after()、playTogether、playSequentially)
简单动画集合(PropertyValueHolder)
动画监听器(AnimatorListener,AnimatorListenerAdapter)

注意: 属性必须要有get/set方法,内部使用反射.如果没有可以定义包装类,间接提供set/get

```java
public class WrapperView {

    private View mView;

    public WrapperView(View mView){
        this.mView = mView;
    }

    public int getWidth(){
        return mView.getLayoutParams().width;
    }

    public void setWidth(int width){
        mView.getLayoutParams().width = width;
        mView.requestLayout();
    }
}
```

### ValueAnimator

ObjectAnimator 继承自 ValueAnimator,ValueAnimator不提供任何动画效果,只是一个数值产生器.
产生具有一定规律的数字,调用者通过`AnimatorUpdateListener`监听数值变化,从而控制动画

```java
ValueAnimator animator = ValueAnimator.ofFloat(0,100);
animator.setTarget(view);
animator.setDuration(1000);
animator.start();
animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Float value = (Float) animation.getAnimatedValue();
        //do the animation!
    }
});
```

### xml和代码使用的异同

需要放在`res/animator`下,放在`anim`下不行

```java
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="4000"
    android:propertyName="rotation"
    android:valueFrom="0"
    android:valueTo="360" />

Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_rotation);
animator.setTarget(view);
animator.start();
```

### View Property Animator

3.0后View新增了animate()方法,是属性动画的简写方式

```java
imageView.animate().alpha(0).y(100).setDuration(1000)
        .setListener(new Animator.AnimatorListener() {
//...
        });
```

- 注意
这里其实可以不用调用start方法,在链式调用结束,默认就会开启动画.
此种方法不能设置多个属性值,不像ObjectAnimator可以设置中间值.

## Activity切换动画

之前的方法:
1. overridePendingTransition(int inAnim, int outAnim);
2. manifest清单文件的主题设置

在`API>=21`后的窗口切换:

官方默认提供了`Explode`,`Slide`,`Fade`三种变换.

通过如下方式开启

```java
ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(xxxActivity.this);
startActivity(intent, transitionActivity.toBundle());
```

通过如下方式来结束

```java
ActivityCompat.finishAfterTransition(this);
```

可以设置四种方式:
包括 `makeCustomAnimation`,`makeScaleUpAnimation`,`makeThumbnailScaleUpAnimation`,`makeSceneTransitionAnimation`


## 自定义动画

继承Animation,实现 `initialize` 和 `applyTransformation`,其中 `initialize`主要做初始化操作,
applyTransformation方法的第一个参数interpolatedTime是插值器的时间因子，取值在0到1之间；
第二个参数Transformation是矩阵的封装类，一般使用它来获得当前的矩阵Matrix对象，然后对矩阵进行操作，就可以实现动画效果了。

## Camera

封装了OpenGL的3D动画,通过Camera即可实现3d动画效果.

## SVG

Android 5.X 后的矢量动画机制 .

## 注意事项

尽量避免使用帧动画,或者较大的图片,防止OOM
在Activity退出后,无限循环动画需要停止掉,防止内存泄漏
注意3.0以下属性动画的兼容性问题
View动画是对view的影像做动画,如果动画完成之后setVisibility(View.GONE)失效,需要调用view.clearAnimation()
不要使用px
在android3.0以前的系统上，view动画和属性动画，新位置均无法触发点击事件，同时，老位置仍然可以触发单击事件。从3.0开始，属性动画的单击事件触发位置为移动后的位置，view动画仍然在原位置；
使用动画的过程中，建议开启硬件加速

[daimajia/AndroidViewAnimations](https://github.com/daimajia/AndroidViewAnimations)
[Android样式的开发:View Animation篇](http://keeganlee.me/post/android/20151003)
[Android样式的开发:Property Animation篇](http://keeganlee.me/post/android/20151026)

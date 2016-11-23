探索Android中有意义的动画
----------------
## 触觉反馈

* 在视图范围内展示波纹效果
` android:background="?android:attr/selectableItemBackground"`

* 波纹在接触点开始，之后填充整个视图背景
` android:background="?android:attr/selectableItemBackgroundBorderless"`

## View Property Animator

`API>12`引入,并行执行动画

```java
view.animate().x()...start();
```

为了兼容性,实现`API >= 4` 的`ViewPropertyAnimator`,可使用`ViewCompat`

```java
ViewCompat.animate(view).x()...;
```

## Object Animator

`ObjectAnimator`可以通过代码和`xml`生成,和 `ViewPropertyAnimator`不同的是:

* 一个实例只能对单一属性执行动画
* 允许自定义属性的动画

1. 我们可以使用`Evaluator`来计算动画执行中的属性值.
2. 同时可以使用`View`给我们提供好的`Wrapper`属性,如`View.SCALE_X` 和 `View.SCALE_Y`
3. 可以使用 `Interpolator`来定义动画的变化率.改变速度,加速度的行为

## Circular Reveal

使用剪切的圆形显示或隐藏一组 `UI` 元素,

```java
//android.view.ViewAnimationUtils
createCircularReveal(View view,
            int centerX,  int centerY, float startRadius, float endRadius){
//...
}
```

## 窗口转换

在`API>=21`起加入.可定制转换包括:

* enter –决定活动视图如何进入场景；
* exit -决定活动视图如何退出场景；
* reenter –决定活动视图退出后如何再度进入；
* shared elements –决定活动间如何共享视图转换。

通过`getWindow()`来设置.

官方默认提供了`Explode`,`lide`,`Fade`三种变换.

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

同时支持自定义的`Content动画`,定义如下.

```java
//自定义动画转换,和`overridePendingTransition`类似
ActivityOptionsCompat makeCustomAnimation(Context context,
            int enterResId, int exitResId);

// 放大向上效果转换
ActivityOptionsCompat makeScaleUpAnimation(View source,
            int startX, int startY, int startWidth, int startHeight);
 
//缩略图放大效果转换            
ActivityOptionsCompat makeThumbnailScaleUpAnimation(View source,
            Bitmap thumbnail, int startX, int startY);
  
//单个共享元素转换            
ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity,
            View sharedElement, String sharedElementName);

//多个共享元素转换                                            
ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity,
            Pair<View, String>... sharedElements);                       
```


## 优化转换

- 包括主题下设置`允许窗口页面转换`
- 启用/禁用转换重叠
- 排除特定视图转换,加快过渡
- 工具栏和操作栏,确保转换中的两个`Activity`使用相同的组件
- 转换持续时间,`200-500ms`最为合适


## 共享元素

如上`第四种,第五种`提供了共享元素转换的方式,需要设置`transitionName`来开启.
对于目标视图可以使用`slide.addTarget(int id)` 来设置入场动画.

## 自定义转换

主要步骤包括:

- 首先创建一个 `SharedTransition`，传入压缩视图与转换名称以引用共享组件。
- 然后创建 `ArcMotion` 实例，使两个视图转换时形成曲线动画效果。
- 接下来扩展 `ChangeBounds` 以创建自定义转换.

## AnimatedVectorDrawable 

定义`vector`标签,这里包含`Path`绘制,`group`组,`target`的概念,动画采用`ObjectAnimator`即可.


参考:

[Exploring Meaningful Motion on Android](https://labs.ribot.co.uk/exploring-meaningful-motion-on-android-1cd95a4bc61d#.ft3xgu3n5)

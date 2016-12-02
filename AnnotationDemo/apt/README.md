## 功能
一个编译时注解 `@Layout`,可以用在`Activity`,'Fragment`,`View`上。
使用参考：[mzule/LayoutAnnotation](https://github.com/mzule/LayoutAnnotation)

## 集成

``` groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}
```
``` groovy
apply plugin: 'android-apt'

dependencies {
    compile 'com.bobomee.android:layout-api:1.0.0'
    apt 'com.bobomee.android:layout-compiler:1.0.0'
}
```

## 混淆配置

``` groovy
-keep com.bobomee.android.layout.** { *; }
```

## 许可

Apache License  2.0


## Thanks:

[mzule/LayoutAnnotation](https://github.com/mzule/LayoutAnnotation)


## publish

> ./gradlew install --> ./gradlew :apt:layout-api:bintrayUpload 

> ./gradlew install --> ./gradlew :apt:layout-compiler:bintrayUpload

> ./gradlew install --> ./gradlew :apt:layout-annotation:bintrayUpload

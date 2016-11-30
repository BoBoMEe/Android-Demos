package com.bobomee.android.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2016/11/23.下午8:33.
 *
 * @author bobomee.
 */

@Retention(RetentionPolicy.CLASS) @Target(ElementType.TYPE) public @interface Layout {
  int value();
}

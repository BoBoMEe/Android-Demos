package com.bobomee.android.layout;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created on 2016/11/23.下午8:41.
 *
 * @author bobomee.
 */

@AutoService(Processor.class) public class Compiler extends AbstractProcessor {
  private Filer filer;

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    filer = processingEnv.getFiler();
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(Layout.class.getCanonicalName());
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    Set<? extends Element> types = roundEnvironment.getElementsAnnotatedWith(Layout.class);

    CodeBlock.Builder code = CodeBlock.builder().addStatement("map = new java.util.HashMap()");
    for (Element e : types) {
      int layout = e.getAnnotation(Layout.class).value();
      ClassName className = ClassName.get((TypeElement) e);
      code.addStatement("map.put(" + className + ".class," + layout + ")");
    }

    MethodSpec.Builder method = MethodSpec.methodBuilder("getLayout")
        .returns(Integer.class)
        .addParameter(Class.class, "klass")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
        .addStatement("return (Integer)map.get(klass)");

    FieldSpec.Builder field =
        FieldSpec.builder(HashMap.class, "map", Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC);

    TypeSpec.Builder type = TypeSpec.classBuilder("Mapper")
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addField(field.build())
        .addStaticBlock(code.build())
        .addMethod(method.build());
    try {
      JavaFile.builder(getClass().getPackage().getName(), type.build()).build().writeTo(filer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }
}

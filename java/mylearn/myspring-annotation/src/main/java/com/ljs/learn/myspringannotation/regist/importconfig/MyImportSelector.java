package com.ljs.learn.myspringannotation.regist.importconfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

// 自定义需要导入的组件
public class MyImportSelector implements ImportSelector {
    // 返回需要导入的组件的**全类名数组**
    // 方法可以返回一个空数组，但是不能返回null，会产生空数组异常
    // AnnotationMetadata: 当前标注@Import注解的类的所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[] {
            "com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassA",
            "com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassC",
        };

    }
}

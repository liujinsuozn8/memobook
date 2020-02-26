package com.ljs.learn.reflect.structure;

import org.junit.Test;

import java.lang.annotation.Annotation;

public class ClassAnnotationTest {

    @Test
    public void test01(){
       Class clazz = Person.class;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        //output:
        //@com.ljs.learn.reflect.structure.MyAnnotation(value="personclass")
    }
}

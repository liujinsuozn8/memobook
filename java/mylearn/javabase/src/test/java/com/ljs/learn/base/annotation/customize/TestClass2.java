package com.ljs.learn.base.annotation.customize;

//写在类声明前
@Myannotation2
public class TestClass2<@Myannotation2 T> {// 写在泛型参数定义前
    //写在属性声明前
    @Myannotation2
    private String name;

    public static void main(String[] args) {
        TestClass2<@Myannotation2 String> t = null; // 写在类型前
        int a = (@Myannotation2 int) 2L; // 写在类型前

        @Myannotation2 int b = 10;
    }

    // 写在泛型参数定义前
    public static <@Myannotation2 E> void method(E t) {}

    //写在参数声明和异常声明前
    public static void test(@Myannotation2 String arg) throws @Myannotation2 Exception {}

}

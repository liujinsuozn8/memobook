package com.ljs.learn.reflect.structure;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

// 获取运行时类的方法结构
public class MethodTest {

    // 获取当前运行时类及其所有父类(直到Object为止)中声明为public的方法
    @Test
    public void test01() {
        Class clazz = Person.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        // output：
        // public java.lang.String com.ljs.learn.reflect.structure.Person.toString()
        // public int com.ljs.learn.reflect.structure.Person.compareTo(java.lang.String)
        // public int com.ljs.learn.reflect.structure.Person.compareTo(java.lang.Object)
        // public void com.ljs.learn.reflect.structure.Person.info()
        // public java.lang.String com.ljs.learn.reflect.structure.Person.display(java.lang.String)
        // public void com.ljs.learn.reflect.structure.Creature.eat()
        // public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
        // public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
        // public final void java.lang.Object.wait() throws java.lang.InterruptedException
        // public boolean java.lang.Object.equals(java.lang.Object)
        // public native int java.lang.Object.hashCode()
        // public final native java.lang.Class java.lang.Object.getClass()
        // public final native void java.lang.Object.notify()
        // public final native void java.lang.Object.notifyAll()
    }

    // 获取当前运行时类的全部方法(不包含父类)
    @Test
    public void test02() {
        Class clazz = Person.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        // output:
        // public java.lang.String com.ljs.learn.reflect.structure.Person.toString()
        // public int com.ljs.learn.reflect.structure.Person.compareTo(java.lang.String)
        // public int com.ljs.learn.reflect.structure.Person.compareTo(java.lang.Object)
        // public void com.ljs.learn.reflect.structure.Person.info()
        // public java.lang.String com.ljs.learn.reflect.structure.Person.display(java.lang.String)
        // private java.lang.String com.ljs.learn.reflect.structure.Person.show(java.lang.String)
        // private static void com.ljs.learn.reflect.structure.Person.showDesc()
    }

    // 获取方法声明的注解
    @Test
    public void test03() {
        Class clazz = Person.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation);
            }
        }

    }

    // 获取方法的：权限修饰符、返回值类型、方法名、参数列表(参数名)、异常
    @Test
    public void test04() {
        Class clazz = Person.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 权限修饰符
            System.out.println(method);
            System.out.print(Modifier.toString(method.getModifiers()) + "\t");

            // 返回值类型
            System.out.print(method.getReturnType() + "\t");

            // 方法名
            System.out.print(method.getName() + " (");

            // 参数列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.print(parameterType.getName() + ",");
            }
            System.out.print(")");

            // 异常
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes != null && exceptionTypes.length != 0) {
                System.out.print(" throws ");

                for (Class<?> exceptionType : exceptionTypes) {
                    System.out.print(exceptionType.getName() + ",");
                }
            }

            System.out.println("\n");
        }
    }

    // 操作运行时类的方法
    @Test
    public void test05() throws Exception{
        Class<Person> cls = Person.class;
        // 调用成员方法
        // 获取指定方法
        // getDeclaredMethod(方法名, 参数列表)
        Method show = cls.getDeclaredMethod("show", String.class);
        show.setAccessible(true);

        Person p = cls.newInstance();

        // invoke(方法的调用者，实参列表)
        Object showReturn = show.invoke(p, "testshow");
        System.out.println(showReturn);

        // 调用静态方法
        Method showDesc = cls.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        // 调用静态方法时，invoke的调用对象使用Class对象和null都可以
        Object returnShowDesc = showDesc.invoke(Person.class);
        //Object returnShowDesc = showDesc.invoke(null);
        System.out.println(returnShowDesc); // 输出：null
    }
}
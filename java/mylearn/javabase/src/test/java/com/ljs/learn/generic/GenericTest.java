package com.ljs.learn.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericTest {

    // 通配符测试
    @Test
    public void testWildCard(){
        List<Object> list1 = new ArrayList<>();
        list1.add("aaa");
        list1.add(123);
        list1.add("bbb");
        list1.add(456);

        List<String> list2 = new ArrayList<>();
        list2.add("qwer");
        list2.add("asdf");
        list2.add("zxcv");
        list2.add("tgbn");

        List<?> list = null;

        list = list1;
        show(list);
        System.out.println("------------------------");

        list = list2;
        show(list);
        System.out.println("------------------------");

        // 无法添加元素
        // list.add("aaa");

        // 可以添加null
        list.add(null);
        list.add(null);
        show(list);
        // output:
        // qwer
        // asdf
        // zxcv
        // tgbn
        // null
        // null
    }

    public void show(List<?> list){
        Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()){
            //迭代时，不能使用泛型通配符?，只能使用Object类型
            Object obj = iterator.next();
            System.out.println(obj);
        }

        for (Object o : list) {
            System.out.println(o);
        }
    }

    // 有条件限制的通配符测试:? extends type
    @Test
    public void testExtendsWildCard(){
        List<? extends Person> list1 = new ArrayList<>();

        List<Person> ps = new ArrayList<>();
        ps.add(new Person("aa", 11));
        ps.add(new Person("bb", 22));
        ps.add(new Person("cc", 33));
        ps.add(new Person("dd", 44));

        List<Student> ss = new ArrayList<>();
        ss.add(new Student( "sa", 12, "12345"));
        ss.add(new Student( "sb", 13, "12346"));
        ss.add(new Student( "sc", 14, "12347"));
        ss.add(new Student( "sd", 15, "12348"));

        // 迭代时，只能使用Person类型
        list1 = ps;
        // 无法添加数据
        // list1.add(new Student("add", 0, "0000"));
        // list1.add(new Person("add",0));
        for (Person person : list1) {
            System.out.println(person);
        }

        System.out.println("----------------");

        list1 = ss;
        // 无法添加数据
        // list1.add(new Student("add", 0, "0000"));
        // list1.add(new Person("add",0));
        for (Person person : list1) {
            System.out.println(person);
        }
    }

    // 有条件限制的通配符测试:? super type
    @Test
    public void testSuperWildCard(){
        List<? super Student> list1 = new ArrayList<>();

        List<Person> ps = new ArrayList<>();
        ps.add(new Person("aa", 11));
        ps.add(new Person("bb", 22));
        ps.add(new Person("cc", 33));
        ps.add(new Person("dd", 44));

        List<Student> ss = new ArrayList<>();
        ss.add(new Student( "sa", 12, "12345"));
        ss.add(new Student( "sb", 13, "12346"));
        ss.add(new Student( "sc", 14, "12347"));
        ss.add(new Student( "sd", 15, "12348"));

        // 迭代时，只能使用Object类型
        list1 = ps;
        // 添加数据，可以添加Student及其子类
        list1.add(new Student("add", 0, "0000"));
        list1.add(new CollegeStudent("add", 0, "0000", "testCollege"));
        for (Object o : list1) {
            System.out.println(o);
        }

        // output:
        // Person{name='aa', age=11}
        // Person{name='bb', age=22}
        // Person{name='cc', age=33}
        // Person{name='dd', age=44}
        // Student{studentID='0000'}
        // CollegeStudent{collegeName='testCollege'}

        System.out.println("----------------");

        list1 = ss;
        // 添加数据，可以添加Student及其子类
        list1.add(new Student("add", 0, "0000"));
        list1.add(new CollegeStudent("add", 0, "0000", "testCollege"));

        for (Object o : list1) {
            System.out.println(o);
        }
        //output:
        // Student{studentID='12345'}
        // Student{studentID='12346'}
        // Student{studentID='12347'}
        // Student{studentID='12348'}
        // Student{studentID='0000'}
        // CollegeStudent{collegeName='testCollege'}
    }
}

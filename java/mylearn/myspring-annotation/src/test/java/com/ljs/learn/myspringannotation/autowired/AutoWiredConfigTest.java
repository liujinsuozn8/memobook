package com.ljs.learn.myspringannotation.autowired;

import com.ljs.learn.myspringannotation.autowired.demo.BookDao;
import com.ljs.learn.myspringannotation.autowired.demo.BookService;
import com.ljs.learn.myspringannotation.autowired.demo02.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class AutoWiredConfigTest {
    // Autowired，自动注入测试
    @Test
    public void getService(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutoWiredConfig.class);
        BookService service = context.getBean(BookService.class);

        // 获取 bookDao
        BookDao dao = (BookDao) context.getBean("bookDao");
        // BookDao{name='null'}
        System.out.println(service.getBookDao());

        // 获取 bookDao02
        // 因为 bookDao02 设置了 @Primary，所以会优先使用
        BookDao dao2 =  context.getBean(BookDao.class);
        // BookDao{name='bookDao02'}
        System.out.println(dao2);

        assertEquals(service.getBookDao(), dao);
    }

    // @Autowired 的标记位置测试
    @Test
    public void autowiredLocation(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutoWiredConfig.class);
        // 通用bean
        Car car = context.getBean(Car.class);
        House house = context.getBean(House.class);
        Work work = context.getBean(Work.class);

        // 1. 获取person类及其内部装配的对象
        Person person = context.getBean(Person.class);
        assertEquals(person.getCar(), car);
        assertEquals(person.getHouse(), house);
        assertEquals(person.getWork(), work);

        // 2. @Autowired 标记在参数上，获取boss类及其内部装配的类
        Boss boss = context.getBean(Boss.class);
        assertEquals(boss.getCar(), car);
        assertEquals(boss.getWork(), null);

        // 3. 只有一个有参构造器，并且不适用 @Autowired，有Spring自动注入
        Teacher teacher = context.getBean(Teacher.class);
        assertEquals(teacher.getCar(), car);
        assertEquals(teacher.getWork(), work);

        // 4. 在配置类中使用: @Bean + 方法参数 时，可以省略@Autowired
        Engineer engineer = context.getBean(Engineer.class);
        assertEquals(engineer.getCar(), car);
        assertEquals(engineer.getWork(), work);
    }
}
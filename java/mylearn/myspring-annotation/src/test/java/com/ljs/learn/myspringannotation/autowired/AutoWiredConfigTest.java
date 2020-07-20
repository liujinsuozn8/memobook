package com.ljs.learn.myspringannotation.autowired;

import com.ljs.learn.myspringannotation.autowired.demo.BookDao;
import com.ljs.learn.myspringannotation.autowired.demo.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class AutoWiredConfigTest {
    @Test
    public void getService(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutoWiredConfig.class);
        BookService service = context.getBean(BookService.class);
        BookDao dao = context.getBean(BookDao.class);

        assertEquals(service.getBookDao(), dao);
    }
}
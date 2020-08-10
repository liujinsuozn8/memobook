package com.ljs.learn.myspringannotation.autowired.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Qualifier("bookDao") // 指定装配目标
    @Autowired
    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }
}

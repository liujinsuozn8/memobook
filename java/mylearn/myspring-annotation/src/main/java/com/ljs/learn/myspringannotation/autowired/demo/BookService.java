package com.ljs.learn.myspringannotation.autowired.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }
}

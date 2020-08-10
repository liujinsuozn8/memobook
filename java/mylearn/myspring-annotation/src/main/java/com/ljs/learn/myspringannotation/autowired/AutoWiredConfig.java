package com.ljs.learn.myspringannotation.autowired;

import com.ljs.learn.myspringannotation.autowired.demo.BookDao;
import com.ljs.learn.myspringannotation.autowired.demo02.Car;
import com.ljs.learn.myspringannotation.autowired.demo02.Engineer;
import com.ljs.learn.myspringannotation.autowired.demo02.Work;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.autowired.demo")
@ComponentScan("com.ljs.learn.myspringannotation.autowired.demo02")
public class AutoWiredConfig {
    @Primary    // 设置当前类为首选bean
    @Bean(name="bookDao02")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setName("bookDao02");
        return bookDao;
    }

    // 6. 在配置类中使用: @Bean + 方法参数 时，可以省略@Autowired
    @Bean
    public Engineer engineer(Car car, Work work){
        Engineer engineer = new Engineer();
        engineer.setCar(car);
        engineer.setWork(work);
        return engineer;
    }
}

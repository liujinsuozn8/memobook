package com.ljs.learn.myspringannotation.regist.importconfig;

import com.ljs.learn.myspringannotation.regist.importconfig.color.ColorBlue;
import com.ljs.learn.myspringannotation.regist.importconfig.color.ColorRed;
import com.ljs.learn.myspringannotation.regist.importconfig.person.Person;
import com.ljs.learn.myspringannotation.regist.importconfig.person.Student;
import com.ljs.learn.myspringannotation.regist.importconfig.person.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @Import(Student.class) // 只导入一个组件
// @Import({Student.class,Teacher.class}) // 导入多个组件
// @Import({Student.class, Teacher.class, MyImportSelector.class}) // 导入多个组件
@Import({Student.class, Teacher.class, MyImportSelector.class,
        ColorRed.class, ColorBlue.class, MyImportBeanDefinitionRegistrar.class}) // 导入多个组件 + 手动导入组件
public class ImportConfig {
    @Bean
    public Person person(){
        return new Person("test", 33);
    }
}

package com.ljs.learn.myspringannotation.regist.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {
    @Conditional({MacCondition.class})
    @Bean("mac")
    public Person person01(){
        return new Person("aaa", 11);
    }

    @Conditional({WinCondition.class})
    @Bean("window")
    public Person person02(){
        return new Person("bbb", 22);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linux")
    public Person person03(){
        return new Person("ccc", 33);
    }
}

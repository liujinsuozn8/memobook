package com.ljs.mavenlearn;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

// 测试依赖范围，但是并没有使用
import com.ljs.mavenlearn.Hello;

public class HelloFriendTest {
    @Test
    public void testHelloFriend(){
        HelloFriend helloFriend = new HelloFriend();
        String results = helloFriend.sayHelloToFriend("Tom");
        assertEquals("Hello Tom! I am John",results);	
    }
}
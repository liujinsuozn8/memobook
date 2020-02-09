package com.ljs.mavenlearn;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloTest {
	@Test
	public void testHello(){
		Hello hello = new Hello();
		String results = hello.sayHello("testUser");
		assertEquals("Hello testUser!",results);	
	}
}

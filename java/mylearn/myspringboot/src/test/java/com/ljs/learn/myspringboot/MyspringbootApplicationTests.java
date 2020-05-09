package com.ljs.learn.myspringboot;

import com.ljs.learn.myspringboot.bean.Person;
import com.ljs.learn.myspringboot.bean.Person02;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyspringbootApplicationTests {

	@Autowired
	private Person person;

	@Autowired
	private Person02 person02;

	@Test
	void testPerson() {
		System.out.println(person);
	}

	@Test
	void testPerson02(){
		System.out.println(person02);
	}

}

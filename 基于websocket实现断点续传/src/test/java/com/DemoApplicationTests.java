package com;

import com.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	FileService fileService;
	@Test
	void contextLoads() {
		System.out.println(fileService);
	}

}

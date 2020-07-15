package com.sampledocker;

import org.junit.jupiter.api.Test;
import com.sampledocker.controller.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleDockerApplicationTests {

	
	@Test
	void testTokenApi() {
		PrismaController object=new PrismaController();
		object.getToken();
	}

	@Test
	void testScanApi() {
		PrismaController object=new PrismaController();
		object.getScan();
	}
}

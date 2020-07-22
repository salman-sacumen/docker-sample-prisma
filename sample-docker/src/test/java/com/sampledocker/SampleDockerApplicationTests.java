package com.sampledocker;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import com.sampledocker.controller.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

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
	
	@Test
	void testExample() throws IOException, ParseException {
		PaloAltoController object=new PaloAltoController();
		ResponseEntity<String> aa = object.getJsonResponse();
		
	}
}

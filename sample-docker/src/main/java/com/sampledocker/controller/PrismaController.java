package com.sampledocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrismaController {

	private static final Logger log = LoggerFactory.getLogger(PrismaController.class);
	Request ob = new Request();

	@GetMapping("/login") 
	public String getToken() {

		try {
			String token = ob.connectLoginCheck();
			log.info("Token received "+token);
			return token;
		} catch (Exception e) {
			log.debug("Exception had been occoured");
		}
		return null;
	}

	@GetMapping("/iac/tf/v1/scan") 
	public String getScan() {
		String token = getToken();
		
		String message  = ob.scanV1(token);
		
		return message;
	}

}

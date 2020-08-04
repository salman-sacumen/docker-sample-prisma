package com.sampledocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	static final Logger log = LoggerFactory.getLogger("MainController.class");

	@GetMapping("/index")
	public String index() {
		log.info("index api triggered ");
		return "Hello World";
	}

	@GetMapping(path = "/index/{name}")
	public String indexValue(@PathVariable("name") String name) {
		log.info("name api triggered ");
		return "Hello " + name;
	}

}

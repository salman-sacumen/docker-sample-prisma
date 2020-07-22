package com.sampledocker.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@RestController
public class PaloAltoController {

	private static final Logger log = LoggerFactory.getLogger(PrismaController.class);

	@GetMapping("/checkMe")
	public ResponseEntity<String> test() throws IOException, ParseException {
		return getJsonResponse();
	}

	public ResponseEntity<String> getJsonResponse() {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Content-Type","application/json");
	    String jsonString="";
	    try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("app_descriptor.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
			jsonString = mapper.writeValueAsString(jsonNode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    
	    return ResponseEntity.ok()
	      .headers(responseHeaders)
	      .body(jsonString);
	
		
	}
}

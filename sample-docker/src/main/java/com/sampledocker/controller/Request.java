package com.sampledocker.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

public class Request {

	private static final Logger log = LoggerFactory.getLogger(Request.class);

	public String connectLoginCheck() {
		try {
			CloseableHttpClient httpClient = getHttpClient();

			HttpPost httpPost = new HttpPost(Constants.TOKEN_URL);
			String reqBody = constructRequestBodyForTokenAPI();
			httpPost.addHeader("Content-Type", "application/json");
			log.info("Request Body : " + reqBody);
			httpPost.setEntity(new StringEntity(reqBody));
			CloseableHttpResponse response = httpClient.execute(httpPost);

			int responseStatus = response.getStatusLine().getStatusCode();

			String responseBody = response.getEntity() == null ? "" : EntityUtils.toString(response.getEntity());

			if (responseStatus == HttpStatus.SC_OK) {
				log.info("Successful Adding");
			}
			log.info("Response Body: " + responseBody);

			try {
				JSONObject jsonObject = new JSONObject(responseBody);
				return jsonObject.get("token").toString();

			} catch (JSONException err) {
				log.info("Error", err.toString());
			}

		} catch (Exception e) {
			log.info("Error during token execution");
		}
		return null;
	}

	public void scanV1(String token) {
		try {
			CloseableHttpClient httpClient = getHttpClient();

			HttpPost httpPost = new HttpPost(Constants.SCAN_URL);

			HttpEntity multiPartEntity = constructRequestBodyForScanAPI();
			httpPost.setEntity(multiPartEntity);

			httpPost.addHeader("Content-Type", "multipart/form-data");
			httpPost.addHeader("x-redlock-auth", token);
			CloseableHttpResponse response = httpClient.execute(httpPost);

			int responseStatus = response.getStatusLine().getStatusCode();
			log.info("response status code " + responseStatus);
			String responseBody = response.getEntity() == null ? "" : EntityUtils.toString(response.getEntity());
			log.info("Response Body: " + responseBody);

		} catch (Exception e) {

		}
	}

	private HttpEntity constructRequestBodyForScanAPI() throws FileNotFoundException, UnsupportedEncodingException {

		File file = new File(getClass().getClassLoader().getResource("ex4.tf").getFile());

//		1
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("templateFile", file);
		builder.addTextBody("templateFile", "templateFile");
		HttpEntity entity = builder.build();
		return entity;

// 		2
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		builder.addBinaryBody("templateFile", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM, file.getName());
//		HttpEntity entity = builder.build();
//		return entity;

//		3
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		FileBody fb = new FileBody(file);
//		builder.addPart("templateFile", fb);
//		HttpEntity entity = builder.build();
//		return  entity;

//		4
//		FileBody data = new FileBody(file);
//		String file_type = "tf";
//		String description = "templateFile";
//		String folder_id = "-1";
//		String source = "MYCOMPUTER";
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		builder.addPart("file_name", new StringBody(file.getName()));
//		builder.addPart("folder_id", new StringBody(folder_id));
//		builder.addPart("description", new StringBody(description));
//		builder.addPart("source", new StringBody(source));
//		builder.addPart("file_type", new StringBody(file_type));
//		builder.addPart("data", data);
//		HttpEntity entity = builder.build();
//		return  entity;
	}

	private String constructRequestBodyForTokenAPI() {
		// Request Body
		JsonObject requestJson = new JsonObject();
		requestJson.addProperty("username", Constants.SECRET_KEY);
		requestJson.addProperty("password", Constants.PASSWORD);
		return requestJson.toString();
	}

	private CloseableHttpClient getHttpClient() throws GeneralSecurityException {

		log.debug("requesting http client connection client open");

		HttpClientBuilder clientBuilder = HttpClients.custom();
		if (clientBuilder == null) {
			return null;
		}
		return clientBuilder.build();
	}

}

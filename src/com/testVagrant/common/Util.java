package com.testVagrant.common;

import java.io.File;

import io.restassured.path.json.JsonPath;



public class Util {
	JsonPath jsonPath ;
	/**
	 * Returns the JsonPath for the Json file
	 * @param fileName
	 * @return
	 */
	public JsonPath getJsonPath(String fileName) {
		jsonPath = new JsonPath(new File(fileName));
		return jsonPath;
	}
}

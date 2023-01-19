package com.testVagrant.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.path.json.JsonPath;

public class Util {
	JsonPath jsonPath;

	/**
	 * Returns the JsonPath for the Json file
	 * 
	 * @param fileName
	 * @return
	 */
	public JsonPath getJsonPath(String fileName) {
		jsonPath = new JsonPath(new File(fileName));
		return jsonPath;
	}

	/* This is for Reporting using Extent Report */
	private static final String OUTPUT_FOLDER = "./reports/";
	private static final String FILE_NAME = "TestExecutionReport.html";
	// private static ExtentReports extent = init();
	// public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;

	public static ExtentReports extentReport() {

		Path path = Paths.get(OUTPUT_FOLDER);
		// if directory exists?
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// fail to create directory
				e.printStackTrace();
			}
		}

		extentReports = new ExtentReports();

		ExtentHtmlReporter reporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		reporter.config().setReportName(" Automation Test Results");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("System", "Windows");
		extentReports.setSystemInfo("Author", "Sridhar");
		/*
		 * extentReports.setSystemInfo("Build#", "1.1");
		 * extentReports.setSystemInfo("Team", "---");
		 * extentReports.setSystemInfo("Customer Name", "NAL");
		 */

		// extentReports.setSystemInfo("ENV NAME", System.getProperty("env"));

		return extentReports;
	}
}

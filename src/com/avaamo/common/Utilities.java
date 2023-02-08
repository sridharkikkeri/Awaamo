package com.avaamo.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Utilities {

	String loc;
	static Properties configProperties;
	String key2 = null;
	static FileInputStream fileInputStream = null;

	public static void loadPropertyFiles(){
		String fileName = getProjectPath() + File.separator + "config" + File.separator + "config.properties";
		loadConfigFile(fileName);

	}

	/**
	 * This will load the Property File
	 * @param configFile
	 */
	public static void loadConfigFile(String configFile) {
		try {
			System.out.println(configFile);
			configProperties = new Properties();
			fileInputStream = new FileInputStream(configFile);
			configProperties.load(fileInputStream);
			configProperties.forEach((key, value) -> System.out.println("Key : " + key + ", Value : " + value));
		} catch (IOException ex) {
			System.out.println("Not able to find : " + configFile);
		}

	}

	/**
	 *  This will return the value from Property file
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public static String getVal(String val) throws Exception {
		return configProperties.getProperty(val);
	}

	/**
	 * This will return the project Path
	 * @return
	 */
	public static String getProjectPath() {
		return System.getProperty("user.dir");
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
		reporter.config().setReportName("Automation Test Results");
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
	
	
	/**
	 * This will compare 2 images
	 * @param filePath1
	 * @param filePath2
	 * @param percentValue
	 * @return
	 * @throws Exception
	 */
    public boolean compareImages( String filePath1, String filePath2, double percentValue) throws Exception {
        System.out.println("Expected Path : " + filePath1);
        System.out.println("Actual Path : " + filePath2);
        boolean isExceeded = true;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
            File file1 = new File(filePath1);
            File file2 = new File(filePath2);

            img1 = ImageIO.read(file1);
            img2 = ImageIO.read(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
        	 System.out.println("Error: Images dimensions mismatch");
            return false;
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        if (p > percentValue) {
            isExceeded = false;
        }
        System.out.println("diff percent: " + (p * 100.0));
        return isExceeded;
    }
}

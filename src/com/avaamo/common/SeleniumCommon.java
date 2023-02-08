package com.avaamo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class SeleniumCommon {
	static String projectPath;
	public static WebDriver driver;
	public static String BROWSER_NAME;
	public static String BASE_URL;
	public static int WAIT_TIME;
	static String configFileName;
	static String geckoDriverPath;
	static String chromeDriverPath;
	public static WebDriverWait wait;

	public SeleniumCommon() {
		loadProperties();

	}

	/**
	 * This will load the property files
	 */
	private static void loadProperties() {

		projectPath = Utilities.getProjectPath();
		configFileName = projectPath + File.separator + "config" + File.separator + "config.properties";
		geckoDriverPath = projectPath + File.separator + "drivers" + File.separator + "geckodriver" + File.separator
				+ "geckodriver.exe";
		chromeDriverPath = projectPath + File.separator + "drivers" + File.separator + "chromedriver" + File.separator
				+ "chromedriver.exe";
		Utilities.loadConfigFile(configFileName);
	}

	/**
	 * This will return the launch the browser
	 * 
	 * @return
	 * @throws Exception
	 */
	public WebDriver launchBrowser() throws Exception {
		BROWSER_NAME = Utilities.getVal("browser");
		BASE_URL = Utilities.getVal("url");
		WAIT_TIME = Integer.parseInt(Utilities.getVal("waitTime"));
		if (BROWSER_NAME.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", geckoDriverPath);
			driver = new FirefoxDriver();
		}

		if (BROWSER_NAME.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);

			driver = new ChromeDriver();
			System.out.println("browser........ " + chromeDriverPath);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30l));
		wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
		return driver;

	}

	/**
	 * This will return a driver Object
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}

	// Click Method
	public void click(By by) {
		waitVisibility(by).click();
	}
	
	// Click Method
	public void click(WebElement we) throws InterruptedException {
		waitVisibility(we).click();
		Thread.sleep(1000);
	}
	
	// Click Method
	public void clickWithWait(WebElement we,int timeout) throws Exception {
		waitForDisplay(driver,we,timeout);
		we.click();
	}
	
	// Click Method
	public void clickWithWait(By by,int timeout) throws Exception {
		WebElement we = driver.findElement(by);
		waitForDisplay(driver,we,timeout);
		we.click();
	}
	
	// Click Method
	public void switchToFrame(WebElement we) {
		waitVisibility(we);
		driver.switchTo().frame(we);
	}

	// Write Text
	public void type(By by, String text) {
		waitVisibility(by).sendKeys(text);
	}
	
	// Write Text
	public void type(WebElement we, String text) {
		waitVisibility(we).sendKeys(text);
	}
	
	/**
	 * 
	 * @param we
	 * @param filePath
	 * @throws IOException
	 */
	public void takeScreenShot(WebElement we, String filePath) throws IOException {
		File file=we.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(filePath));
	}


	/**
	 * This will return a Webelement
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getWebElement(By locator) {
		return waitVisibility(locator);
	}

	

	/**
	 * This will return a List of Webelement
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> getWebElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * This will return the text of a WebLelment
	 * 
	 * @param locator
	 * @return
	 */
	public String getText(By locator) {
		return waitVisibility(locator).getText();
	}

	/**
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitVisibility(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	
	/**
	 * 
	 * @param we
	 * @return
	 */
	public WebElement waitVisibility(WebElement we) {
		return wait.until(ExpectedConditions.visibilityOf(we));
	}

	/**
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitVisibility(String locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}
	
	
	

	/**
	 * This will HighLight the Element
	 * 
	 * @param driver
	 * @param Element
	 */
	public void highLightElement(WebDriver driver, By Element) {
		WebElement emailTxt = driver.findElement(Element);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].style.border='2px solid red'", emailTxt);
	}

	/**
	 * Wait for element to displayed till the given wait time
	 * 
	 * @param driver
	 * @param locator
	 * @param iWaitSeconds
	 * @return
	 */
	public boolean waitForDisplay(WebDriver driver, By locator, int iWaitSeconds) {
		// Implements wait time as a multiple of 1 second

		WebElement webelement;
		for (int second = 0; second < iWaitSeconds; second++) {

			try {
				webelement = driver.findElement(locator);

				if (webelement.isDisplayed()) {
					if (webelement.getSize().getWidth() > 0) {
						return true;
					}
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * This will check if the element is present on the DOM
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This will check if the element is Visible on the DOM
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

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
		reporter.config().setReportName("Ebay Automation Test Results");
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
	 * 
	 * @param driver
	 * @param webelement
	 * @param timeInSec
	 * @return
	 * @throws Exception
	 */
	
    public boolean waitForDisplay(WebDriver driver, WebElement webelement, int timeInSec) throws Exception {
        //Implements wait time as a multiple of 1 second
        final long threadSleep = 1000;

       
        for (int second = 0; second < timeInSec; second++) {

            try {
               
                if (webelement.isDisplayed()) {
                    if (webelement.getSize().getWidth() > 0) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}

package com.avaamo.test;

import java.io.File;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.avaamo.common.SeleniumCommon;
import com.avaamo.common.Utilities;
import com.avaamo.data.ChatAgentData;
import com.avaamo.listeners.ITestListenerImpl;
import com.avaamo.pages.ChatAgentPage;
import com.avaamo.pages.HomePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class testChatAgent extends SeleniumCommon implements ChatAgentData {

	private Utilities _util;
	HomePage homePage;
	ChatAgentPage chatAgentPage;
	String projectPath;
	String dataPath;

	@BeforeClass
	public void setUp() throws Exception {
		_util = new Utilities();
		Utilities.loadPropertyFiles();
		projectPath = Utilities.getProjectPath();
		dataPath = Utilities.getVal("dataFolderPath");
	}
	
	@BeforeMethod
	public void launchBrowserAndNavigate() throws Exception {
		driver = launchBrowser();
		driver.get(BASE_URL);
	}

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}

	@Test(description = "testing the Pizza Order for Veg")
	public void testOrderPizzaFlowForVeg() throws Exception {
		String expectedPath = projectPath + File.separator + dataPath + "expected";
		String actualPath = projectPath + File.separator + dataPath + "actual";
		String firstName = Utilities.getVal("firstName");
		String email = Utilities.getVal("email");
		ITestListenerImpl.extentTest.log(Status.INFO,
				MarkupHelper.createLabel(
						"****************************** Testing the Pizza Order for Veg ******************************",
						ExtentColor.BLUE));
		homePage = PageFactory.initElements(driver, HomePage.class);
		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on Chat Agen Icon");
		homePage.clickChatAgentButton();
		chatAgentPage = PageFactory.initElements(driver, ChatAgentPage.class);
		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on Get Started Link ");
		chatAgentPage.clickGetStartedLink();
		ITestListenerImpl.extentTest.log(Status.INFO,
				"Entering login details, First Name : " + firstName + " email : " + email);
		chatAgentPage.enterLoginDetails(firstName, email);
		ITestListenerImpl.extentTest.log(Status.INFO, MarkupHelper.createLabel(
				"************ Verifying Welcome to Pizza Text is Displayed ************", ExtentColor.GREEN));
		Assert.assertTrue(chatAgentPage.isWelcomeToPizzaTextDisplayed());
		ITestListenerImpl.extentTest.log(Status.INFO, "Sending the Query : " + QUERY1);
		chatAgentPage.sendQuery(QUERY1);
		
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting Veg Pizza");
		chatAgentPage.selectVegPizza();
		
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting the Topping " + TOPPING);
		chatAgentPage.selectToppingAndSubmit(TOPPING);
		
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting the Crest " + THICK_CREST);
		chatAgentPage.selectCrustAndSubmit(THICK_CREST);
		
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting the Size " + MEDIUM);
		chatAgentPage.selectPizzaSizeAndSubmit(MEDIUM);
		
		chatAgentPage.WaitForYesConfirmation(YES);
		chatAgentPage.takeOrderSummaryScreenShot(actualPath + File.separator + PIZZA_ORDER_FILE);
		boolean b = _util.compareImages(expectedPath + File.separator + PIZZA_ORDER_FILE,
				actualPath + File.separator + PIZZA_ORDER_FILE, .05);
		ITestListenerImpl.extentTest.log(Status.INFO,
				MarkupHelper.createLabel("************ Verifying the placed Order ************", ExtentColor.GREEN));
		Assert.assertTrue(b);

		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on confirmation :  " + YES);
		chatAgentPage.WaitForYesConfirmation(YES);
		chatAgentPage.sendQuery(YES);
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting ThumsUp  ");
		chatAgentPage.selectThumsUp();
		ITestListenerImpl.extentTest.log(Status.INFO, "Selecting FeedBack  " + GOOD);
		chatAgentPage.submitFeedBack(GOOD);
		ITestListenerImpl.extentTest.log(Status.INFO, MarkupHelper
				.createLabel("************ Verifying Order Placed successful message ************", ExtentColor.GREEN));
		b = chatAgentPage.verifyOrderPlaced();
		Assert.assertTrue(b);
	}
	
	
	@Test(description = "testing with query New Pizza")
	public void testQueryNewPizza() throws Exception {
		String expectedPath = projectPath + File.separator + dataPath + "expected";
		String actualPath = projectPath + File.separator + dataPath + "actual";
		String firstName = Utilities.getVal("firstName");
		String email = Utilities.getVal("email");
		boolean b;
		ITestListenerImpl.extentTest.log(Status.INFO,
				MarkupHelper.createLabel(
						"****************************** Testing the Pizza Order for Veg ******************************",
						ExtentColor.BLUE));
		homePage = PageFactory.initElements(driver, HomePage.class);
		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on Chat Agen Icon");
		homePage.clickChatAgentButton();
		chatAgentPage = PageFactory.initElements(driver, ChatAgentPage.class);
		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on Get Started Link ");
		chatAgentPage.clickGetStartedLink();
		ITestListenerImpl.extentTest.log(Status.INFO,
				"Entering login details, First Name : " + firstName + " email : " + email);
		chatAgentPage.enterLoginDetails(firstName, email);
		ITestListenerImpl.extentTest.log(Status.INFO, MarkupHelper.createLabel(
				"************ Verifying Welcome to Pizza Text is Displayed ************", ExtentColor.GREEN));
		Assert.assertTrue(chatAgentPage.isWelcomeToPizzaTextDisplayed());
		ITestListenerImpl.extentTest.log(Status.INFO, "Sending the Query : " + QUERY2);
		chatAgentPage.sendQuery(QUERY2);
		b=chatAgentPage.isResponseDisplayed(QUERY2_RESPONSE);
		ITestListenerImpl.extentTest.log(Status.INFO, MarkupHelper.createLabel(
				"************ Verifying Response for query: " +  QUERY2 + " ************", ExtentColor.GREEN));
		Assert.assertTrue(b);
		ITestListenerImpl.extentTest.log(Status.INFO, "Clicking on Thats the not Link ");
		chatAgentPage.clickThatsNotLink();
		b=chatAgentPage.isResponseDisplayed(TRY_AGAIN_MSG);
		ITestListenerImpl.extentTest.log(Status.INFO, MarkupHelper.createLabel(
				"************ Verifying for Try Again: " +  QUERY2 + " ************", ExtentColor.GREEN));
		Assert.assertTrue(b);
	}

}

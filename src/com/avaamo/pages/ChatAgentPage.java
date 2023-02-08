package com.avaamo.pages;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.avaamo.common.SeleniumCommon;

public class ChatAgentPage extends SeleniumCommon {
	WebDriver driver;

	public ChatAgentPage(WebDriver driver) {
		this.driver = driver;
	}

	// Locating Get Started Link
	@FindBy(xpath = "//a[@class='get-started-link']")
	WebElement getStartedLink;

	@FindBy(id = "first_name")
	WebElement firstName;

	@FindBy(id = "email")
	WebElement email;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement submitBtn;

	@FindBy(xpath = "//iframe[contains(@src,'avaamo.com/web_channels')]")
	WebElement loginFrame;

	@FindBy(id = "queryTextbox")
	WebElement queryTextBox;

	@FindBy(xpath = "//button[text()='Send']")
	WebElement sendButton;

	@FindBy(xpath = "//a[@title='veg']")
	WebElement vegLink;

	String toppingCB = "//span[text()='TPNAME']/preceding-sibling::input";

	@FindBy(xpath = "//button[@class='btn default_card_submit']")
	WebElement toppingSubmit;

	@FindBy(xpath = "//p[contains(text(),'Welcome to McPizza Booking Journey')]")
	WebElement welcomeToPizzaText;

	@FindBy(xpath = "//p[contains(text(),'Your Pizza Will Look like this ')]")
	WebElement orderSummary;

	@FindBy(xpath = "//img[@alt='Thick Crust']")
	WebElement thickCrust;

	@FindBy(xpath = "//a[@title='Medium']")
	WebElement mediumSize;

	@FindBy(xpath = "//a[@title='Yes']")
	WebElement yesLink;

	@FindBy(xpath = "//button[@class='thumbs-up']")
	WebElement thumbsUp;

	@FindBy(xpath = "//button[@class='thumbs-down']")
	WebElement thumbsDown;
	
	@FindBy(xpath="//input[@role='combobox']")
	WebElement feedBackInput;
	
	@FindBy(xpath="//button[@class='btn default_card_submit']")
	WebElement feedBackSubmit;
	
	@FindBy(xpath="//p[contains(text(),'ORDER PLACED')]")
	WebElement orderPlacedMessage;
	
	@FindBy(xpath="//a[contains(@title,' not it')]")
	WebElement thatNotLink;
	
	String crustImg = "//a[text()='REPLACE']";
	String linkWithTitle = "//a[@title='REPLACE']";
	String feedBackOption = "//li[text()='REPLACE']";
	String responseStr = "//p[contains(text(),'REPLACE')]";

	/**
	 * Clicking on Get Started Link
	 * 
	 * @throws InterruptedException
	 */
	public void clickGetStartedLink() throws InterruptedException {
		click(getStartedLink);
	}

	/**
	 * Entering the Login Details
	 * 
	 * @param fName
	 * @param emailID
	 * @throws InterruptedException
	 */
	public void enterLoginDetails(String fName, String emailID) throws InterruptedException {
		switchToFrame(loginFrame);
		type(firstName, fName);
		type(email, emailID);
		click(submitBtn);
	}

	public void sendQuery(String query) throws InterruptedException {
		type(queryTextBox, query);
		click(sendButton);
	}

	public void selectVegPizza() throws InterruptedException {
		click(vegLink);
	}

	public void selectToppingAndSubmit(String toppingName) throws Exception {
		clickWithWait(By.xpath(toppingCB.replace("TPNAME", toppingName)), 10);
		click(toppingSubmit);
	}

	public void selectCrustAndSubmit(String crestName) throws InterruptedException {
		click(By.xpath(crustImg.replace("REPLACE", crestName)));
	}

	public void selectPizzaSizeAndSubmit(String pizzaSize) throws InterruptedException {
		click(By.xpath(linkWithTitle.replace("REPLACE", pizzaSize)));

	}
	
	public void selectThumsUp() throws Exception {
		clickWithWait(thumbsUp, 10);
	}
	
	public void selectThumsDown() throws Exception {
		clickWithWait(thumbsDown, 10);
	}

	public void submitFeedBack(String msg) throws Exception {
		clickWithWait(feedBackInput,10);
		clickWithWait(driver.findElement(By.xpath(feedBackOption.replace("REPLACE", msg))),19);
		clickWithWait(feedBackSubmit,10);
	}
	
	public boolean isWelcomeToPizzaTextDisplayed() {
		return waitVisibility(welcomeToPizzaText).isDisplayed();
	}

	public boolean waitForCrest(String crestName) throws InterruptedException {
		return waitVisibility(crustImg.replace("REPLACE", crestName)).isDisplayed();

	}

	public boolean waitForSize(String pizzaSize) throws InterruptedException {
		return waitVisibility(linkWithTitle.replace("REPLACE", pizzaSize)).isDisplayed();

	}

	public boolean WaitForYesConfirmation(String confirmation) throws InterruptedException {
		return waitVisibility(linkWithTitle.replace("REPLACE", confirmation)).isDisplayed();

	}

	public void takeOrderSummaryScreenShot(String filePath) throws IOException {
		waitVisibility( orderSummary);
		takeScreenShot(orderSummary, filePath);
	}
	
	
	public void takeOrderSubmittedMsgScreenShot(String filePath) throws IOException {
		takeScreenShot(orderPlacedMessage, filePath);
	}
	
	public boolean verifyOrderPlaced() throws IOException {
		return orderPlacedMessage.isDisplayed();
	}
	
	public boolean isResponseDisplayed(String respMsg) {
		return waitForDisplay(driver, By.xpath(responseStr.replace("REPLACE", respMsg)), 10);
	}
	
	public void clickThatsNotLink() throws Exception {
		clickWithWait(thatNotLink, 10);
	}


}

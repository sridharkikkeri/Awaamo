package com.avaamo.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.avaamo.common.SeleniumCommon;



public class HomePage extends SeleniumCommon {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	//Locating Chat Agent Icon
		@FindBy(xpath="//img[@alt='Chat agent button']")
		WebElement chatAgentBtn;
		
		public void clickChatAgentButton() throws InterruptedException {
			click(chatAgentBtn);
		}
}

package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FinanceManagerPage {

	private WebDriver driver;
	private WebDriverWait wdw; // Explicit waits
	
	@FindBy(xpath="//*[text()='Welcome to the FinanceManager homepage']")
	private WebElement welcomeHeading;
	
	public FinanceManagerPage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getWelcomeHeading() {
		return this.wdw.until(ExpectedConditions.visibilityOf(welcomeHeading));
	}
}

package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private WebDriver driver;
	private WebDriverWait wdw; // Explicit waits
	
	@FindBy(xpath="//input[@id='username']") // PageFactory annotation
	private WebElement usernameInput;
	
	@FindBy(id="password") // PageFactory annotation
	private WebElement passwordInput;
	
	@FindBy(id="login-btn") // PageFactory annotation
	private WebElement loginButton;
	
	@FindBy(xpath="//div[@id='login-info']/p")
	private WebElement errorMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;	
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before throwing an exception
		
		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getUsernameInput() {
		return this.usernameInput;
	}
	
	public WebElement getPasswordInput() {
		return this.passwordInput;
	}
	
	/*
	 * Let's pretend that the login button takes time to appear for some reason (it doesn't but we can pretend for this example)
	 * 
	 * - If an element takes time to appear, we need to utilize a wait (explicit or implicit) so that it doesn't throw an exception and complain
	 * 	that an element doesn't exist
	 * 
	 */
	public WebElement getLoginButton() {
		return this.loginButton;
	}
	
	public WebElement getErrorMessage() {
		return this.wdw.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}

}

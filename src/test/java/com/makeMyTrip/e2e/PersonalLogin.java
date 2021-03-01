package com.makeMyTrip.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import junit.framework.TestCase;

public class PersonalLogin extends TestCase{
	  WebDriver driver;
	  Properties properties = new Properties();
	  WebDriverWait wait;
	String userName = "", password = ""; boolean loginBoxEnabled = false;
	By accountButton = By.xpath("//li[@data-cy='account']"), userNameField=By.xpath("//input[@id='username']"), continueButton =By.xpath("//span[text()='Continue']"), passwordField =By.xpath("//input[@type='password']"), loginButton=By.xpath("//span[text()='Login']"),loginBox = By.xpath("//div[@class='loginModal displayBlock modalLogin dynHeight personal ']"), loginCard=By.xpath("//div[@class='login__card makeFlex hrtlCenter cursorPointer appendBottom10']");

	public void setUp() throws Exception {
		properties.load(new FileReader(new File("src/test/resources/test.properties")));
		System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
		userName = properties.getProperty("username");
		password = properties.getProperty("password");
	}

	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		try {
			WebElement d = find(loginBox);
			if (d != null) {
				find(loginCard).click();
				loginBoxEnabled = true;
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
			if(!loginBoxEnabled) {
			find(accountButton).click();
			}
			find(userNameField).sendKeys(userName);
			
			find(continueButton).click();
			
			find(passwordField).sendKeys(password);
			
			find(loginButton).click();
			Thread.sleep(5000);
	}
	
	WebElement find(By element){
		wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

}

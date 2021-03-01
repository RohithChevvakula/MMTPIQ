package com.makeMyTrip.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import junit.framework.TestCase;

public class PersonalLogin extends TestCase{
	 private WebDriver driver;
	 private Properties properties = new Properties();
	 protected WebDriverWait wait = new WebDriverWait(driver, 30);
	String userName = "", password = ""; boolean loginBoxEnabled = false;

	public void setUp() throws Exception {
		properties.load(new FileReader(new File("src/test/resources/test.properties")));
		System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		userName = properties.getProperty("username");
		password = properties.getProperty("password");
	}

	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		driver.get("https://www.makemytrip.com/");
		Thread.sleep(2000);
		try {
			WebElement d = driver.findElement(By.xpath("//div[@class='loginModal displayBlock modalLogin dynHeight personal ']"));
			if (d != null) {
				driver.findElement(By.xpath("//div[@class='login__card makeFlex hrtlCenter cursorPointer appendBottom10']")).click();
				loginBoxEnabled = true;
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
			if(!loginBoxEnabled) {
			Thread.sleep(2000);
			driver.findElement(By.xpath("//li[@data-cy='account']")).click();
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userName);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Continue']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Login']")).click();
			Thread.sleep(2000);
		
//		<input type="text" class="font14 fullWidth" id="username" autocomplete="off" placeholder="Enter email or mobile number" data-cy="userName" value="">
//		<input type="text" class="font14 fullWidth" id="username" autocomplete="off" placeholder="Enter email or mobile number" data-cy="userName" value="">

	}

}

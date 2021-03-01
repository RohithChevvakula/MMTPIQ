package com.makeMyTrip.e2e;


import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("deprecation")
public class MmtTicketBooking extends TestCase{
	private WebDriver driver;
	WebDriverWait wait;
	private Properties properties = new Properties();
	String departureDate="",returnDate="", source="", destination="", tripDateXpath = "//div[not(contains(@class,'--outside')) and @aria-label='#']",adultXpath = "//p[text()='ADULTS (12y +)']/../ul/li[@data-cy='adults-#']",childrenXpath = "//p[text()='CHILDREN (2y - 12y )']/../ul/li[@data-cy='children-#']";
	By flightsMenu = By.xpath("//*[@class='menu_Flights']"),typeOfTrip = By.xpath("//div[@class='makeFlex']/ul/li"),fromCity = By.xpath("//input[@id='fromCity']"),cityInputField = By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open react-autosuggest__input--focused']"),citySuggestions = By.xpath("//div[@class='react-autosuggest__section-container react-autosuggest__section-container--first']/ul/li"),loginBox = By.xpath("//div[@class='loginModal displayBlock modalLogin dynHeight personal ']"), todayDate = By.xpath("//div[@class='DayPicker-Day DayPicker-Day--today']"),noOfTravellersButton = By.xpath("//div[@data-cy='flightTraveller']"),classType = By.xpath("//ul[@class='guestCounter classSelect font12 darkText']/li[@data-cy='travelClass-0']"),applyButton = By.xpath("//button[text()='APPLY']"),searchButton = By.xpath("//a[text()='Search']"),bookButton = By.xpath("//button[(text()='Book Now')]"),continueButton = By.xpath("//button[text()='Continue']"),reviewPageHeader = By.xpath("//h4[text()='Review your booking']");

	public void setUp() throws Exception {
		properties.load(new FileReader(new File("src/test/resources/test.properties")));
		source = properties.getProperty("source"); destination = properties.getProperty("destination");
		System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
	}

	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	public void test() throws Exception {
		wait = new WebDriverWait(driver, 30);
		
		try { find(loginBox).click(); }
		catch(Exception e) { e.printStackTrace(); }
		
		find(flightsMenu).click();
		
		assertEquals("https://www.makemytrip.com/flights/", driver.getCurrentUrl());
		
		for (WebElement a : findAll(typeOfTrip)) {
			if (a.getText().toLowerCase().contains("round trip")) {
				a.click();
				break;
			}
		}
		
		find(fromCity).click();
		
		find(cityInputField).sendKeys(source);
		
		//auto suggestions for departure
		for(WebElement i : findAll(citySuggestions)) {
			if(i.getText().replaceAll("\\s", "").contains(source)) {
				i.click();
				break;
			}
		}
		
		find(cityInputField).sendKeys(destination);
		
		//auto suggestions for arrival
		for (WebElement i : findAll(citySuggestions)) {
			if (i.getText().replaceAll("\\s", "").contains(destination)) {
				i.click();
				break;
			}
		}
				
				String today = find(todayDate).getAttribute("aria-label");
				departureDate = noOfDaysFromToday(today, 2);
				
				By depDate = By.xpath(tripDateXpath.replace("#", departureDate));
				find(depDate).click();
				
				returnDate = noOfDaysFromToday(today, 3);
				By retDate = By.xpath(tripDateXpath.replace("#", returnDate));
				find(retDate).click();
				
				// click on number of travellers
				find(noOfTravellersButton).click();
				
				//number of adults  
				By noOfAdults = By.xpath(adultXpath.replace('#','1'));
				find(noOfAdults).click();
				
				//number of children 
				By noOfChildren = By.xpath(childrenXpath.replace('#','1'));
				find(noOfChildren).click();
				
				// Select Economy class
				find(classType).click();
				
				find(applyButton).click();
				
				find(searchButton).click();
				
				// select 2nd flight in departure flights list
				selectDepartureFlightNumber(2);
				
				// select 2nd flight in arrival flights list
				selectReturnFlightNumber(2);
				
				find(bookButton).click();
				
				String oldTab = driver.getWindowHandle();
				find(continueButton).click();
				
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
				newTab.remove(oldTab);
				driver.switchTo().window(newTab.get(0));
				
				assertNotNull(find(reviewPageHeader));
	}
	
	@Test
	public void afterTest(){
		
	}
	
	String noOfDaysFromToday(String oldDate, int noOfDaysFromToday) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
		Calendar c = Calendar.getInstance();
		try{
		   c.setTime(sdf.parse(oldDate));
		}catch(ParseException e){
			e.printStackTrace();
		 }
		   
		c.add(Calendar.DAY_OF_MONTH, noOfDaysFromToday);  
		String newDate = sdf.format(c.getTime());  
		return newDate;
	}
	
	void selectDepartureFlightNumber(int n) throws InterruptedException {
		Date date = new Date(departureDate);
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM");
		String depDate = format.format(date);
		driver.findElement(By.xpath("(//p[contains(text(),'"+depDate+"')]/../div[2]//label["+n+"]/div/div[2]/div[2]/span | //div[@id='ow-domrt-jrny']/div/div["+n+"])[1]")).click();
	}
	
	void selectReturnFlightNumber(int n) throws InterruptedException {
		Date date = new Date(returnDate);
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM");
		String retDate = format.format(date);
		driver.findElement(By.xpath("(//p[contains(text(),'"+retDate+"')]/../div[2]//label["+n+"]/div/div[2]/div[2]/span | //div[@id='rt-domrt-jrny']/div/div["+n+"])[1]")).click();
	}
	WebElement find(By element){
		wait = new WebDriverWait(driver, 15);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	List<WebElement> findAll(By locator) throws InterruptedException {
		wait = new WebDriverWait(driver, 15);
		Thread.sleep(3000);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

}

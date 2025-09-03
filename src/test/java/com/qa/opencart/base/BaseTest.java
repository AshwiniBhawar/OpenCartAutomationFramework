package com.qa.opencart.base;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.CheckOutPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultPage;

import io.qameta.allure.Description;

//@Listeners({TestAllureListener.class,ChainTestListener.class})
public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	
	private static final Logger log= LogManager.getLogger(DriverFactory.class);
	
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountPage accountPage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected CheckOutPage checkOutPage;
	protected RegistrationPage registrationPage;
	protected HomePage homePage;
	
	@Description("launch the browser: {0} and url")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		log.info("before test method executed==>set up the browser");
		df= new DriverFactory();
		prop=df.initProp();
		
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		
		driver=df.initDriver(prop);
		loginPage= new LoginPage(driver);
	}
	
	/**
	 * take screenshot after failure for each test
	 * this will be running after each @test method
	 * @param result
	 */
	
	@AfterMethod
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) {
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
	}
	
	
	@AfterTest
	public void tearDown() {
		log.info("after test method executed==> quit the browser");
		driver.quit();
	}

}

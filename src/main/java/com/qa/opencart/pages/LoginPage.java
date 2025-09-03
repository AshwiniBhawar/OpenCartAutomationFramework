package com.qa.opencart.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;


public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eUtil;
	private CommonMethodsPage cm;
	
	private static final Logger log= LogManager.getLogger(LoginPage.class);
	
	//private By locators:page objects
	private final By usernameLocator= By.id("input-email");
	private final By passwordLocator= By.id("input-password");
	private final By loginBtnLocator= By.xpath("//input[@value='Login']");
	private final By forgotPwdLinkLocator= By.linkText("Forgotten Password");
	private final By headerLocator= By.tagName("h2");
	private final By registerLinkLocator= By.linkText("Register");
		
	private final By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	//public constructor
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eUtil= new ElementUtil(driver);		
		cm=new CommonMethodsPage(driver);
	}
	
	//public page methods/actions
	
	@Step("waiting for page title..")
	public String getLoginPageTitle() {
		String title=eUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		log.info("login page title: "+title);
		return title;
	}
	
	@Step("waiting for login page url..")
	public String getLoginPagUrl() {
		String url=eUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		log.info("login page url: "+url);
		return url; 
	}
	
	@Step("forgot pws link exist..")
	public boolean isForgotPasswordLinkExist() {
		boolean result=eUtil.isElementDisplayed(forgotPwdLinkLocator);
		log.info("is forgot pwd link exist: "+ result);
		return result;
	}
	
	@Step("login page header exist..")
	public boolean isHeaderExist() {
		boolean result=eUtil.isElementDisplayed(headerLocator);
		log.info("is header exist: "+result);
		return result;
	}
	
	@Step("login to the open cart application..")
	public AccountPage doLogin(String appUsername, String appPassword) {
		log.info("Application credentials : " + appUsername + " : " + "************");
		eUtil.waitForElementVisible(usernameLocator, AppConstants.DEFAULT_SHORT_WAIT).sendKeys(appUsername);
		eUtil.doSendKeys(passwordLocator, appPassword);
		eUtil.doClick(loginBtnLocator);
		return new AccountPage(driver);
	}
	
	@Step("navigate to register page..")
	public RegistrationPage navigateToRegisterPage() {
		log.info("navigate to registration page");
		eUtil.waitForElementVisible(registerLinkLocator, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegistrationPage(driver);
	}
	
	@Step("navigate to home page")
	public HomePage navigateToHomePage() {
		return cm.navigateToHomePage();
	}
	
	@Step("login with in-correct username: {0} and password: {1}")
	public boolean doLoginWithInvalidCredentails(String invalidUN, String invalidPWD) {
		if(invalidUN==null) {
			invalidUN="";
		}
		if(invalidPWD==null) {
			invalidPWD="";
		}
		
		log.info("Invalid application credentials: " + invalidUN + " : " + invalidPWD);
		WebElement emailEle = eUtil.waitForElementVisible(usernameLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailEle.clear();
		emailEle.sendKeys(invalidUN);
		eUtil.doSendKeys(passwordLocator, invalidPWD);
		eUtil.doClick(loginBtnLocator);
		String errorMessg = eUtil.waitForElementVisible(loginErrorMessg,AppConstants.DEFAULT_MEDIUM_WAIT).getText();
		log.info("invalid creds error messg: " + errorMessg);
		if (invalidUN.isBlank() && invalidPWD.isBlank() && errorMessg.contains(AppConstants.LOGIN_BLANK_CREDS_MESSG)) {
			return true;
		}
		
		else if (invalidUN.isBlank() && !invalidPWD.isBlank() && errorMessg.contains(AppConstants.LOGIN_BLANK_CREDS_MESSG)) {
			return true;
		}
		
		else if (!invalidUN.isBlank() && invalidPWD.isBlank() && errorMessg.contains(AppConstants.LOGIN_INVALID_CREDS_MESSG)) {
			return true;
		}
		
		else if (!invalidUN.isBlank() && !invalidPWD.isBlank() && errorMessg.contains(AppConstants.LOGIN_INVALID_CREDS_MESSG)) {
			return true;
		}
		return false;
	}
	
	public List<String> getFooterLinks(){
		return cm.footerLinksExist();
	}
}

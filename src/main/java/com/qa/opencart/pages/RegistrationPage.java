package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtil eUtil;

	private static final Logger log= LogManager.getLogger(RegistrationPage.class);
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);

	}

	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmpassword = By.id("input-confirm");

	private final By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private final By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");

	private final By agreeCheckBox = By.name("agree");
	private final By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private final By successMessg = By.cssSelector("div#content h1");

	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");
	
		
	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		log.info("fill the registration form");
		WebElement ele=eUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_WAIT);
		ele.clear();
		ele.sendKeys(firstName);
		eUtil.doSendKeys(this.lastName, lastName);
		eUtil.doSendKeys(this.email, email);
		eUtil.doSendKeys(this.telephone, telephone);
		eUtil.doSendKeys(this.password, password);
		eUtil.doSendKeys(this.confirmpassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eUtil.doClick(subscribeYes);
		} else {
			eUtil.doClick(subscribeNo);
		}

		eUtil.doClick(agreeCheckBox);
		eUtil.doClick(continueButton);

		String successMesg = eUtil.waitForElementVisible(successMessg, AppConstants.DEFAULT_MEDIUM_WAIT).getText();
		log.info("Registration success message: "+successMesg);

		if (successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eUtil.doClick(logoutLink);
			eUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}
	}
}

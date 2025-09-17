package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage{

	private WebDriver driver;
	private ElementUtil eUtil;
	private CommonMethodsPage cm;
	
	private static final Logger log= LogManager.getLogger(AccountPage.class);
		
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
		cm=new CommonMethodsPage(driver);
	}

	private final By myAccountHeadersLocator = By.cssSelector("div#content h2");
	private final By logoutLinkLocator = By.linkText("Logout");
	
	@Step("search a product")
	public SearchResultPage searchProduct(String searchKey) {
		return cm.searchProduct(searchKey);
	}
	
	@Step("account page headers list")
	public List<String> getAccountPageHeaders() {
		List<WebElement> headersList=eUtil.waitForElementsPresence(myAccountHeadersLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		log.info("total number of headers :"+headersList.size());
		List<String> headersValList= new ArrayList<String>();
		for(WebElement e: headersList) {
			String test=e.getText();
			headersValList.add(test);
		}
		log.info("account page header list: "+headersList);
		return headersValList;
	}
	
	@Step("Account page logout link exist")
	public boolean isLogoutLinkExist() {
		WebElement logoutEle= eUtil.waitForElementVisible(logoutLinkLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		boolean result=eUtil.isElementDisplayed(logoutEle);
		log.info("is log out link exist: "+result );
		return result;
	}
	
	@Step("navigate to home page")
	public HomePage navigateToHomePage() {
		return cm.navigateToHomePage();
	}
	
	@Step("click on shopping cart link")
	public CheckOutPage clickShoppingCartLink() {
		return cm.clickOnShoppingCartLink();
	}

}

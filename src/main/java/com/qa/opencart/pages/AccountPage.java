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
	
	public SearchResultPage searchProduct(String searchKey) {
		return cm.searchProduct(searchKey);
	}
	
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
	
	public boolean isLogoutLinkExist() {
		WebElement logoutEle= eUtil.waitForElementVisible(logoutLinkLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		boolean result=eUtil.isElementDisplayed(logoutEle);
		log.info("is log out link exist: "+result );
		return result;
	}
	
	public HomePage navigateToHomePage() {
		return cm.navigateToHomePage();
	}
	
	public CheckOutPage clickShoppingCartLink() {
		return cm.clickOnShoppingCartLink();
	}

}

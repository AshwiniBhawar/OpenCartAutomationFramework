package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class CommonMethodsPage {

	private WebDriver driver;
	private ElementUtil eUtil;

	private static final Logger log = LogManager.getLogger(CommonMethodsPage.class);

	public CommonMethodsPage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
	}

	private final By menuItemsLocator = By
			.xpath("//nav[@id='menu']//div[contains(@class, 'navbar-ex1-collapse')]/ul/li");
	private final By searchLocator = By.name("search");
	private final By searchIconLocator = By.cssSelector("div#search button");
	private final By nalLogoLocator = By.xpath("//img[@title='naveenopencart']");
	private final By shoppingCartBtnLocator = By.xpath("//a[@title='Shopping Cart']");
	private final By topMenuHeadersLocator = By.xpath("//div[@id='top-links']//span[1]");
	private final By footerLinksLocator = By.cssSelector("footer li a");

	public boolean isNAlLogoDisplayed() {
		boolean result = eUtil.waitForElementVisible(nalLogoLocator, AppConstants.DEFAULT_MEDIUM_WAIT).isDisplayed();
		log.info("is NAL logo displayed on home page: " + result);
		return result;
	}

	public SearchResultPage searchProduct(String searchKey) {
		log.info("search a product");
		WebElement searchElement = eUtil.waitForElementVisible(searchLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		searchElement.clear();
		searchElement.sendKeys(searchKey);
		eUtil.doClick(searchIconLocator);
		return new SearchResultPage(driver);
	}

	public List<String> getMenuList() {
		List<WebElement> menuList = eUtil.waitForElementsVisible(menuItemsLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		List<String> list = new ArrayList<String>();

		log.info("Size of menu items: " + menuList.size());
		for (WebElement e : menuList) {
			String menuItemName = e.getText();
			list.add(menuItemName);
		}
		log.info("Menu list contains: " + list);
		return list;
	}

	public HomePage navigateToHomePage() {
		log.info("navigate to home page");
		boolean result = eUtil.waitForElementVisible(nalLogoLocator, AppConstants.DEFAULT_MEDIUM_WAIT).isDisplayed();
		if (result) {
			eUtil.waitForElementVisible(nalLogoLocator, AppConstants.DEFAULT_SHORT_WAIT).click();
			return new HomePage(driver);
		} else {
			log.error("Home page img is not displayed on the page");
			throw new FrameworkException("Home page img is not displayed on the page");
		}
	}

	public CheckOutPage clickOnShoppingCartLink() {
		log.info("click on shopping cart link");
		eUtil.waitForElementVisible(shoppingCartBtnLocator, AppConstants.DEFAULT_MEDIUM_WAIT).click();
		return new CheckOutPage(driver);
	}

	public List<String> topMenuHeadersList() {
		List<WebElement> headerList = eUtil.waitForElementsVisible(topMenuHeadersLocator,
				AppConstants.DEFAULT_MEDIUM_WAIT);
		List<String> list = new ArrayList<String>();

		for (WebElement e : headerList) {
			list.add(e.getText());
		}
		log.info("home page top menu headers list" + list);
		return list;
	}

	public List<String> footerLinksExist() {
		List<WebElement> footerList = eUtil.waitForElementsVisible(footerLinksLocator, AppConstants.DEFAULT_SHORT_WAIT);
		List<String> footerValueList = new ArrayList<String>();
		for (WebElement e : footerList) {
			String text = e.getText();
			footerValueList.add(text);
		}
		log.info("list of footer links: "+footerValueList);
		log.info("size of footer links: "+footerValueList.size());
		return footerValueList;
	}

}

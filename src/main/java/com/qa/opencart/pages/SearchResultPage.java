package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultPage {
	
	private WebDriver driver;
	private ElementUtil eUtil;
	
	private static final Logger log= LogManager.getLogger(SearchResultPage.class);
	
	public SearchResultPage(WebDriver driver){
		this.driver=driver;
		eUtil= new ElementUtil(driver);
	}
	
	private final By searchResultsLocator= By.xpath("//div[@class='product-thumb']");
	private final By resultsHeaderLocator=By.tagName("h1");
		
	public int getResultsCount() {
		int resultsCount= eUtil.waitForElementsVisible(searchResultsLocator, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		log.info("results count :"+ resultsCount);
		return resultsCount;
	}
	
	public String getResultsHeader() {
		String resultsHeader= eUtil.doElementGetText(resultsHeaderLocator);
		log.info("results header :"+ resultsHeader);
		return resultsHeader;
	}
	
	public ProductInfoPage clickOnSearchProduct(String productName) {
		log.info("product name :" + productName);
		eUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	
}

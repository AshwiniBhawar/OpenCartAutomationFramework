package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.JavascriptUtil;

public class HomePage {
	
	private WebDriver driver;
	private ElementUtil eUtil;
	private JavascriptUtil jsUtil;
	private CommonMethodsPage cm;
	
	private static final Logger log= LogManager.getLogger(HomePage.class);
	
	public HomePage( WebDriver driver) {
		this.driver=driver;
		eUtil= new ElementUtil(driver);
		jsUtil= new JavascriptUtil(driver);
		cm= new CommonMethodsPage(driver);
	}
	
	private final By getBrandLogoLocator = By.xpath("//div[@id='carousel0']//img");
	private final By scrollBrandLogoLocator= By.xpath("//div[contains(@class,'carousel0')]//span[@class='swiper-pagination-bullet']");
	private final By swipperSlideImageLocator= By.cssSelector(".swiper-slide.text-center.swiper-slide-active img[alt='MacBookAir']");
	

	public boolean isNALLogoDisplayed() {
		return cm.isNAlLogoDisplayed();
	}
	
	public List<String> getMenuList() {
		return cm.getMenuList();
	}
	
	public List<String> getBrandLogoList() {
		jsUtil.scrollIntoView(eUtil.getElement(getBrandLogoLocator));
		List<WebElement> scrollCount=eUtil.waitForElementsVisible(scrollBrandLogoLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		List<String> list= new ArrayList<String>();
		
		By firstBrndLogoLocator= By.xpath("(//div[@id='carousel0']//div[contains(@class,'swiper-slide')]/img)[1]");
		String brandLogoName= eUtil.getElement(firstBrndLogoLocator).getAttribute("alt");
		list.add(brandLogoName);
		
		for(int i=1;i<=scrollCount.size();i++) {
			By logoLocator = By.xpath("(//div[@id='carousel0']//div[contains(@class,'swiper-slide')]/img)["+(i+1)+"]");
			brandLogoName = eUtil.getElement(logoLocator).getAttribute("alt");
			list.add(brandLogoName);
		}
		
		log.info("Brands count is: "+list.size());
		log.info("Brand list contains: "+list);
		return list;
	}
			
	public SearchResultPage searchProduct(String searchKey) {
		return cm.searchProduct(searchKey);
	}
	
	public boolean isSwipperImageSectionExist() {
		boolean result=eUtil.waitForElementVisible(swipperSlideImageLocator, AppConstants.DEFAULT_SHORT_WAIT).isDisplayed();
		log.info("is swipper image section exist on home page: "+result);
		return result;
	}
	
	public List<String> topMenuHeadersList(){
		return cm.topMenuHeadersList();
	}

}

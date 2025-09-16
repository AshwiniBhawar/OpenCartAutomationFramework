package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

public class CheckOutPage {
	private WebDriver driver;
	private ElementUtil eUtil;
	
	private static final Logger log= LogManager.getLogger(CheckOutPage.class);
	
	public CheckOutPage(WebDriver driver){
		this.driver=driver;
		eUtil= new ElementUtil(driver);
	}
	
	private final By shoppingCartHeaderLocator=By.tagName("h1");
	private final By checkoutBtnLocator=By.linkText("Checkout");
	private final By getTableRowsLocator=By.xpath("//div[@class='table-responsive']/table/tbody/tr");
	private final By cancelCartProductLocator=By.xpath("//button[contains(@data-original-title,'Remove')]");
	private final By updateCartProductValueLocator=By.xpath("//button[@data-original-title='Update']");
	private final By shopCartProductHeaderLocator= By.xpath("//table[@class='table table-bordered']/thead//td");
	private final By shopCartTableLocator=By.xpath("//div[@class='table-responsive']/table//img");
	private final By shopcartEmptyMsgLocator=By.xpath("//div[@id='content']/p[contains(text(),'empty')]");
	private final By shopCartContinueBtnLocator=By.xpath("//div[@class='pull-right']/a");
	
	public boolean isCheckoutBtnExist() {
		return eUtil.isElementDisplayed(checkoutBtnLocator);
	}
	
	public List<String> shoppingCartProductDetails() {
		log.info("get the details of shopping cart product");
		eUtil.waitForElementsVisible(shopCartProductHeaderLocator, AppConstants.DEFAULT_MEDIUM_WAIT);
		List<WebElement> rows=eUtil.getElements(getTableRowsLocator);
		log.info("available products rows: "+rows.size());
		List<String> list= new ArrayList<String>();
		for(int i=1; i<=rows.size();i++) {
			 //By shopCartProductsHeaderLocator= By.xpath("//div[@class='table-responsive']/table/thead//td["+i+"]");
			 //By shopCartProductNotAvailableLocator=By.xpath("//div[@class='table-responsive']/table/tbody//td[@class='text-left']/span["+i+"]");
			 By shopCartProductsImageLocator=By.xpath("//div[@class='table-responsive']/table/tbody//td//img["+i+"]");
			 By shopCartProductLinkLocator=By.xpath("//div[@class='table-responsive']/table/tbody//td[@class='text-left']/a["+i+"]");
			 By shopCartProductModelLocator=By.xpath("//div[@class='table-responsive']/table/tbody//td[@class='text-left'][2]["+i+"]");
			 By shopCartProductQuanityLocator=By.xpath("//input[contains(@name,'quantity')]["+i+"]");
			 By shopCartUnitPriceLocator=By.xpath("//div[@class='table-responsive']/table/tbody//td[5]["+i+"]");
			 By shopCartTotalPricelocator= By.xpath("//div[@class='table-responsive']/table/tbody//td[6]["+i+"]");
		
			 String imgTitleText=eUtil.waitForElementVisible(shopCartProductsImageLocator,AppConstants.DEFAULT_SHORT_WAIT).getAttribute("title");
			 list.add(imgTitleText);
			 String productNameLinkText=eUtil.doElementGetText(shopCartProductLinkLocator);
			 list.add(productNameLinkText);
			 String productModelText=eUtil.doElementGetText(shopCartProductModelLocator);
			 list.add(productModelText);
			 String productQuanityText=eUtil.getElement(shopCartProductQuanityLocator).getAttribute("value");
			 list.add(productQuanityText);
			 String productUnitPriceText=eUtil.doElementGetText(shopCartUnitPriceLocator);
			 list.add(productUnitPriceText);
			 String productTotalPriceText=eUtil.doElementGetText(shopCartTotalPricelocator);
			 list.add(productTotalPriceText);
		}
		
		log.info("Shopping cart item details : "+list);
		return list;
	}
	
	public String clearShoppingCart() {
		log.info("clear the shopping cart");
		try {
			eUtil.waitForElementVisible(shopCartTableLocator, AppConstants.DEFAULT_SHORT_WAIT);
			List<WebElement> list = eUtil.waitForElementsVisible(cancelCartProductLocator,AppConstants.DEFAULT_MEDIUM_WAIT);
			log.info("size of products available in the cart: "+list.size());
			if (list.size() > 0) {
				for (WebElement e : list) {
					e.click();
				}
			}
			String emptyCartMsgText = eUtil.waitForElementVisible(shopcartEmptyMsgLocator, AppConstants.DEFAULT_LARGE_WAIT).getText();
			log.info("empty cart message is: " +emptyCartMsgText);
			return emptyCartMsgText;
		}
		catch(Exception e) {
			String emptyCartMsgText = eUtil.waitForElementVisible(shopcartEmptyMsgLocator, AppConstants.DEFAULT_LARGE_WAIT).getText();
			log.info("empty cart message is: " +emptyCartMsgText);
			return emptyCartMsgText;
		}
	}
	
	public HomePage clickContinueOnShoppingCart() {
		log.info("click on continue button");
		eUtil.doClick(shopCartContinueBtnLocator);
		return new HomePage(driver);
	}
}

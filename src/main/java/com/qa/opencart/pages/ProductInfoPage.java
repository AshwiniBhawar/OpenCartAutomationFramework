package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eUtil;
	private Map<String, String> productMap;

	private static final Logger log= LogManager.getLogger(ProductInfoPage.class);
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
	}

	private final By headerLocator = By.tagName("h1");
	private final By productImagesLocator = By.cssSelector("ul.thumbnails img");
	private final By productCountLocator = By.name("quantity");
	private final By addToCartBtnLocator = By.id("button-cart");
	private final By shoppingCartLinkLocator = By.linkText("shopping cart");
	private final By productAddedSuccMsgLocator = By.cssSelector("div.alert.alert-success.alert-dismissible");
	private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private final By productReviewLocator = By.linkText("Reviews (0)");
	private final By productReviewNameLocator = By.id("input-name");
	private final By productReviewTextLocator = By.id("input-review");
	private final By productReviewRatingLocator = By.xpath("//div[@class='col-sm-12']//input[@type='radio']");
	private final By productReviewContinueBtnLocator = By.id("button-review");
	private final By productReviewMessageLocator = By.cssSelector("div.alert.alert-success.alert-dismissible");

	public String getProductHeader() {
		String headerVal = eUtil.waitForElementVisible(headerLocator, AppConstants.DEFAULT_MEDIUM_WAIT).getText();
		log.info("product header :" + headerVal);
		return headerVal;
	}

	public int getProductImages() {
		int imagesCount = eUtil.waitForElementsVisible(productImagesLocator, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		log.info("product images count is:"+ imagesCount);
		return imagesCount;
	}

	public Map<String, String> getProductData() {
		// productMap= new HashMap<String, String>();
		productMap = new LinkedHashMap<String, String>();
		productMap.put("productname", getProductHeader());
		productMap.put("productimages", String.valueOf(getProductImages()));

		getProductMetaData();
		getProductPriceData();

		log.info("===========product data=============: \n" + productMap);
		return productMap;
	}

	private void getProductMetaData() {
		List<WebElement> metaList = eUtil.waitForElementsVisible(productMetaData, AppConstants.DEFAULT_MEDIUM_WAIT);
		log.info("total meta data :" + metaList.size());

		for (WebElement e : metaList) {
			String metaData = e.getText();
			String[] meta = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue.toString());
		}
	}

	private void getProductPriceData() {
		List<WebElement> priceList = eUtil.waitForElementsVisible(productPriceData, AppConstants.DEFAULT_MEDIUM_WAIT);
		log.info("total price data: " + priceList.size());
		
		if(priceList.size()==2) {
			String priceValue = priceList.get(0).getText();
			String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
			productMap.put("productprice", priceValue);
			productMap.put("extaxprice", exTaxValue);
		}
		else {
			String originalValue = priceList.get(0).getText();
			String priceValue = priceList.get(1).getText();
			String exTaxValue = priceList.get(2).getText().split(":")[1].trim();
			productMap.put("originalprice", originalValue);
			productMap.put("productprice", priceValue);
			productMap.put("extaxprice", exTaxValue);
		}
	}

	public CheckOutPage addProductToCart(String productQty) {
		log.info("product quality count is: " + productQty);
		WebElement productQtyLocator = eUtil.waitForElementVisible(productCountLocator,AppConstants.DEFAULT_MEDIUM_WAIT);
		productQtyLocator.clear();
		productQtyLocator.sendKeys(productQty);
		eUtil.waitForElementVisible(addToCartBtnLocator,AppConstants.DEFAULT_MEDIUM_WAIT).click();
		eUtil.waitForElementVisible(shoppingCartLinkLocator, AppConstants.DEFAULT_MEDIUM_WAIT).click();
		return new CheckOutPage(driver);
	}

	public String productAddedToCartSuccMsg(String productQty) {
		log.info("product quality count is: " + productQty);
		WebElement productQtyLocator = eUtil.waitForElementVisible(productCountLocator,AppConstants.DEFAULT_MEDIUM_WAIT);
		productQtyLocator.clear();
		productQtyLocator.sendKeys(productQty);
		eUtil.doClick(addToCartBtnLocator);
		String succMsg = eUtil.waitForElementVisible(productAddedSuccMsgLocator, AppConstants.DEFAULT_MEDIUM_WAIT)
				.getText();
		succMsg = succMsg.split("×")[0];
		log.info("product added to the cart success msg: "+succMsg);
		return succMsg;
	}

	public String writeProductReview(String fullName, String enterReviewMessage, String ratingValue) {
		log.info("write a product review");
		if (fullName.length() >= 3 && fullName.length() <= 25) {
			eUtil.waitForElementVisible(productReviewLocator, AppConstants.DEFAULT_MEDIUM_WAIT).click();
			WebElement reviewername = eUtil.waitForElementVisible(productReviewNameLocator,AppConstants.DEFAULT_MEDIUM_WAIT);
			reviewername.clear();
			reviewername.sendKeys(fullName);
		} else {
			log.error("Warning: Review Name must be between 3 and 25 characters!" + "====="+ "Entered review name is:" + fullName);
			throw new FrameworkException("Warning: Review Name must be between 3 and 25 characters!" + "====="+ "Entered review name is:" + fullName);
		}

		if (enterReviewMessage.length() >= 25 && enterReviewMessage.length() <= 1000) {
			eUtil.doSendKeys(productReviewTextLocator, enterReviewMessage);
		} else {
			log.error("Warning: Review Text must be between 25 and 1000 characters!");
			throw new FrameworkException("Warning: Review Text must be between 25 and 1000 characters!");
		}
		String reviewMsg=null;
		
		if (!ratingValue.isBlank() || !ratingValue.isEmpty()) {
			List<WebElement> ratingsList = eUtil.getElements(productReviewRatingLocator);
			boolean flag=false;
			
			for (WebElement e : ratingsList) {
				String ratingAttributeValue = e.getAttribute("value");
				
				if (ratingAttributeValue.equals(ratingValue)) {
					flag=true;
					e.click();
					eUtil.doClick(productReviewContinueBtnLocator);
					reviewMsg = eUtil.waitForElementVisible(productReviewMessageLocator, AppConstants.DEFAULT_MEDIUM_WAIT).getText();
					log.info("product review msg is: "+reviewMsg);
				} 
			}
			
			if(flag==false) {
					log.error("Provided review rating value does not match with the rating range"+" ==> Please pass a value between 1-5");
					throw new FrameworkException("Provided review rating value does not match with the rating range"+" ==> Please pass a value between 1-5");
			}
		} else {
			log.error("Please pass a valid review rating value: only between 1-5");
			throw new FrameworkException("Please pass a valid review rating value: only between 1-5");
		}
		return reviewMsg;
	}
	
}

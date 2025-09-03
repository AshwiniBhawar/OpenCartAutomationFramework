package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class AddProductToTheCartPageTest extends BaseTest{

	@BeforeTest
	public void accPageSetup() {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void addProductToCartTest() {
		searchResultPage=accountPage.searchProduct("macbook");
		productInfoPage=searchResultPage.clickOnSearchProduct("MacBook Pro");
		checkOutPage=productInfoPage.addProductToCart("2");
		boolean actualResult=checkOutPage.isCheckoutBtnExist();
		Assert.assertTrue(actualResult);
	}
	
	@Test
	public void productAddedToCartSuccMsgTest() {
		searchResultPage=accountPage.searchProduct("macbook");
		productInfoPage=searchResultPage.clickOnSearchProduct("MacBook Pro");
		String actualMsg=productInfoPage.productAddedToCartSuccMsg("2");
		Assert.assertTrue(actualMsg.contains("Success: You have added MacBook Pro to your shopping cart!"));
	}
}

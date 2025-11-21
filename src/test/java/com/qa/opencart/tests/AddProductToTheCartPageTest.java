package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-5: Design the Open Cart App Add Product To The Cart Page")
@Feature("F-5: design open cart add product to the cart feature")
@Story("US-5: develope ladd product to the cart feature- add product, check succ message etc.")
public class AddProductToTheCartPageTest extends BaseTest{

	@Description("login to the application")
	@BeforeClass
	public void accPageSetup() {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("add product to the cart test")
	@Test
	public void addProductToCartTest() {
		searchResultPage=accountPage.searchProduct("macbook");
		productInfoPage=searchResultPage.clickOnSearchProduct("MacBook Pro");
		checkOutPage=productInfoPage.addProductToCart("2");
		boolean actualResult=checkOutPage.isCheckoutBtnExist();
		Assert.assertTrue(actualResult);
	}
	
	@Description("check the success message for added product")
	@Test
	public void productAddedToCartSuccMsgTest() {
		searchResultPage=accountPage.searchProduct("macbook");
		productInfoPage=searchResultPage.clickOnSearchProduct("MacBook Pro");
		String actualMsg=productInfoPage.productAddedToCartSuccMsg("2");
		Assert.assertTrue(actualMsg.contains("Success: You have added MacBook Pro to your shopping cart!"));
	}
}

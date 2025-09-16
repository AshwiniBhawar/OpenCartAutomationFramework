package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-10: Design the Open Cart App Shopping Cart Product Page")
@Feature("F-10: design open cart shopping cart product feature")
@Story("US-10: develope shopping cart product feature")
public class ShoppingCartProductDetailsTest extends BaseTest{

	@Description("login to the application")
	@BeforeTest
	public void accPageSetup() {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getShoppingCartExcelData() {
		return ExcelUtil.getTestData("testdatainfo", "shoppingcartdata");
	}
		
	@Description("shopping cart {2} product data validation test")
	@Test(dataProvider="getShoppingCartExcelData")
	public void shopCartProductDataValidationUsingExcelTest(String searchKey,String productImage, String productName, String productModel, String quantity, String unitPrice, String totalPrice) {
		String cartEmptyMsg=accountPage.clickShoppingCartLink().clearShoppingCart();
		Assert.assertEquals(cartEmptyMsg, AppConstants.SHOPPING_CART_EMPTY_MSG);
		List<String> actualData=accountPage.searchProduct(searchKey).clickOnSearchProduct(productName).addProductToCart(quantity)
		.shoppingCartProductDetails();
		Assert.assertEquals(actualData, List.of(productImage, productName, productModel, quantity, unitPrice, totalPrice));
		
	}	
	
}

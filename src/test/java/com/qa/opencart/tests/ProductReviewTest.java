package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-7: Design the Open Cart App Product Review Page")
@Feature("F-7: design open cart product review feature")
@Story("US-7: develope product review feature")
public class ProductReviewTest extends BaseTest{

	@Description("login to the application")
	@BeforeClass
	public void accPageSetup() {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getReviewExcelData() {
		return ExcelUtil.getTestData("testdatainfo", "productreview");
	}
	
	@Description("submit product review for {1}")
	@Test(dataProvider="getReviewExcelData")
	public void submitProductReviewTest(String searchKey, String productName, String fullName, String reviewMessage, String rating) {
		String actualMsg=accountPage.searchProduct(searchKey).clickOnSearchProduct(productName).writeProductReview(fullName, reviewMessage,rating);
		Assert.assertEquals(actualMsg, AppConstants.PRODUCT_REVIEW_MESSAGE);
	}
}

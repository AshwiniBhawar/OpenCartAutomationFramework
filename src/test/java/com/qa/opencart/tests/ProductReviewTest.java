package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductReviewTest extends BaseTest{

	@BeforeClass
	public void accPageSetup() {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getReviewExcelData() {
		return ExcelUtil.getTestData("testdatainfo", "productreview");
	}
	
	@Test(dataProvider="getReviewExcelData")
	public void submitProductReviewTest(String searchKey, String productName, String fullName, String reviewMessage, String rating) {
		String actualMsg=accountPage.searchProduct(searchKey).clickOnSearchProduct(productName).writeProductReview(fullName, reviewMessage,rating);
		Assert.assertEquals(actualMsg, AppConstants.PRODUCT_REVIEW_MESSAGE);
	}
}

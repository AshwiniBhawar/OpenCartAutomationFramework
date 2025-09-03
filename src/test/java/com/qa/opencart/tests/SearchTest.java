package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest{
	
	//BT(chrome+url)--> BC(login) --> @Test
	
		@BeforeClass
		public void accPageSetup() {
			accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
		
		@DataProvider
		public Object[][] getSearchData() {
			return new Object[][]{
					{"macbook", "MacBook Pro"} ,
					{"canon", "Canon EOS 5D"},
					{"samsung", "Samsung Galaxy Tab 10.1"},
					{"palm", "Palm Treo Pro"}
			};
		}
		
		@Test(dataProvider="getSearchData",priority=Integer.MAX_VALUE)
		public void clickSearchProductTest(String searchKey, String productName) {
			searchResultPage=accountPage.searchProduct(searchKey);
			productInfoPage=searchResultPage.clickOnSearchProduct(productName);
			String actualHeader=productInfoPage.getProductHeader();
			Assert.assertEquals(actualHeader, productName);
		}
		
		@Test
		public void searchResultsCountTest() {
			searchResultPage=accountPage.searchProduct("macbook");
			int count=searchResultPage.getResultsCount();
			Assert.assertEquals(count, 3);	
		}
		
		@Test
		public void searchResultsHeaderTest() {
			searchResultPage=accountPage.searchProduct("macbook");
			String header=searchResultPage.getResultsHeader();
			Assert.assertTrue(header.toLowerCase().contains("macbook"));	
		}
		
		
}

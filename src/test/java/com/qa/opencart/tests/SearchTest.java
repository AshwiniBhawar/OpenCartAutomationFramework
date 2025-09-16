package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-9: Design the Open Cart App Product Search Page")
@Feature("F-9: design open cart product search feature")
@Story("US-9: develope product search feature- search results count, headers, click on search product etc.")
public class SearchTest extends BaseTest{
	
	//BT(chrome+url)--> BC(login) --> @Test
	
		@Description("login to the application")
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
		
		@Description("click on search product test")
		@Test(dataProvider="getSearchData",priority=Integer.MAX_VALUE)
		public void clickSearchProductTest(String searchKey, String productName) {
			searchResultPage=accountPage.searchProduct(searchKey);
			productInfoPage=searchResultPage.clickOnSearchProduct(productName);
			String actualHeader=productInfoPage.getProductHeader();
			Assert.assertEquals(actualHeader, productName);
		}
		
		@Description("search result product count test")
		@Test
		public void searchResultsCountTest() {
			searchResultPage=accountPage.searchProduct("macbook");
			int count=searchResultPage.getResultsCount();
			Assert.assertEquals(count, 3);	
		}
		
		@Description("search result product headers test")
		@Test
		public void searchResultsHeaderTest() {
			searchResultPage=accountPage.searchProduct("macbook");
			String header=searchResultPage.getResultsHeader();
			Assert.assertTrue(header.toLowerCase().contains("macbook"));	
		}
				
}

package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-6: Design the Open Cart App Product Info Page")
@Feature("F-6: design open cart product info feature")
@Story("US-6: develope product info feature- product info, images count, headers etc.")
public class ProductInfoPageTest extends BaseTest {
	
	//BT(chrome+url)--> BC(login) --> @Test
	
		@Description("login to the application")
		@BeforeClass
		public void accPageSetup() {
			accountPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
		
		@DataProvider(name="productHeaderData")
		public Object[][] getProductDetails() {
			return new Object[][]{
					{"macbook", "MacBook Pro"},
					{"canon", "Canon EOS 5D"},
					{"samsung", "Samsung Galaxy Tab 10.1"},
					{"palm", "Palm Treo Pro"}
			};
		}
			
		@Description("product headers test for {1}")
		@Test(dataProvider="productHeaderData")
		public void productHeaderTest(String searchKey, String productName) {
			searchResultPage=accountPage.searchProduct(searchKey);
			productInfoPage=searchResultPage.clickOnSearchProduct(productName);
			String actualHeader=productInfoPage.getProductHeader();
			Assert.assertEquals(actualHeader, productName);
		}
		
//		@DataProvider
//		public Object[][] getProductImages() {
//			return new Object[][]{
//					{"macbook", "MacBook Pro" , "4"},
//					{"canon", "Canon EOS 5D", "3"},
//					{"samsung", "Samsung Galaxy Tab 10.1", "7"},
//					{"palm", "Palm Treo Pro", "3"}
//			};
//		}
		
//		@DataProvider
//		public Object[][] getProductImagesDataExcel() {
//			return ExcelUtil.getTestData("testdata","products");
//		}
		
		@DataProvider
		public Object[][] getProductImagesDataCSV() {
			return CSVUtil.getCSVData("productdata");
		}
		
		@Description("product images count test")
		@Test(dataProvider="getProductImagesDataCSV")
		public void productImagesCountTest(String searchKey, String productName, String imageCount) {
			int expectedImageCount=Integer.parseInt(imageCount);
			System.out.println(expectedImageCount);
			searchResultPage=accountPage.searchProduct(searchKey);
			productInfoPage=searchResultPage.clickOnSearchProduct(productName);
			int actualImagesCount=productInfoPage.getProductImages();
			Assert.assertEquals(actualImagesCount, expectedImageCount);
		}
		
				
		@DataProvider
		public Object[][] getProductInfoCSVData() {
			return CSVUtil.getCSVData("productinfodata");
		}
		
		@DataProvider
		public Object[][] getProductInfoExcelData() {
			return ExcelUtil.getTestData("testdatainfo","productinfo");
		}
		
		@Description("product info data test for {1}")
		@Test(dataProvider="getProductInfoCSVData")
		public void productInfoTest(String searchKey,String productName, String productImages, String brand, String productCode, 
									String rewardPoints,String availability, String originalPrice, String productPrice, String exTaxPrice) {
			searchResultPage=accountPage.searchProduct(searchKey);
			productInfoPage=searchResultPage.clickOnSearchProduct(productName);
			
			Map<String, String> actualProductData=productInfoPage.getProductData();

			SoftAssert softAssert= new SoftAssert();
			softAssert.assertEquals(actualProductData.get("productname"), productName);
			softAssert.assertEquals(actualProductData.get("productimages"), productImages);
			softAssert.assertEquals(actualProductData.get("Brand"), brand);
			softAssert.assertEquals(actualProductData.get("Product Code"), productCode);
			softAssert.assertEquals(actualProductData.get("Reward Points"), rewardPoints);
			softAssert.assertEquals(actualProductData.get("Availability"), availability);
			softAssert.assertEquals(actualProductData.get("originalprice"), originalPrice);
			softAssert.assertEquals(actualProductData.get("productprice"), productPrice);
			softAssert.assertEquals(actualProductData.get("extaxprice"), exTaxPrice);	
			
			softAssert.assertAll();
		}
		
}

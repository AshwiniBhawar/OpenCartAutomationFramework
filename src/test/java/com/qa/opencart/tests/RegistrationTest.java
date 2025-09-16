package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-8: Design the Open Cart App Registration Page")
@Feature("F-8: design open cart registration feature")
@Story("US-8: develope registration feature")
public class RegistrationTest extends BaseTest{
	
	//BT(chrome+login url) --> BC(move to register page) --> @Test
	
	@Description("navigate to register page")
	@BeforeClass
	public void goToRegisterPage() {
		registrationPage=loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegistrationData() {
		return new Object[][] {
				{"harpreet", "automation", "9999999991", "harpreet@123", "yes" },
				{"siya", "automation", "9999999991", "siya@123", "no" },
		};
	}
	
	@DataProvider
	public Object[][] getRegExcelData() {
		return ExcelUtil.getTestData("testdatainfo","registrationdatawithemail");
	}
	
	@DataProvider
	public Object[][] getRegCSVDataWithoutEmail() {
		return CSVUtil.getCSVData("registrationdata");
	}
	
	@Description("register a user with random email id test")
	//@Test(dataProvider="getRegCSVDataWithoutEmail")
	public void register(String firstName, String lastName, String telephone, String password, String subscribe) {
		boolean result=registrationPage.userRegister(firstName, lastName, StringUtil.getRandomEmail(), telephone, password, subscribe);
		Assert.assertTrue(result);
	} 
	
	@Description("register a user test")
	@Test(dataProvider="getRegExcelData")
	public void register(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		boolean result=registrationPage.userRegister(firstName, lastName, email, telephone, password, subscribe);
		Assert.assertTrue(result);
	}
	
}

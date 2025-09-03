package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegistrationTest extends BaseTest{
	
	//BT(chrome+login url) --> BC(move to register page) --> @Test
	
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
		return ExcelUtil.getTestData("registrationdata","register");
	}
	
	@DataProvider
	public Object[][] getRegCSVData() {
		return CSVUtil.getCSVData("registrationdata");
	}
	
	@Test(dataProvider="getRegCSVData")
	public void register(String firstName, String lastName, String telephone, String password, String subscribe) {
		boolean result=registrationPage.userRegister(firstName, lastName, StringUtil.getRandomEmail(), telephone, password, subscribe);
		Assert.assertTrue(result);
	} 
}

package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class LoginPageNegativeTest extends BaseTest {
		
	@DataProvider
	public Object[][] getNegativeLoginData() {
		return new Object[][] {
			{"automationframework@gmail.com", "autoframe"},
			{"automationframework@gmail.com", ""},
			{"", "test@123"},
			{"", ""},
			{"tester871234@gmail.com","test12234"}
		};
	}
	
	@DataProvider
	public Object[][] getNegativeLoginDataFromExcel(){
		return ExcelUtil.getTestData("testdatainfo", "negativelogintestdata");
	}
	

	@Test(dataProvider = "getNegativeLoginDataFromExcel")
	public void negativeLoginTestForResiteredUser(String invalidUN, String invalidPWD) {
		Assert.assertTrue(loginPage.doLoginWithInvalidCredentails(invalidUN, invalidPWD));
	}
	

}

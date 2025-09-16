package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP-2: Design the Open Cart App Login Negative Page")
@Feature("F-2: design open cart login negative feature")
@Story("US-2: develope login feature with wrong username, password")
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
	
	@Description("login with invalid username and password")
	@Test(dataProvider = "getNegativeLoginDataFromExcel")
	public void negativeLoginTestForResiteredUser(String invalidUN, String invalidPWD) {
		Assert.assertTrue(loginPage.doLoginWithInvalidCredentails(invalidUN, invalidPWD));
	}
	

}

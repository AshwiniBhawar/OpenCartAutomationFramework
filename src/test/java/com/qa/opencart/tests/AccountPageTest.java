package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-3: Design the Open Cart App Account Page")
@Feature("F-3: design account page feature")
@Story("US-3: develope account page feature- page headers, logout link etc.")
public class AccountPageTest extends BaseTest {
	
	//BT--> BC --> @Test
	
	@Description("login to the application")
	@BeforeClass
	public void accPageSetup() {
		accountPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("Account page logout link test")
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountPage.isLogoutLinkExist());
	}
	
	@Description("Account page headers list test")
	@Test
	public void accPageHeadersTest() {
		List<String> accHeadersList=accountPage.getAccountPageHeaders();
		Assert.assertEquals(accHeadersList.size(), AppConstants.ACC_PAGE_HEADERS_COUNT);
		Assert.assertEquals(accHeadersList, AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
	}

}

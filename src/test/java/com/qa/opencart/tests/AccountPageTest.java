package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest {
	
	//BT--> BC --> @Test
	
	@BeforeClass
	public void accPageSetup() {
		accountPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> accHeadersList=accountPage.getAccountPageHeaders();
		Assert.assertEquals(accHeadersList.size(), AppConstants.ACC_PAGE_HEADERS_COUNT);
		Assert.assertEquals(accHeadersList, AppConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
	}

}

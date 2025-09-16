package com.qa.opencart.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("EP-1: Design the Open Cart App Login Page")
@Feature("F-1: design open cart login feature")
@Story("US-1: develope login core feature- title, url, user is able to login etc.")
public class LoginPageTest extends BaseTest{
	
	@Description("login page title test")
	@Test
	public void loginPageTitleTest() {
		String actualTitle=loginPage.getLoginPageTitle();
		ChainTestListener.log("login title is: "+actualTitle);
		Assert.assertEquals(actualTitle, "Account Login");
	}
	
	@Description("login page url test")
	@Test
	public void loginPageUrlTest() {
		String actualUrl=loginPage.getLoginPagUrl();
		ChainTestListener.log("login url is: "+actualUrl);
		Assert.assertTrue(actualUrl.contains("route=account/login"));
	}
	
	@Description("login page header exist test")
	@Test
	public void isLoginPageHeaderExistTest() {
		boolean actualResult=loginPage.isHeaderExist();
		ChainTestListener.log("login page header is exist?: "+actualResult);
		Assert.assertTrue(actualResult);
	}
	
	@Description("forgotton pwd link exist test")
	@Test
	public void isForgottenPwdLinkExistTest() {
		boolean actualResult=loginPage.isForgotPasswordLinkExist();
		ChainTestListener.log("forgotten Password link is exist?: "+actualResult);
		Assert.assertTrue(actualResult);
	}
	
	@Description("login with valid username and password test")
	@Test(priority=Integer.MAX_VALUE)
	public void doLoginTest() throws InterruptedException {
		accountPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		ChainTestListener.log("login username is: "+prop.getProperty("username")+ "and password is :"+prop.getProperty("password"));
		Assert.assertTrue(accountPage.isLogoutLinkExist());
	}
	
	@Description("footer links exist test")
	@Test
	public void footerLinksExistTest(){
		List<String> footerList = loginPage.getFooterLinks();
		Assert.assertEquals(footerList.size(), AppConstants.DEFAULT_FOOTER_LINKS_COUNT);
	}
}

package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class HomePageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		homePage=loginPage.navigateToHomePage();
	}
	
	@Test
	public void isNALLogoExistTest() {
		boolean result=homePage.isNALLogoDisplayed();
		Assert.assertTrue(result);
	}
	
	@Test
	public void isMenuItemsExistTest() {
		List<String> menuList=homePage.getMenuList();
		Assert.assertEquals(menuList, AppConstants.EXPECTED_HOMEPAGE_MENU_LIST);
	}
	
	@Test
	public void isBrandLogoExistTest() {
		List<String> logoList=homePage.getBrandLogoList();
		Assert.assertEquals(logoList, AppConstants.EXPECTED_BRANDS_LOGO_LIST);
	}
	
	@Test
	public void isSwipperImageSectionExist() {
		boolean result=homePage.isSwipperImageSectionExist();
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void isTopHeaderMenuExistTest() {
		List<String> result=homePage.topMenuHeadersList();
		Assert.assertEquals(result, AppConstants.EXPECTED_TOP_HEADER_MENU_LIST);
	}

}

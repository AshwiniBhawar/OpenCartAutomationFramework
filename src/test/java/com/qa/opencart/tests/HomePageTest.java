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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP-4: Design the Open Cart App Home Page")
@Feature("F-4: design home page feature")
@Story("US-4: develope home page feature- menulist, logo etc.")
public class HomePageTest extends BaseTest{
	
	@Description("Navigate to the homepage")
	@BeforeClass
	public void accPageSetup() {
		homePage=loginPage.navigateToHomePage();
	}
	
	@Description("Homepage NAL logo exist test..")
	@Test
	public void isNALLogoExistTest() {
		boolean result=homePage.isNALLogoDisplayed();
		Assert.assertTrue(result);
	}
	
	@Description("Homepage menu items list exist test..")
	@Test
	public void isMenuItemsExistTest() {
		List<String> menuList=homePage.getMenuList();
		Assert.assertEquals(menuList, AppConstants.EXPECTED_HOMEPAGE_MENU_LIST);
	}
	
	@Description("Homepage brand logo exist test..")
	@Test
	public void isBrandLogoExistTest() {
		List<String> logoList=homePage.getBrandLogoList();
		Assert.assertEquals(logoList, AppConstants.EXPECTED_BRANDS_LOGO_LIST);
	}
	
	@Description("Homepage swipper image section exist test..")
	@Test
	public void isSwipperImageSectionExist() {
		boolean result=homePage.isSwipperImageSectionExist();
		Assert.assertEquals(result, true);
	}
	
	@Description("Homepage top menu header list exist test..")
	@Test
	public void isTopHeaderMenuExistTest() {
		List<String> result=homePage.topMenuHeadersList();
		Assert.assertEquals(result, AppConstants.EXPECTED_TOP_HEADER_MENU_LIST);
	}

}

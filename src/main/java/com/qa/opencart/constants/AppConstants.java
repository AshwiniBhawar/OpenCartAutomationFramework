package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {
	public static final int DEFAULT_SHORT_WAIT = 5;
	public static final int DEFAULT_MEDIUM_WAIT = 10;
	public static final int DEFAULT_LARGE_WAIT = 20;	
	
	public static final int DEFAULT_FOOTER_LINKS_COUNT = 15;	
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	
	public static final String ACC_PAGE_TITLE = "My Account";
	public static final String ACC_PAGE_FRACTION_URL = "route=account/account";
	public static final String USER_REGISTER_SUCCESS_MESSG= "Your Account Has Been Created!";

	public static final int ACC_PAGE_HEADERS_COUNT = 4;
	
	public static final List<String> EXPECTED_ACCOUNT_PAGE_HEADERS_LIST= List.of("My Account", 
										"My Orders", "My Affiliate Account", "Newsletter");
	
	public static final List<String> EXPECTED_HOMEPAGE_MENU_LIST= List.of("Desktops", 
			"Laptops & Notebooks","Components", "Tablets", "Software", "Phones & PDAs",
			"Cameras", "MP3 Players");
	
	public static final List<String> EXPECTED_BRANDS_LOGO_LIST= List.of("Harley Davidson", "Dell", "Disney", "Starbucks", "Nintendo", "NFL",
			"RedBull", "Sony", "Coca Cola", "Burger King", "Canon"); 
	
	public static final List<String> EXPECTED_TOP_HEADER_MENU_LIST = List.of("123456789", "My Account", "Wish List (0)", "Shopping Cart", "Checkout");
	
	public static final String PRODUCT_REVIEW_MESSAGE="Thank you for your review. It has been submitted to the webmaster for approval.";
	public static final String SHOPPING_CART_EMPTY_MSG="Your shopping cart is empty!";
	
	public static final String LOGIN_INVALID_CREDS_MESSG = "Warning: No match for E-Mail Address and/or Password.";
	public static final String LOGIN_BLANK_CREDS_MESSG = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";
}

package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class JavascriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavascriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	@Step("flash the element: {0}")
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");// grey
		for (int i = 0; i < 7; i++) {
			changeColor("rgb(0,200,0)", element);// green
			changeColor(bgcolor, element);// grey
		}
	}

	@Step("change the color of the element :{1} to {0}")
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	@Step("get title using js")
	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}

	@Step("get url using js")
	public String getURLByJS() {
		return js.executeScript("return document.URL;").toString();
	}

	@Step("refresh a browser using js")
	public void refreshBrowserByJS() {
		js.executeScript("history.go(0)");
	}

	@Step("navigate back using js")
	public void navigateToBackPage() {
		js.executeScript("history.go(-1)");
	}

	@Step("navigate forward using js")
	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}

	@Step("generate an alert using js")
	public void generateAlert(String message) {
		js.executeScript("alert('" + message + "')");
	}

	@Step("get page inner text using js")
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	@Step("click the element: {0} using js")
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	@Step("send a value into the textbox for id:{0} and value:{1} using js")
	public void sendKeysUsingWithId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
	}

	@Step("scroll page down using js")
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	@Step("scroll page downto the particular height {0} using js")
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	@Step("scroll page up using js")
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	@Step("scroll page till element:{0} using js")
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	@Step("draw a border around the element :{0} using js")
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	@Step("zoom a page using js")
	public void pageZoom(int zoomPercentage) {
		js.executeScript("document.body.style.zoom = '" + zoomPercentage + "%'");
	}
}

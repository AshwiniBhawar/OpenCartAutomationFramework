package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	
		private WebDriver driver;
		private Actions action;
		private Select select;
		private JavascriptUtil jsUtil;
		
		private static final Logger log = LogManager.getLogger(ElementUtil.class);
		
		public ElementUtil(WebDriver driver) {
			this.driver = driver;
			action= new Actions(driver);
			jsUtil= new JavascriptUtil(driver);
		}

		@Step("get a webelement:{0}")
		public WebElement getElement(By locator) {
			WebElement element=driver.findElement(locator);
			if(DriverFactory.highlightElement.equals("true")) {
				log.info("flashing the element:" +locator);
				jsUtil.flash(element);
			}
			return element;
		}

		@Step("send a value {1} into the textbox which has {0} locator")
		public void doSendKeys(By locator, String... value) {
			log.info("entering the value : " + Arrays.toString(value) + " into locator: " + locator);
			if (value == null) {
				log.error("value : " + value + " is null...");
				throw new ElementException("===value can not be null====");
			}
			WebElement ele=getElement(locator);
			ele.clear();
			ele.sendKeys(value);
		}
		
		@Step("send multiple values {1} into the textbox which has {0} locator")
		public void doMultipleSendKeys(By locator, CharSequence... value) {			//send multiple values comma seperated
//			for(CharSequence v:value) {
//				if(v==null) {
//					throw new ElementException("===value can not be null====");
//				}
//			}
			getElement(locator).sendKeys(value);
		}

		@Step("click a {0} locator")
		public void doClick(By locator) {
			log.info("clicking on element using : " + locator);
			getElement(locator).click();
		}

		@Step("get a text of {0} locator")
		public String doElementGetText(By locator) {
			log.info("get the text of the element: " + locator);
			return getElement(locator).getText();
		}

		@Step("checking the element :{0} is displayed on the page")
		public boolean isElementDisplayed(By locator) {
			try {
				return getElement(locator).isDisplayed();
			} catch (NoSuchElementException e) {
				log.info("Element is not displayed on the page: " + locator);
				return false;
			}
		}
		
		@Step("checking the element :{0} is displayed on the page")
		public boolean isElementDisplayed(WebElement element) {
			try {
				return element.isDisplayed();
			} catch (NoSuchElementException e) {
				log.info("Element is not displayed on the page: " + element);
				return false;
			}
		}

		@Step("checking the element :{0} is displayed on the page for mandatory fields")
		public boolean isElementDisplayedForMandortyFields(By locator) {
			try {
				return getElement(locator).isDisplayed();
			} catch (NoSuchElementException e) {
				log.error("Element is not displayed on the page: " + locator);
				throw new ElementException("Element is not displayed on the page: " + locator);
			}
		}

		@Step("checking the element :{0} is eanbled on the page")
		public boolean isElementEnabled(By locator) {
			try {
				return getElement(locator).isEnabled();
			} catch (NoSuchElementException e) {
				log.info("Element is not enabled on the page: " + locator);
				return false;
			}
		}

		@Step("get the dom attribute value of {1} for {0}")
		public String getElementDOMAttributeValue(By locator, String attrName) {
			return getElement(locator).getDomAttribute(attrName);
		}

		@Step("get the dom attribute property value of {1} for {0}")
		public String getElementDOMPropertyValue(By locator, String propName) {
			return getElement(locator).getDomProperty(propName);
		}

		@Step("checking {0} exist")
		public boolean isElementExist(By locator) {
			if (getElements(locator).size() == 1) {
				log.info("the element : " + locator + " is present on the page one time");
				return true;
			} else {
				log.info("the element : " + locator + " is not present on the page one time");
				return false;
			}
		}

		@Step("checking {0} exist within particular range")
		public boolean isElementExist(By locator, int expectedEleCount) {
			if (getElements(locator).size() == expectedEleCount) {
				log.info("the element : " + locator + " is present on the page " + expectedEleCount + " time");
				return true;
			} else {
				log.info("the element : " + locator + " is not present on the page " + expectedEleCount + " time");
				return false;
			}
		}

		@Step("find the elements for {0}")
		public List<WebElement> getElements(By locator) {
			return driver.findElements(locator);
		}

		@Step("get the webelements text list for {0}")
		public List<String> getElementsTextList(By locator) {
			List<WebElement> eleList = getElements(locator);
			List<String> eleTextList = new ArrayList<String>();// pc=0,vc=10; []
			for (WebElement ele : eleList) {
				String text = ele.getText();
				if (text.length() != 0) {
					eleTextList.add(text);
				}
			}
			return eleTextList;
		}

		@Step("get the webelements list size for {0}")
		public int getElementsCount(By locator) {
			return getElements(locator).size();
		}

		@Step("click the element where text conatins {1}")
		public boolean clickElement(By locator, String eleText, String expectedPageTitle) throws InterruptedException {
			List<WebElement> eleList = getElements(locator);
			log.info("total number of elements: " + eleList.size());

			for (WebElement e : eleList) {
				String text = e.getText();
				log.info("text of an element "+e+" is: "+text);
				if (text.contains(eleText)) {
					e.click();
					String actualPageTitle = driver.getTitle();
					log.info(actualPageTitle);
					if (actualPageTitle.contains(expectedPageTitle)) {
						return true;
					}
				}
			}
			return false;
		}

		@Step("click the element where text conatins {1}")
		public boolean doSearch(By searchLocator, String searchKey, By suggestionsLocator, String suggestionValue,
				String expectedPageTitle){

			doSendKeys(searchLocator, searchKey);

			List<WebElement> suggList = getElements(suggestionsLocator);
			log.info("total number of suggestions: " + suggList.size());

			for (WebElement e : suggList) {
				String text = e.getText();
				log.info("text of an element "+e+" is: "+text);
				if (text.contains(suggestionValue)) {
					e.click();
					String actualPageTitle = driver.getTitle();
					log.info(actualPageTitle);
					if (actualPageTitle.contains(expectedPageTitle)) {
						return true;
					}
				}
			}
			return false;
		}
		
		
		//*******************Select drop down utils*************//
		
		@Step("search the element :{0}")
		private void selectElement(By locator) {
			select = new Select(getElement(locator));
		}
		
		@Step("select the element:{0} by index")
		public void doSelectByIndex(By locator, int index) {
			selectElement(locator);
			select.selectByIndex(index);
		}
		
		@Step("select the element:{0} by visible text")
		public void doSelectByVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.selectByVisibleText(eleText);
		}
		
		@Step("select the element:{0} by fractional visible text")
		public void doSelectByContainsVisibleText(By locator, String value) {
			selectElement(locator);
			select.selectByContainsVisibleText(value);
		}
		
		@Step("select the element:{0} by value")
		public void doSelectByValue(By locator, String value) {
			selectElement(locator);
			select.selectByValue(value);
		}
		
		@Step("select all the elements:{0}")
		public List<String> getAllSelectedOptionsList(By locator) {
			selectElement(locator);
			List<WebElement> options = select.getAllSelectedOptions();
			log.info("total no. of selected values: " +options.size());
			List<String> allSelectedOptions= new ArrayList<String>();
			
			for(WebElement e:options) {
				allSelectedOptions.add(e.getText());
			}
			
			return allSelectedOptions;
		}
		
		@Step("get count of the element:{0}")
		public int getAllSelectedOptionsCount(By locator) {
			selectElement(locator);
			return select.getAllSelectedOptions().size();
		}

		@Step("get dropdown values of the element:{0}")
		public List<String> getDropDownValuesList(By locator) {
			selectElement(locator);
			List<WebElement> optionsList = select.getOptions();
			log.info("total number of options: " + optionsList.size());

			List<String> optionsValueList = new ArrayList<String>();// pc=0, size=0, []
			for (WebElement e : optionsList) {
				String text = e.getText();
				optionsValueList.add(text);
			}

			return optionsValueList;
		}
		
		@Step("get dropdown values count of the element:{0}")
		public int getDropDownOptionsCount(By locator) {
			selectElement(locator);
			return select.getOptions().size();
		}
		
		@Step("select dropdown value of the element:{0} which has value:{1}")
		public void selectDropDownValue(By locator, String value) {
			List<WebElement> optionsList = getElements(locator);
			log.info("dropdown values size is: "+optionsList.size());
			for (WebElement e : optionsList) {
				String text = e.getText();
				if (text.contains(value)) {
					e.click();
					break;
				}
			}
		}
		
		@Step("select dropdown first value")
		public String getDropDownFirstSelectValue(By locator) {
			selectElement(locator);
			return select.getFirstSelectedOption().getText();
		}
		
		@Step("check dropdown has multiple values or not :{0}")
		public boolean isDropDownIsMultiple(By locator) {
			selectElement(locator);
			boolean isMultiple = select.isMultiple();
			log.info("is dropdown multiple choice: "+ isMultiple);
			return isMultiple;
		}
		
		@Step("deselect the element:{0} by visible text:{1}")
		public void doDeSelectByVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.deselectByVisibleText(eleText);
		}
		
		@Step("deselect the element:{0} by fraction visible text:{1}")
		public void doDeSelectByContainsVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.deSelectByContainsVisibleText(eleText);
		}
		
		@Step("deselect the element:{0} by index")
		public void doDeSelectByIndex(By locator, int index) {
			selectElement(locator);
			select.deselectByIndex(index);
		}
		
		@Step("deselect the element:{0} by value")
		public void doDeSelectByValue(By locator, String eleText) {
			selectElement(locator);
			select.deselectByVisibleText(eleText);
		}
		
		@Step("deselect all the selected element:{0}")
		public void doDeSelectAll(By locator) {
			selectElement(locator);
			select.deselectAll();
		}
		
		//*************************************Actions utils**************************************//
		
		@Step("move to the element:{0} using action class")
		private void moveToElement(By locator) throws InterruptedException {
			Thread.sleep(1000);
			action.moveToElement(getElement(locator)).perform();
			
		}
		
		@Step("handling level 2 sub menus using action class")
		public void menuSubMenuHandlingLevel2(By parentMenu,By childMenu) throws InterruptedException {
			moveToElement(parentMenu);
			Thread.sleep(1000);
			doClick(childMenu);
		}
		
		@Step("handling level 3 sub menus using action class")
		public void menuSubMenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3) throws InterruptedException {
			doClick(menuLevel1);
			moveToElement(menuLevel2);
			Thread.sleep(1000);
			doClick(menuLevel3);
		}
		
		@Step("handling level 4 sub menus using action class")
		public void menuSubMenuHandlingLevel4(String actionType,By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4) throws InterruptedException {
			if(actionType.equalsIgnoreCase("click")) {
				doClick(menuLevel1);
			}
			else if(actionType.equalsIgnoreCase("mousehover")) {
				moveToElement(menuLevel1);
			}
			
			moveToElement(menuLevel2);
			moveToElement(menuLevel3);
			Thread.sleep(1000);
			doClick(menuLevel4);
		}
		
		@Step("drag:{0} and drop{1} the element using actions class")
		public void dragAndDrop(By srcLocator, By destLocator) {
			action.dragAndDrop(getElement(srcLocator), getElement(destLocator));
		}
		
		@Step("sending a value:{1} in the textbox for the element:{0} using actions class")
		public void doActionsSendKeys(By locator, String value) {
			action.sendKeys(getElement(locator), value).perform();
		}

		@Step("click on the element:{0} using actions class")
		public void doActionsClick(By locator) {
			action.click(getElement(locator)).perform();
		}

		@Step("sending a value:{1} in the textbox for the element:{0} using actions class")
		public void doSendKeysWithPause(By locator, String value, long pauseTime) {

			if (value == null) {
				log.error("===value can not be null");
				throw new RuntimeException("===value can not be null");
			}

			char val[] = value.toCharArray();
			for (char ch : val) {
				action.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
			}

		}

		@Step("sending a value:{1} in the textbox for the element:{0} using actions class")
		public void doSendKeysWithPause(By locator, String value) {

			if (value == null) {
				log.error("===value can not be null");
				throw new RuntimeException("===value can not be null");
			}

			char val[] = value.toCharArray();
			for (char ch : val) {
				action.sendKeys(getElement(locator), String.valueOf(ch)).pause(200).perform();
			}

		}
		
		@Step("scroll to the element:{0} using actions class")
		public void scrollToElement(By locator) {
			action.scrollToElement(getElement(locator)).perform();
		}
		
		@Step("right click on the element:{0} using actions class")
		public void contextClick(By locator) {
			action.contextClick(getElement(locator)).perform();
		}
		

		// *********************Wait util****************//

		/**
		 * An expectation for checking that an element is present on the DOM of a page.
		 * This does not necessarily mean that the element is visible.
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		@Step("waiting for a presence of the element :{0}")
		public WebElement waitForElementPresence(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if(DriverFactory.highlightElement.equals("true")) {
				jsUtil.flash(element);
			}
			return element;
		}

		/**
		 * An expectation for checking that an element is present on the DOM of a page
		 * and visible. Visibility means that the element is not only displayed but also
		 * has a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		
		@Step("waiting for a visiblity of the element :{0}")
		public WebElement waitForElementVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			if(DriverFactory.highlightElement.equals("true")) {
				jsUtil.flash(element);
			}
			return element;
		}

		/**
		 * An expectation for checking that there is at least one element present on a
		 * web page.
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		@Step("waiting for a presence of the elements:{0}")
		public List<WebElement> waitForElementsPresence(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		}

		/**
		 * An expectation for checking that all elements present on the web page that
		 * match the locator are visible. Visibility means that the elements are not
		 * only displayed but also have a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		
		@Step("waiting for a visiblity of the elements:{0}")
		public List<WebElement> waitForElementsVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}

		/**
		 * An expectation for checking an element is visible and enabled such that you
		 * can click it.
		 * 
		 * @param locator
		 * @param timeout
		 */
		
		@Step("waiting for the element:{0} to click")
		public void clickElementWhenReady(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}

		
		public Alert waitForAlert(int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.alertIsPresent());
		}

		@Step("waiting for an alert and get the text")
		public String getAlertText(int timeout) {
			return waitForAlert(timeout).getText();
		}

		@Step("waiting for an alert and accept it")
		public void acceptAlert(int timeout) {
			waitForAlert(timeout).accept();
		}

		@Step("waiting for an alert and dismiss it")
		public void dismissAlert(int timeout) {
			waitForAlert(timeout).dismiss();
		}

		@Step("waiting for an alert and send a value in textbox:{1}")
		public void sendKeysInAlert(int timeout, String value) {
			waitForAlert(timeout).sendKeys(value);
		}

		@Step("waiting for page title with fraction value: {0}")
		public String waitForTitleContains(String fractionTitleValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.titleContains(fractionTitleValue));
			} catch (TimeoutException e) {
				log.error("expected title value : " + fractionTitleValue + " is not present");
			}

			return driver.getTitle();
		}
		
		@Step("waiting for page title with expected value: {0}")
		public String waitForTitleIs(String expectedTitleValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.titleIs(expectedTitleValue));
			} catch (TimeoutException e) {
				log.error("expected title value : " + expectedTitleValue + " is not present");
			}

			return driver.getTitle();
		}
		@Step("waiting for a fractional url:{0} to be present on the page")
		public String waitForURLContains(String fractionURLValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.urlContains(fractionURLValue));
			} catch (TimeoutException e) {
				log.error("expected URL value : " + fractionURLValue + " is not present");
			}

			return driver.getCurrentUrl();
		}

		@Step("waiting for an url:{0} to be present on the page")
		public String waitForURLIs(String epxectedURLValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.urlToBe(epxectedURLValue));
			} catch (TimeoutException e) {
				log.error("expected URL value : " + epxectedURLValue + " is not present");
			}

			return driver.getCurrentUrl();
		}

		@Step("waiting for a window using windowIndex")
		public boolean waitForWindow(int expectedNoOfWindows, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
			} catch (TimeoutException e) {
				log.error("expected number of windows are correct");
				return false;
			}
		}

		@Step("waiting for a frame using locator:{0}")
		public boolean waitForFrame(By frameLocator, int timeout) {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
				return true;
			} catch (TimeoutException e) {
				log.error("frame is not present on the page");
				return false;
			}

		}

		@Step("waiting for a frame using frameIndex")
		public boolean waitForFrame(int frameIndex, int timeout) {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
				return true;
			} catch (TimeoutException e) {
				log.error("frame is not present on the page");
				return false;
			}

		}

		@Step("waiting for a frame using NameorID:{0}")
		public boolean waitForFrame(String frameNameOrID, int timeout) {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
				return true;
			} catch (TimeoutException e) {
				log.error("frame is not present on the page");
				return false;
			}

		}
		
		
		//******************FluentWait Utils************//
		
		@Step("waiting for a visibility of the element:{0} using fluent wait")
		public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.withMessage("=====ELEMENT NOT VISIBLE ON THE PAGE====");
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		@Step("waiting for a presence of the element:{0} using fluent wait")
		public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.withMessage("=====ELEMENT NOT PRESENT ON THE PAGE====");
			
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		
		@Step("waiting for a frame using fluent wait")
		public void waitForFrameWithFluentWait(By frameLocator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchFrameException.class)
					.withMessage("=====FRAME NOT VISIBLE ON THE PAGE====");
			
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		@Step("waiting for an alert using fluent wait")
		public Alert waitForAlertWithFluentWait(int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoAlertPresentException.class)
					.withMessage("=====Alert NOT VISIBLE ON THE PAGE====");
			
			return wait.until(ExpectedConditions.alertIsPresent());
		}

}

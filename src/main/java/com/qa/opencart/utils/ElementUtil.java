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

		@Step("get a webelement for {0} locator")
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

		//@Step("click the element where text conatins {1}")
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
		
		private void selectElement(By locator) {
			select = new Select(getElement(locator));
		}
		
		public void doSelectByIndex(By locator, int index) {
			selectElement(locator);
			select.selectByIndex(index);
		}
		
		public void doSelectByVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.selectByVisibleText(eleText);
		}
		
		public void doSelectByContainsVisibleText(By locator, String value) {
			selectElement(locator);
			select.selectByContainsVisibleText(value);
		}
		
		public void doSelectByValue(By locator, String value) {
			selectElement(locator);
			select.selectByValue(value);
		}
		
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
		
		public int getAllSelectedOptionsCount(By locator) {
			selectElement(locator);
			return select.getAllSelectedOptions().size();
		}

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
		
		public int getDropDownOptionsCount(By locator) {
			selectElement(locator);
			return select.getOptions().size();
		}
		
		
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
		
		public String getDropDownFirstSelectValue(By locator) {
			selectElement(locator);
			return select.getFirstSelectedOption().getText();
		}
		
		public boolean isDropDownIsMultiple(By locator) {
			selectElement(locator);
			boolean isMultiple = select.isMultiple();
			log.info("is dropdown multiple choice: "+ isMultiple);
			return isMultiple;
		}
		
		public void doDeSelectByVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.deselectByVisibleText(eleText);
		}
		
		public void doDeSelectByContainsVisibleText(By locator, String eleText) {
			selectElement(locator);
			select.deSelectByContainsVisibleText(eleText);
		}
		
		public void doDeSelectByIndex(By locator, int index) {
			selectElement(locator);
			select.deselectByIndex(index);
		}
		
		public void doDeSelectByValue(By locator, String eleText) {
			selectElement(locator);
			select.deselectByVisibleText(eleText);
		}
		
		public void doDeSelectAll(By locator) {
			selectElement(locator);
			select.deselectAll();
		}
		
		//*************************************Actions utils**************************************//
		
		private void moveToElement(By locator) throws InterruptedException {
			Thread.sleep(1000);
			action.moveToElement(getElement(locator)).perform();
			
		}
		
		public void menuSubMenuHandlingLevel2(By parentMenu,By childMenu) throws InterruptedException {
			moveToElement(parentMenu);
			Thread.sleep(1000);
			doClick(childMenu);
		}
		
		public void menuSubMenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3) throws InterruptedException {
			doClick(menuLevel1);
			moveToElement(menuLevel2);
			Thread.sleep(1000);
			doClick(menuLevel3);
		}
		
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
		
		public void dragAndDrop(By srcLocator, By destLocator) {
			action.dragAndDrop(getElement(srcLocator), getElement(destLocator));
		}
		
		public void doActionsSendKeys(By locator, String value) {
			action.sendKeys(getElement(locator), value).perform();
		}

		public void doActionsClick(By locator) {
			action.click(getElement(locator)).perform();
		}

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
		
		public void scrollToElement(By locator) {
			action.scrollToElement(getElement(locator)).perform();
		}
		
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
		
		@Step("waiting for element :{0} visible within the timeout: {1}")
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
		public void clickElementWhenReady(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}

		public Alert waitForAlert(int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.alertIsPresent());
		}

		public String getAlertText(int timeout) {
			return waitForAlert(timeout).getText();
		}

		public void acceptAlert(int timeout) {
			waitForAlert(timeout).accept();
		}

		public void dismissAlert(int timeout) {
			waitForAlert(timeout).dismiss();
		}

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

		public String waitForURLContains(String fractionURLValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.urlContains(fractionURLValue));
			} catch (TimeoutException e) {
				log.error("expected URL value : " + fractionURLValue + " is not present");
			}

			return driver.getCurrentUrl();
		}

		public String waitForURLIs(String epxectedURLValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.urlToBe(epxectedURLValue));
			} catch (TimeoutException e) {
				log.error("expected URL value : " + epxectedURLValue + " is not present");
			}

			return driver.getCurrentUrl();
		}

		public boolean waitForWindow(int expectedNoOfWindows, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
			} catch (TimeoutException e) {
				log.error("expected number of windows are correct");
				return false;
			}
		}

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
		
		public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.withMessage("=====ELEMENT NOT VISIBLE ON THE PAGE====");
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		
		public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.withMessage("=====ELEMENT NOT PRESENT ON THE PAGE====");
			
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		
		
		public void waitForFrameWithFluentWait(By frameLocator, int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoSuchFrameException.class)
					.withMessage("=====FRAME NOT VISIBLE ON THE PAGE====");
			
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		
		public Alert waitForAlertWithFluentWait(int timeout, int pollingtime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofMillis(pollingtime))
					.ignoring(NoAlertPresentException.class)
					.withMessage("=====Alert NOT VISIBLE ON THE PAGE====");
			
			return wait.until(ExpectedConditions.alertIsPresent());
		}

}

package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlightElement;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	/**
	 * This method is initializing driver on the basis of browser..
	 * 
	 * @param browserName
	 * @return driver instance
	 */

	public WebDriver initDriver(Properties prop) {
		log.info("initialize the driver");
		String browserName = prop.getProperty("browser");
		log.info("browser name is: " + browserName);

		highlightElement = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		Boolean remoteExec=Boolean.parseBoolean(prop.getProperty("remote"));

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if(remoteExec) {
				init_remoteDriver("chrome");
			}
			else {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
		case "firefox":
			if(remoteExec) {
				init_remoteDriver("firefox");
			}
			else {
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":
			if(remoteExec) {
				init_remoteDriver("edge");
			}
			else {
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		default:
			log.error(AppError.INVALID_BROWSER_MSG + " : " + browserName);
			throw new FrameworkException("=====Invalid Browser=====");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}
	
	/**
	 * this is used to init the remote webdriver with selenium grid
	 * @param string
	 */

	private void init_remoteDriver(String browserName) {
		try {
		switch(browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			break;
		case "edge":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			break;
		case "firefox":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			break;
		default:
			log.error(AppError.INVALID_BROWSER_MSG + browserName);
			throw new FrameworkException("=========Invalid Browser=========");
			} 	
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
			
	}

	/**
	 * this is used to get the local copy of the driver any time..
	 * 
	 * @return
	 */

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is initializing the prop with properties file..
	 * 
	 * @return prop instance
	 */

	public Properties initProp() {
		log.info("initialize the properties");

		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		log.info("Env name is: " + envName);
		try {
			if (envName == null) {
				log.info("no env.. is passed, hence running tcs on QA environment..");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			}

			else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.prod.properties");
					break;
				default:
					log.error("Env value is invalid..please pass the right env value");
					throw new FrameworkException("=====Invalid Environment=====");
				}
			}		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * takescreenshot logic
	 * 
	 * @return
	 */

	public static File getScreenshotFile() {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		return ts.getScreenshotAs(OutputType.FILE);
	}

	public static String getScreenshotBase64() {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		return ts.getScreenshotAs(OutputType.BASE64);
	}

	public static byte[] getScreenshotByte() {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		return ts.getScreenshotAs(OutputType.BYTES);
	}
}

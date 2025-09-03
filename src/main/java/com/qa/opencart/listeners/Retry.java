package com.qa.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = 3;
	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {								//check the status of test case- not success					
			if (count < maxTry) {								//check if count is less than maxTry
				count++;										//increase the count by 1
				result.setStatus(ITestResult.FAILURE);			//mark test as failed
				return true;									//tells TestNg to re-run the test
			} else {	
				result.setStatus(ITestResult.FAILURE);  		//If maxTry reached, TestNG will mark it as fail.
			}
		} else {
			result.setStatus(ITestResult.SUCCESS);				//If test passes, TestNG will mark it as passed.
		}
		return false;
	}

}

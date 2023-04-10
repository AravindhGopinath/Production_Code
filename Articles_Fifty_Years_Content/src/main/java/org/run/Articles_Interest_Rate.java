package org.run;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import base.BaseClass;
import excel.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Matcher;

public class Articles_Interest_Rate extends BaseClass {

	@DataProvider
	public Iterator<Object[]> getTestData() {

		ArrayList<Object[]> testData = ExcelUtils.getDataFromexcel();
		return testData.iterator();

	}

	@BeforeTest
	public void browserLaunch() {

		headless("chrome");
	}

	@AfterTest
	public void browserQuit() {

		driver.quit();
	}

	
	@Test(dataProvider = "getTestData")

	public void verifyLink(String INDEX, String Articles_URL) throws Throwable {

		try {

			System.out.println(INDEX + ") ==> " + Articles_URL);

			loadUrl(Articles_URL);

			try {

				WebElement pl = driver.findElement(By.tagName("body"));

				String text = pl.getText();

				String Verify_1 = "Shriram completes 50 years of service!";

				String Verify_2 = "Shriram Jubilee Deposit - a 50-month investment scheme.";

				
//				WebElement fifty_Years_Content = driver
//						.findElement(By.xpath("//h6[text()='Shriram completes 50 years of service!']"));
//
//				WebElement fifty_Years_Content_2 = driver.findElement(
//						By.xpath("//span[text()='Shriram Jubilee Deposit - a 50-month investment scheme.']"));

				if (text.contains(Verify_1) || text.contains(Verify_2) ) {

					System.out.println("Jubilee Content is presented");
					int parseInt = Integer.parseInt(INDEX);
					ExcelUtils.writeinexcel("Jubilee Content is presented", parseInt);

				}

				else {
					System.out.println("There's no Jubilee content found");
					int parseInt = Integer.parseInt(INDEX);
					ExcelUtils.writeinexcel("There's no Jubilee content found", parseInt);
				}

			}

			catch (Exception e) {

				System.out.println("There's no Jubilee content found");
				int parseInt = Integer.parseInt(INDEX);
				ExcelUtils.writeinexcel("There's no Jubilee content found", parseInt);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

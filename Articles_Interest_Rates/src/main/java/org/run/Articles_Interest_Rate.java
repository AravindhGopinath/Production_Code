package org.run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import base.BaseClass;
import excel.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Matcher;

public class Articles_Interest_Rate extends BaseClass {

	static List<String> data = new ArrayList<String>();

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

			WebElement pl = driver.findElement(By.tagName("body"));

//			WebElement Particular_div = driver.findElement(By.xpath("//div[@class='eligi_bility']"));

			String text = pl.getText();

			String txt = "EFFECTIVE";

			String txt1 = "YIELD";

			double interestrate = 9.10;

			Pattern pattern = Pattern.compile("[0-9]*\\.[0-9]+", Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(text);

			if (matcher.find()) {
				String group = matcher.group(0);

				System.out.println(group);

				double parseDouble = Double.parseDouble(group);

				if (parseDouble > interestrate) {

					String currentUrl = driver.getCurrentUrl();

					String print = "Interest Rate is above 9.10% ==> " + group + " ==> " + currentUrl;

					System.out.println(print);

					int parseInt = Integer.parseInt(INDEX);

					ExcelUtils.writeinexcel(print, parseInt);

//					System.out.println("Interest Rate is above 9.10%");
				}

				else {

					int parseInt = Integer.parseInt(INDEX);
					ExcelUtils.writeinexcel("There's no Interest Rate above 9.10% ", parseInt);

				}

			}

			else if (text.toLowerCase().contains(txt.toLowerCase())
					|| text.toLowerCase().contains(txt1.toLowerCase())) {

				String currentUrl = driver.getCurrentUrl();

				String print = "Effective yield Content is presented ==> " + currentUrl;

				System.out.println(print);

//				data.add(print); *********************************************
//
//				String join = String.join(", " + "\n", data); *********************************************
//				System.out.println(join); *********************************************
				int parseInt = Integer.parseInt(INDEX);
				ExcelUtils.writeinexcel(print, parseInt);
//				data.clear(); *********************************************

			}

			else {
				System.out.println("There's no Interest Rate found");
//				String join = String.join(", " + "\n", data); *********************************************
//				System.out.println(join); *********************************************
				int parseInt = Integer.parseInt(INDEX);
				ExcelUtils.writeinexcel("There's no Interest Rate above 9.10% or Effective Yield content found",
						parseInt);
//				data.clear(); *********************************************
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

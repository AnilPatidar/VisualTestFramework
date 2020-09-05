package selenium.test.applitools;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Visual Test
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PlainSeleniumScript {

	final String homeLoctor = "//*[@id=\"atIdViewHeader\"]/div/div[2]/div[1]/div[1]/a";
	final String headerLoctor = "h.fvzqb94btn9";

	@Test
	public void Test() throws Exception {
		System.out.println("Test Started");

		WebDriver driver = getDriver();

		try {

			driver.get("https://sites.google.com/view/helpcenter24x7/copy-of-home");

			assertTrue(driver.findElement(By.xpath(homeLoctor))
					.isDisplayed());

			assertTrue(driver.findElement(By.id(headerLoctor))
					.getText().equals("How can we help you?"));

			driver.quit();

		} finally {
			driver.quit();
		}
	}

	public WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", "/Users/anilpatidar/Downloads/chromedriver");
		return new ChromeDriver();
	}

}

package selenium.test.applitools;

import org.junit.Before;
import org.junit.Test;

/**
 * Visual Test
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;

public class SeleniumAppliToolsLevel1 {

	Eyes eyes = new Eyes();

	@Test
	public void Test() throws Exception {
		System.out.println("Test Started");

		WebDriver driver = getDriver();

		try {

			eyes.setMatchLevel(MatchLevel.NONE);
			
			driver = eyes.open(driver, "Help Center", "Help Center for New Customers");

			driver.get("https://sites.google.com/view/helpcenter24x7/copy-of-home");

			eyes.checkWindow("Home Page");

			TestResults result = eyes.close(false);
			if (result.isPassed()) {
				System.out.println("Test Passed.");
			} else {
				System.out.println("Test Failed.");

			}
			driver.quit();

		} finally {
			eyes.abortIfNotClosed();
		}
	}

	@Before
	public void setUpAppliTool() {

		// obtain the API key from an environment variable and set it
		String apiKey = "q7DLM9izvfLo9qK89EK3C6w4XKCEBZnvcV9x0OyU1078c110";
		eyes.setApiKey(apiKey);

	}

	public WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", "/Users/anilpatidar/Downloads/chromedriver");
		return new ChromeDriver();
	}

}

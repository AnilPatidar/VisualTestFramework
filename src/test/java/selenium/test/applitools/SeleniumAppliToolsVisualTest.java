package selenium.test.applitools;

import org.junit.Before;
import org.junit.Test;

/**
 * Visual Test
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.selenium.Eyes;

public class SeleniumAppliToolsVisualTest {

	Eyes eyes = new Eyes();
 
	@Test
	public void Test() throws Exception {
		WebDriver driver = getDriver();
		System.out.println("Chrome is ready for testing..");
		System.out.println("Test Started");
		try{
		driver= eyes.open(driver, "Youtube", "Youtube Home Page");
		driver.get("https://sites.google.com/view/helpcenter24x7/copy-of-home");
		System.out.println("Visual Check has been started");
		eyes.checkWindow("Validate Youtube Home Page");
 		System.out.println("Visual Check has been completed.");
		eyes.close(false);
		driver.quit();
		System.out.println("Test Completed.");
		}finally {
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
		System.out.println("Chrome Browser getting initialized.");
		System.setProperty("webdriver.chrome.driver", "/Users/anilpatidar/Downloads/chromedriver");
		return new ChromeDriver();
	}

}

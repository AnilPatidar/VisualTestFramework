package selenium.test.applitools;

import org.junit.Before;
import org.junit.Test;

/**
 * Visual Test
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

public class ApplitoolsAltraGrid {

	private String apiKey = "q7DLM9izvfLo9qK89EK3C6w4XKCEBZnvcV9x0OyU1078c110";
	private EyesRunner runner = null;
	private Configuration suiteConfig;
	private Eyes eyes;
 
	@Test
	public void Test() throws Exception {
		System.out.println("Test Started");

		WebDriver driver = getDriver();

		try {

			Configuration testConfig = eyes.getConfiguration();
			testConfig.setTestName("Help Center for New Customers");
			testConfig.setMatchLevel(MatchLevel.STRICT);

			eyes.setConfiguration(testConfig);

			// Open Eyes, the application,test name and viewport size are allready
			// configured
			driver = eyes.open(driver);

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

	public void VisualRunnerSetup() {
 
		runner = new VisualGridRunner(1);
		// Create a configuration object, we will use this when setting up each test
	 
		suiteConfig = new Configuration();
		suiteConfig
		 // Visual Grid configuration
		   .addBrowser(1024, 786, BrowserType.FIREFOX)
		   .addBrowser(900, 600, BrowserType.IE_10)
		   .addDeviceEmulation(DeviceName.iPhone_4, ScreenOrientation.PORTRAIT)
		    // other configurations ....
			.setApiKey(apiKey)
				.setAppName("Help Center");

		eyes = new Eyes(runner);
		eyes.setConfiguration(suiteConfig);

	}

	public WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", "/Users/anilpatidar/Downloads/chromedriver");
		return new ChromeDriver();
	}

}

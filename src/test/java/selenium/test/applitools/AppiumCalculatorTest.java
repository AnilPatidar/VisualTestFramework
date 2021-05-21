package selenium.test.applitools;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.appium.Eyes;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumCalculatorTest {

	Eyes eyes = new Eyes();

	//if you get any issue related to screenshot, comment out selenium eyes-selenium-java3 dependency in pom.xml
	@Test
	public void calculatorTest() throws InterruptedException, MalformedURLException {

		String platform = "Android 7.1.1";
		
		URL url = new URL("http://localhost:4723/wd/hub");
		
		String[] platformInfo = platform.split(" ");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
		capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, 8201);
		capabilities.setCapability(MobileCapabilityType.APP,
				"/Users/anilpatidar/selenium-workspace/Visual-Testing-FrameWork/build/AndroidCalculator.apk");
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		
		WebDriver driver = new AndroidDriver<MobileElement>(url, capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			eyes.setMatchLevel(MatchLevel.EXACT);

			driver = eyes.open(driver, "Calculor App 1", "Calculator Welcome Screen Test 1");

			eyes.checkWindow("calculator page");

			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android2.calculator3:id/digit6")));
			
			driver.findElement(By.id("com.android2.calculator3:id/digit6")).click();
			
			eyes.checkWindow("calculator tutorial screen");
			
			TestResults result = eyes.close(false);
			
			if (result.isPassed()) {
				System.out.println("Test Passed.");
			} else {
				System.out.println("Test Failed.");

			}
		} finally {
			eyes.abortIfNotClosed();
			driver.quit();
		}

	}

	@Before
	public void setUpAppliTool() {
		// obtain the API key from an environment variable and set it
		String apiKey = "gUEXhdFt0wKucl1rHonePxaO106Ala3tEJa6gD6IJYpf4110";
		eyes.setApiKey(apiKey);

	}

}

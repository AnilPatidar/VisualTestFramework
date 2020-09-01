package selenium.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Visual Test
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;

public class AppiumOpenCVVisualTest {

	Eyes eyes = new Eyes();
	WebDriver driver = null;
	AndroidDriver<WebElement> appDriver = null;
	String connectedDeviceName = null;

	@Test
	public void youtubeTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/anilpatidar/Downloads/chromedriver");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("app", "");
		
		URL server = new URL("http://localhost:4723/wd/hub");
		appDriver = new AndroidDriver<>(server, capabilities);

		connectedDeviceName = appDriver.getSessionDetail("deviceName").toString();

	   setUpAppliTool();

		Map<String, String> mobileEmulation = new HashMap<>();

		mobileEmulation.put("deviceName", "Nexus 5");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		driver = new ChromeDriver(chromeOptions);

		// eyes.open(driver,"Youtube","Youtube Home Page Test");
		System.out.println("Test Started");

		// driver.get("https://www.youtube.com/feed/trending");
		driver.get("https://www.youtube.com");

		System.out.println("Visual Check has been started");

		doVisualCheck("youtubeLoginPage");
		// eyes.checkWindow("Validate Youtube Home Page");

		takeScreenShot();

		// eyes.close(false);
		System.out.println("Visual Check has been completed.");

		driver.quit();
		appDriver.quit();
		System.out.println("Test Completed.");

	}

	public void setUpAppliTool() {

		// obtain the API key from an environment variable and set it
		String apiKey = "q7DLM9izvfLo9qK89EK3C6w4XKCEBZnvcV9x0OyU1078c110";
		eyes.setApiKey(apiKey);

		String batchName = "Youtube Test";
		String batchId = "1";

		// set the batch
		BatchInfo batchInfo = new BatchInfo(batchName);
		batchInfo.setId(batchId);
		eyes.setBatch(batchInfo);

	}

	public void takeScreenShot() throws IOException {

		String path = Paths.get("").toAbsolutePath().toString();
		System.out.println("Working Directory = " + path);

		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination

		File DestFile = new File(path + "/ScreenShots/youtube-" + System.currentTimeMillis() + ".png");

		// Copy file at destination

		FileUtils.copyFile(SrcFile, DestFile);
	}

	public void doVisualCheck(String checkName) throws Exception {
		float MATCH_THRESHOLD = 0.9f;

		String path = Paths.get("").toAbsolutePath().toString();
		String baselineFilename = path + "/ScreenShots/baseline/" + connectedDeviceName + "/" + checkName + ".png";
		File baselineImg = new File(baselineFilename);

		// If no baseline image exists for this check, we should create a baseline image
		if (!baselineImg.exists()) {
			System.out.println(String.format("No baseline found for '%s' check; capturing baseline instead of checking",
					checkName));
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File newBaseline = scrShot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(newBaseline, new File(baselineFilename));
			System.out.println("Captured base line.");
			return;
		}

		// Otherwise, if we found a baseline, get the image similarity from Appium. In
		// getting the similarity,
		// we also turn on visualization so we can see what went wrong if something did.
		SimilarityMatchingOptions opts = new SimilarityMatchingOptions();
		opts.withEnabledVisualization();

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File currentImg = scrShot.getScreenshotAs(OutputType.FILE);

		SimilarityMatchingResult res = appDriver.getImagesSimilarity(baselineImg, currentImg, opts);

		// If the similarity is not high enough, consider the check to have failed
		if (res.getScore() < MATCH_THRESHOLD) {
			File failViz = new File(path + "/ScreenShots/fail/" + connectedDeviceName + "/" + checkName + ".png");

			// Lets create the directory
			try {
				Files.createDirectories(Paths.get(path + "/ScreenShots/fail/" + connectedDeviceName));

			} catch (Exception err) {
				System.out.println("ERROR (Directory Create)" + err.getMessage());
			}

			res.storeVisualization(failViz);
			String messsage = String.format(
					"Visual check of '%s' failed; similarity match was only %f, and below the threshold of %f. Visualization written to %s.",
					checkName, res.getScore(), MATCH_THRESHOLD, failViz.getAbsolutePath());

			System.out.println(messsage);
			throw new Exception(messsage);
		}

		// Otherwise, it passed!
		System.out.println(
				String.format("Visual check of '%s' passed; similarity match was %f", checkName, res.getScore()));
	}
}

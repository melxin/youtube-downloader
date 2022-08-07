/*
 * Copyright (c) 2021, Xperiosa <https://github.com/xperiosa/> 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.yt2dl;

import com.yt2dl.utils.ResourceLoader;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Convert and download videos using Selenium API see:
 * <https://github.com/SeleniumHQ/selenium>
 */
@Slf4j
@AllArgsConstructor
public class Converter implements Callable<String>
{
	private final String url;
	private final String extension;

	@Override
	public String call()
	{
		// init web driver
		WebDriver driver = getWebDriver();

		String converterSite = "https://ytmp3.cc/" + url;
		driver.get(converterSite);

		if (extension.toLowerCase().equalsIgnoreCase("mp4"))
		{
			// Click mp4
			WebElement MP4_Button = driver.findElement(By.cssSelector("a#mp4"));
			MP4_Button.click();
		}

		FluentWait<WebDriver> wait = new FluentWait<>(driver)
			.pollingEvery(5, TimeUnit.SECONDS)
			.withTimeout(15, TimeUnit.MINUTES);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#download")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a#download")));

		// Get video title
		String videoTitle = driver.findElement(By.cssSelector("div#advise")).getText() + "." + extension.toLowerCase();
		log.info("Video title: {}", videoTitle);
		File video = new File(Constants.YT_DOWNLOADS_DIRECTORY + File.separator + videoTitle);

		// Download
		driver.findElement(By.cssSelector("a#download")).click();

		wait.until(x -> video.exists());

		driver.close();
		driver.quit();
		return "Finished downloading: " + videoTitle;
	}

	/**
	 * get web driver instance
	 */
	private WebDriver getWebDriver()
	{
		if (Constants.CHROME_DRIVER)
		{
			// Add chrome browser driver to System properties
			System.setProperty("webdriver.chrome.driver", ResourceLoader.CHROME_DRIVER.getAbsolutePath());

			// Change default download directory
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("download.default_directory", Constants.YT_DOWNLOADS_DIRECTORY.getAbsolutePath()); // <-- Different download dir
			options.setExperimentalOption("prefs", chromePrefs);

			// Initialize the browser driver with options
			WebDriver driver = new ChromeDriver(options);
			log.info("Successfully setup web-driver: {}", driver.toString());
			return driver;
		}

		/*if (Constants.FIREFOX_DRIVER)
		{
			System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
			WebDriver driver = new FirefoxDriver();
		}*/
		return null;
	}
}

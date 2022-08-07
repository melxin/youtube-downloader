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

import com.yt2dl.utils.DateTimeUtils;
import java.io.File;
import java.util.logging.Level;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Constants
{
	// YouTube URLs to download
	public static String[] URLS =
	{
		//"https://www.youtube.com/watch?v=mT2vUsrTlzE",
		"https://www.youtube.com/watch?v=oMR3UBbFuwA"
	};

	/**
	 * Extension format to download video in
	 *
	 * Only MP3 & MP4 are supported
	 */
	public static final String EXTENSION = "MP3"; //MP3, MP4

	// Driver to use
	public static final boolean CHROME_DRIVER = true;
	public static final boolean FIREFOX_DRIVER = false;

	// System property variables
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final File DOWNLOADS_DIRECTORY = new File(USER_HOME, "Downloads");
	public static final File DESKTOP_DIRECTORY = new File(USER_HOME, "Desktop");
	public static final File DOCUMENTS_DIRECTORY = new File(USER_HOME, "Documents");

	// Downloads directory
	public static final File MAIN_DOWNLOADS_DIRECTORY = new File(DESKTOP_DIRECTORY, "Youtube_Downloads");
	public static final File YT_DOWNLOADS_DIRECTORY = new File(MAIN_DOWNLOADS_DIRECTORY, DateTimeUtils.getCurrentDateString());

	// Disable seleniums logging
	private static final boolean DISABLE_SELENIUM_LOGGING = true;

	/**
	 * Post Initialization
	 */
	static
	{
		// Create downloads main dir
		if (!MAIN_DOWNLOADS_DIRECTORY.exists())
		{
			MAIN_DOWNLOADS_DIRECTORY.mkdir();
			log.debug("Created directory: {}", Constants.MAIN_DOWNLOADS_DIRECTORY.getAbsolutePath());
		}

		// Disable selenium logging
		if (DISABLE_SELENIUM_LOGGING)
		{
			System.setProperty("webdriver.chrome.silentOutput", "true");
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
			log.debug("Selenium logging is set to disabled");
		}
	}
}

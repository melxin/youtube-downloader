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
package com.yt2dl.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;

/**
 * ResourceLoader class used to load images and icons
 */
@Slf4j
public class ResourceLoader
{
	// Drivers
	public static File CHROME_DRIVER;
	public static File GECKO_DRIVER;
	
	static
	{
		try
		{
			CHROME_DRIVER = new File(ResourceLoader.class.getResource("/chromedriver").toURI());
			GECKO_DRIVER = new File(ResourceLoader.class.getResource("/geckodriver").toURI());
			Runtime.getRuntime().exec("chmod +x " + CHROME_DRIVER.getAbsolutePath());
			Runtime.getRuntime().exec("chmod +x " + GECKO_DRIVER.getAbsolutePath());
		}
		catch (URISyntaxException | IOException ex)
		{
			log.error("Failed to load resources", ex);
		}
	}
}

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Downloader
{
	private final ExecutorService executor = Executors.newFixedThreadPool(3); // Executor to submit Future<>

	private final AtomicInteger downloadCount = new AtomicInteger();

	/**
	 * ExecuteTasks
	 *
	 * @param tasks, list of tasks
	 * @throws InterruptedException
	 */
	private void executeTasks(List tasks) throws InterruptedException
	{
		// invoke
		List<Future> futures = executor.invokeAll(tasks, 60, TimeUnit.MINUTES);

		futures.stream().filter((future) -> (!future.isCancelled())).forEach((future) ->
		{
			try
			{
				VideoInfo result = (VideoInfo) future.get(15, TimeUnit.MINUTES);
				
				if (result == null) 
				{
				    log.error("Task failed...");
				    return;
				}
				
				downloadCount.incrementAndGet();
				log.info("Finished task: {}", result.toString());
			}
			catch (InterruptedException | ExecutionException | TimeoutException ex)
			{
				log.error("", ex);
			}
		});

		log.info("Finished all tasks...");
		executor.shutdown();
		System.exit(0);
	}

	/**
	 * Download
	 *
	 * @throws java.lang.InterruptedException
	 */
	public void Download() throws InterruptedException
	{
		// prepare tasks list
		List<Converter> tasks = new ArrayList<>();

		for (String url : Constants.URLS)
		{
			// Convert video log
			log.info("Convert video: {}", url);

			// Instance converter
			Converter converter = new Converter(url, Constants.EXTENSION);

			// Add converter to tasks list
			tasks.add(converter);
		}
		// Execute tasks
		executeTasks(tasks);
	}
}

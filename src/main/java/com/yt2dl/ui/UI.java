/*
 * Copyright (c) 2021, Xperiosa <https://github.com/xperiosa>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.yt2dl.ui;

import com.yt2dl.Constants;
import com.yt2dl.Downloader;
import java.awt.event.ActionEvent;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Xperiosa <https://github.com/xperiosa>
 */
@Slf4j
public class UI extends javax.swing.JFrame
{
	private javax.swing.JPanel panel;
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JTextArea textArea;
	private javax.swing.JButton downloadButton;

	/**
	 * Constructor
	 */
	public UI()
	{
		initComponents();
	}

	private void initComponents()
	{
		panel = new javax.swing.JPanel();
		scrollPane = new javax.swing.JScrollPane();
		textArea = new javax.swing.JTextArea();
		downloadButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		panel.setAutoscrolls(true);

		textArea.setColumns(20);
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);

		downloadButton.setText("Download");

		downloadButton.addActionListener((ActionEvent e) ->
		{
			if (textArea.getText().length() < 1)
			{
				return;
			}

			Constants.URLS = textArea.getText().split(System.lineSeparator());
			Downloader downloader = new Downloader();
			try
			{
				downloader.Download();
			}
			catch (InterruptedException ex)
			{
				log.error("", ex);
			}
		});

		javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
		panel.setLayout(panelLayout);
		panelLayout.setHorizontalGroup(
				panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panelLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
										.addGroup(panelLayout.createSequentialGroup()
												.addGap(0, 0, Short.MAX_VALUE)
												.addComponent(downloadButton)
												.addGap(0, 0, Short.MAX_VALUE)))
								.addContainerGap())
		);
		panelLayout.setVerticalGroup(
				panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(downloadButton)
								.addContainerGap())
		);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);

		pack();
	}

}

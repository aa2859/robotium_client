package com.WebControl.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.util.Log;

class ThreadUtil implements Runnable {
	private String character = "GB2312";
	private List<String> list;
	private InputStream inputStream;

	public ThreadUtil(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.list = list;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream,
					character));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					System.out.println("11 " + line);
					Log.i("123", line);
					list.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println(" 命令执行报错：" + e.getMessage());
		} finally {
			try {
				inputStream.close();
				if(br!=null)
					br.close();
			} catch (IOException e) {
				System.out.println(" 命令执行报错：" + e.getMessage());
			}
		}
	}
}

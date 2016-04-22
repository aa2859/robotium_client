package com.WebControl.control;

import java.util.ArrayList;
import java.util.List;



public class CommandUtil {
	// 保存进程的输出流信息
	private List<String> stdoutList = new ArrayList<String>();
	private List<String> erroroutList = new ArrayList<String>();

	public void executeCommand(String... command) throws Exception {
		for (String commands : command) {
			stdoutList.clear();
			erroroutList.clear();
			Process p = Runtime.getRuntime().exec(commands);
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(),
					stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(),
					erroroutList);
			stdoutUtil.start();
			erroroutUtil.start();
			p.waitFor();
		}
	}

	public List<String> getStdoutList() {
		return stdoutList;
	}

	public List<String> getErroroutList() {
		return erroroutList;
	}
}

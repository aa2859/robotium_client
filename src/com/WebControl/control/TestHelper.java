package com.WebControl.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.TrafficStats;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;
import com.robotium.solo.WebElement;

public class TestHelper {
	public static Solo solo;
	public static Test ts;

	public static int uid = 0;
	public static long startUpFlow = 0;
	public static long startDownFlow = 0;

	public static long endUpFlow = 0;
	public static long endDownFlow = 0;

	/*
	 * 查找WebElement
	 */
	public static WebElement findWebElement(TestStep step) {

		final boolean isInput = "input".equals(step.getTagName())
				|| "textarea".equals(step.getTagName());

		if (isInput && step.getUserXPath() == null
				&& step.getTextContent() != null) {
			// Log.i("123", "input");
			return findWebElement_ADV(step, solo.getConfig().timeout_large);
		}

		String xp = step.XPath();
		if (step.getUserXPath() != null)
			xp = step.getUserXPath();

		WebElement we = null;

		// Log.i("123","xpath:"+step.XPath());
		if (step.getAdvanced() != null
				&& step.getAdvanced().indexOf("swipFind") != -1)// 后期再区分高级配置
		{

			if (solo.waitForWebElement(By.xpath(xp), solo)) {

				we = solo.getWebElement(By.xpath(xp), step.getIndex() - 1);

				// 把对象滑动到中间位置
				DisplayMetrics dm = new DisplayMetrics();
				solo.getCurrentActivity().getWindowManager()
						.getDefaultDisplay().getMetrics(dm);

				int wide = dm.widthPixels;
				int height = dm.heightPixels;

				int heightRag = height / 8;

				if (we.getLocationY() > height / 2 + heightRag
						|| we.getLocationY() < height / 2 - heightRag) {

					int rag = height / 2 - we.getLocationY();
					// Log.i("123",
					// "XY:"+we.getLocationX()+","+we.getLocationY());

					// Log.i("123",
					// "移动:"+wide/2+","+height/2+"  "+wide/2+","+(height/2+rag));
					solo.drag(wide / 2, wide / 2, height/2, height / 2 + rag,
							40);
					we = solo.getWebElement(By.xpath(xp), step.getIndex() - 1);
				}

			}

		} else {

			// Log.i("123", "Timeout:"+Timeout.getLargeTimeout()) ;
			if (solo.waitForWebElement(By.xpath(xp))) {

				we = solo.getWebElement(By.xpath(xp), step.getIndex() - 1);

			}

		}

		// cancelRobotiumWebClient();

		return we;

	}

	/*
	 * 查找input 等 value值不能用xpath描述的对象
	 */
	public static WebElement findWebElement_ADV(TestStep step, int timeout) {

		String xp = step.XPath();
		// Log.i("123", "input:"+xp);

		final long endTime = SystemClock.uptimeMillis() + timeout;
		while (true) {

			final boolean timedOut = SystemClock.uptimeMillis() > endTime;

			if (timedOut) {
				return null;
			}
			solo.sleep(1000);

			ArrayList<WebElement> webElementToReturn = solo.getWebElements(By
					.xpath(xp));

			for (WebElement wer : webElementToReturn) {
				// Log.i("123", "wer:"+wer.getText());
				if (wer.getText().equals(step.getTextContent()))
					return wer;
			}

		}
	}

	public static Boolean unfindWebElement(TestStep step) {
		long endTime = SystemClock.uptimeMillis() + 20000;// 查找时间
		String xp = step.XPath();
		if (step.getUserXPath() != null)
			xp = step.getUserXPath();

		do {
			if (!solo.waitForWebElement(By.xpath(xp), solo, 2000))
				return true;
			solo.sleep(1000);
		} while (SystemClock.uptimeMillis() < endTime);

		return false;

	}

	// 转换时间类型
	public static String getTrueString(String str) {
		String trueString = str;

		// 匹配date
		Pattern pat = Pattern.compile("'date,[^']*'");
		Matcher mat = pat.matcher(trueString);

		while (mat.find()) {

			int first = mat.group(0).indexOf(',');
			int second = mat.group(0).lastIndexOf('\'');
			String formatStr = mat.group(0).substring(first + 1, second);

			SimpleDateFormat formater = new SimpleDateFormat(formatStr);

			trueString = trueString.replaceAll(mat.group(0),
					formater.format(new Date()));

		}

		return trueString;
	}

	public static TestStep getTS(String className, Element el)
			throws XPathExpressionException {
		TestStep ts;
		try {
			Class<?> cl = Class.forName("com.WebControl.control." + className);
			// Method method = cl.getDeclaredMethod("print", new
			// Class[]{String.class});
			Constructor<?> constructor = cl
					.getDeclaredConstructor(new Class[] { Element.class });
			ts = (TestStep) constructor.newInstance(new Object[] { el });

			// method.invoke(object, new Object[]{"zhouxianli"});
		} catch (Exception e) {
			// TODO: handle exception
			ts = new TestStep(el);
		}

		return ts;
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	// 得到控件数字ID
	public static int getId(String id) {
		Activity a = solo.getCurrentActivity();
		int viewId = a.getResources().getIdentifier(id, "id",
				a.getPackageName());
		return viewId;
	}

	// 筛选控件
	public static ArrayList<View> screenView(TestStep step) {
		ArrayList<View> viewList = new ArrayList<View>();
		ArrayList<View> tagList = new ArrayList<View>();
		ArrayList<View> idList = new ArrayList<View>();
		ArrayList<View> textList = new ArrayList<View>();

		viewList = solo.getViews();
		if (step.getTextContent() != null) {
			View view = null;
			for (int i = 1; i <= step.getIndex(); i++) {
				if(step.getAdvanced()!=null){
					if(step.getAdvanced().equals("neglect")){
						view = solo.getTextView(step.getTextContent(), i, false, 500);
					}
				}else{
					view = solo.getTextView(step.getTextContent(), i);
				}
				
				
				
				if (view != null) {
					textList.add(view);

				}

			}
		} else {
			textList = viewList;
		}

		if (step.getId() != null) {
			for (View view : textList) {
				if (TestHelper.getId(step.getId()) == view.getId()) {
					idList.add(view);
				}
			}
		} else {
			idList = textList;
		}

		if (step.getTagName() != null) {
			String tagName = step.getTagName().toLowerCase();
			for (View view : idList) {
				if (("class android.widget." + tagName).equals(view.getClass()
						.toString().toLowerCase())) {
					tagList.add(view);
				}
			}
		} else {
			tagList = idList;
		}

		return tagList;
	}

	// 循环找控件
	public static View screen(TestStep step) {
		ArrayList<View> tagList = new ArrayList<View>();
		Config config = solo.getConfig();
		long endTime = SystemClock.uptimeMillis() + config.timeout_large;
		do {
			tagList = TestHelper.screenView(step);
			if (tagList.size() != 0)
				break;
		} while (SystemClock.uptimeMillis() < endTime);
		return tagList.get(step.getIndex() - 1);
	}

	// 获取被测应用的UID;
	public static int appinfo(Activity mActivity) {
		int uid = 0;
		ActivityManager activityManager = (ActivityManager) mActivity
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {
			if (appProcess.processName.equals(Test.packageName)) {
				uid = appProcess.uid;
			}
		}
		return uid;
	}

	public static void startFlow(Activity act) {
		if (startUpFlow == 0) {
			if (uid == 0)
				uid = appinfo(act);

			startUpFlow = TrafficStats.getUidRxBytes(uid);

			startDownFlow = TrafficStats.getUidTxBytes(uid);
		}
	}

	public static void endFlow() {
		if (endUpFlow == 0) {
			endUpFlow = TrafficStats.getUidRxBytes(uid);

			endDownFlow = TrafficStats.getUidTxBytes(uid);
		}
	}

	// Http发送服务
	public static String sendPost(String url, String param, String sessionToken) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);

			URLConnection conn = realUrl.openConnection();

			// Log.i("123", "URLConnection");
			conn.setRequestProperty("sessionToken", sessionToken);

			conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
			conn.setRequestProperty("Cache-Control", "no-cache");
			/*
			 * conn.setRequestProperty("User-Agent",
			 * "Mozilla/4.0 (compatible; MSIE 4.0; Windows NT)");
			 */

			conn.setRequestProperty("connection", "Keep-Alive");

			conn.setRequestProperty("contentType",
					"application/json; charset=utf-8");
			conn.setRequestProperty("Accept", "application/json");

			conn.setDoOutput(true);
			conn.setDoInput(true);
			// Log.i("123", "setDoInput");
			out = new PrintWriter(conn.getOutputStream());

			out.print(param);

			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}

		return result;
	}




	
}

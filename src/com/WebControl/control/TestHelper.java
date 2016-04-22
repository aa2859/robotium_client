package com.WebControl.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.app.Activity;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;
import com.robotium.solo.WebElement;

public class TestHelper {
	public static Solo solo;

	/*
	 * 查找WebElement
	 */
	public static WebElement findWebElement(TestStep step) {

		WebElement we = null;
		int waittime = 20000;
		int tm = solo.getConfig().timeout_large/waittime+1;
		
		
		// Log.i("123","xpath:"+step.XPath());
		for (int i = 0; i < tm; i++) {
			
			we = findWebElement(step,waittime);
			if (we == null) {
				if (solo.waitForText("请求超时", 1, 0) || solo.waitForText("系统错误", 1, 0)) {
					
					View view = solo.getTextView("确定", 1);
					
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					String date = formatter.format(new Date());
					solo.takeScreenshot("_" + date + "warning");
					solo.clickOnView(view);

					
					throw new IllegalArgumentException("overtime");
			}

				
					
				
			} else {
				return we;
			}
		}
		return we;
	}

	public static WebElement findWebElement(TestStep step,int timeout)
	{
		
		final boolean isInput = "input".equals(step.getTagName()) || "textarea".equals(step.getTagName());
		
		if(isInput && step.getUserXPath()==null && step.getTextContent()!=null)
			{
				//Log.i("123", "input");
				return findWebElement_ADV(step,timeout);
			}
		
		
		WebElement we = null;

		String xp = step.XPath();
		if (step.getUserXPath() != null)
			xp = step.getUserXPath();
		
		
		if (step.getAdvanced() != null
				&& step.getAdvanced().indexOf("swipFind") != -1) {// 后期再区分高级配置

			if (solo.waitForWebElement(By.xpath(xp), solo, timeout)) {
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
					
					solo.drag(wide / 2, wide / 2, height / 2, height / 2
							+ rag, 40);
					we = solo.getWebElement(By.xpath(xp),
							step.getIndex() - 1);
				}

			}

		} else {

			if (solo.waitForWebElement(By.xpath(xp), timeout, false)) {

				we = solo.getWebElement(By.xpath(xp), step.getIndex() - 1);
			}

		}
		
		return we;
		
	}
	
	
	public static WebElement findWebElement_ADV(TestStep step,int timeout)
	{
		
		String xp =  step.XPath();
		//Log.i("123", "input:"+xp);
		
		final long endTime = SystemClock.uptimeMillis() + timeout;
		while (true) {	

			final boolean timedOut = SystemClock.uptimeMillis() > endTime;

			if (timedOut){
				return null;
			}
			solo.sleep(1000);
			
			ArrayList<WebElement> webElementToReturn = solo.getWebElements(By.xpath(xp)); 

			for(WebElement wer:webElementToReturn)
			{
				//Log.i("123", "wer:"+wer.getText());
				if(wer.getText().equals(step.getTextContent()))
					return wer;
			}

			
		}
	}
	
	
	
	public static Boolean unfindWebElement(TestStep step)
	{
		long endTime = SystemClock.uptimeMillis() +20000;//查找时间
		String xp =  step.XPath() ;
		if(step.getUserXPath()!=null)
			xp = step.getUserXPath();
		
		
		
		do {
			if(!solo.waitForWebElement(By.xpath(xp), solo, 2000))
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

			for (int i = 1; i <= step.getIndex(); i++) {
				View view = solo.getTextView(step.getTextContent(), i);

				String tvText = ((TextView) view).getText().toString();
				// Log.i("123", "text1:"+tvText);

				// Log.i("123", "text2:"+step.getTextContent());

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

}

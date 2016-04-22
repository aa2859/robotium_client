package com.WebControl.control;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.util.DisplayMetrics;
import android.util.Log;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_SwipBankStep extends TestStep {
	public R_SwipBankStep(Element step) throws XPathExpressionException {
		super(step);
		// this.setTagName("select");
	}

	@Override
	public void Excut(Solo solo) {
		// TODO Auto-generated method stub

		// 第一排选择框
		String xp = "//ul[@class='scroller']";
		// "//ul[@class='scroller']/li[@style]"
		if (solo.waitForWebElement(By.xpath(xp))) {

			int myWebX = 0;
			int myWebY = 0;
			List<WebElement> webList = solo.getWebElements(By
					.xpath("//ul[@class='scroller']/li"));
			for (WebElement webElement : webList) {
			
				if (webElement.getText() != null
						&& webElement.getText().equals(this.getTextContent())) {
					myWebX = webElement.getLocationX();
					myWebY = webElement.getLocationY();
					break;
				}
			}
			if (myWebX == 0 && myWebY == 0) {
				throw new IllegalArgumentException("NoControlIsFound");
			}

			xp = "//ul[@class='scroller']/li[@style]";
			WebElement web = solo.getWebElement(By.xpath(xp), 0);
			int webX = web.getLocationX();
			int webY = web.getLocationY();

			int cut = myWebY - webY;
			solo.drag(webX, webX, webY, webY - cut, 120);
		} else {
			throw new IllegalArgumentException("NoControlIsFound");
		}

		// if (solo.waitForWebElement(By.xpath(xp))) {
		// // solo.getWebElements(By.xpath(xp));
		// // xp = "//ul[@class='scroller']/li";
		// // Log.i("123", solo.getWebElements(By.xpath(xp)).size()+"");
		// //
		//
		//
		//
		//
		// WebElement we = solo.getWebElement(By.xpath(xp), 0);
		// we.getLocationX();
		// we.getLocationY();
		// Log.i("123", "we.getLocationX():"+we.getLocationX());
		// Log.i("123", "we.getLocationY():"+we.getLocationY());
		//
		//
		// WebElement myWeb = TestHelper.findWebElement(this);
		// Log.i("123", myWeb.getClass()+"");
		// myWeb.getLocationX();
		// myWeb.getLocationY();
		// Log.i("123", "myWeb.getLocationX():"+myWeb.getLocationX());
		// Log.i("123", "myWeb.getLocationY():"+myWeb.getLocationY());
		//
		// int cut = myWeb.getLocationY()-we.getLocationY();
		// solo.drag(we.getLocationX(), myWeb.getLocationX(), we.getLocationY(),
		// we.getLocationY()-cut, 100);
		// } else {
		// throw new IllegalArgumentException("NoControlIsFound");
		// }

	}

}

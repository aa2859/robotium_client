package com.WebControl.control;



import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;




import android.os.SystemClock;
import android.util.DisplayMetrics;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

//车E保专用
public class R_CancelInsuranceList extends TestStep {
	
	
	public R_CancelInsuranceList(Element step) throws XPathExpressionException
	{
		super(step);
	}
	
	@Override
	public void Excut(Solo solo){
		this.screenShot(solo);
		if(!solo.waitForWebElement(By.xpath("//*[@id='menuScroll']")))
			throw new IllegalArgumentException("NoControlIsFound"); 
		String xPath = "//*[@id='menuScroll']//*[@class='ui-link check']";
		
		if(!solo.waitForWebElement(By.xpath(xPath), 5,  false)&&solo.waitForWebElement(
				By.xpath("//*[@class='zktotal']/span[text()='0.00']"), 0, false))
		{
			return;
		}
		
		int[] xyLocation = new int[2];
		int height;
		
		DisplayMetrics dm = new DisplayMetrics();
		solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		height=dm.heightPixels;
		
		WebElement menuScroll = solo.getWebElement(By.xpath("//*[@id='menuScroll']"), 0);

		//we.getLocationOnScreen(xyLocation);
			xyLocation[0] = menuScroll.getLocationX();
			xyLocation[1] = menuScroll.getLocationY();
		
		
		//如果总保费不为0那就表示有险种被勾上了
		cancelClick(solo, xPath,height,xyLocation);
		long endTime;
		endTime = SystemClock.uptimeMillis() + 100000;
		while(!solo.waitForWebElement(By.xpath("//*[@class='zktotal']/span[text()='0.00']"), 0, false))
		{	
			final boolean timedOut = SystemClock.uptimeMillis() > endTime;
			if (timedOut){
				throw new IllegalArgumentException("error"); 
			}
			solo.clickOnScreen(xyLocation[0], xyLocation[1]);
			solo.drag(xyLocation[0], xyLocation[0], xyLocation[1], 20, 40,1000);
			cancelClick(solo, xPath,height,xyLocation);
		}
		
		endTime = SystemClock.uptimeMillis() + 100000;
		while(!solo.waitForWebElement(	By.xpath("//span[text()='交强险']"), 0, false))
		{
			final boolean timedOut = SystemClock.uptimeMillis() > endTime;
			if (timedOut){
				throw new IllegalArgumentException("error"); 
			}
			solo.clickOnScreen(xyLocation[0], xyLocation[1]-100);
			solo.sleep(500);
			solo.drag(xyLocation[0], xyLocation[0], xyLocation[1]-100, height-20, 20,1000);
		}
		solo.clickOnScreen(xyLocation[0], xyLocation[1]-100);
		solo.sleep(500);
		solo.drag(xyLocation[0], xyLocation[0], xyLocation[1]-100, height-20, 20,1000);
		super.Excut(solo);
	}
	
	private void cancelClick(Solo solo,String xPath,int height,int[] xyLocation){
		final long endTime = SystemClock.uptimeMillis() + 100000;
		while(solo.waitForWebElement(By.xpath(xPath), 5, false)){
			final boolean timedOut = SystemClock.uptimeMillis() > endTime;
			if (timedOut){
				throw new IllegalArgumentException("error"); 
			}
			
			WebElement we = solo.getWebElement(By.xpath(xPath), 0);
			if(we.getLocationY()>(height*0.75)){
				solo.drag(xyLocation[0], xyLocation[0], xyLocation[1], xyLocation[0]-100, 40);	
				continue;
			}
			
			solo.clickOnWebElement(we);
		}
	}
	
	
}

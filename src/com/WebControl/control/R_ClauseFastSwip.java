package com.WebControl.control;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.util.DisplayMetrics;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_ClauseFastSwip extends TestStep {
	public R_ClauseFastSwip(Element step) throws XPathExpressionException
	{
		super(step);
	}
	
	@Override
	public void Excut(Solo solo){
		this.screenShot(solo);
		DisplayMetrics dm = new DisplayMetrics();
		solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm) ;
		
		int wide=dm.widthPixels;
		int height=dm.heightPixels;
		
		int x1 = wide/2;
		int y1 = height/3*2;
		int x2 = wide/2;;
		int y2 = 20;
		
		for(int i=0;i<this.getIndex();i++)
		{
			//solo.clickOnScreen(x1, y1);
			solo.drag(x1, x2, y1, y2, 20,1000);
		}
		
		
		super.Excut(solo);
	}
}

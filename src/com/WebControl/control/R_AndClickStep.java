package com.WebControl.control;




import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.View;
import com.robotium.solo.Solo;




public class R_AndClickStep extends TestStep {
	private String checkText;
	public String getCheckText() {
		if (checkText != null && checkText.trim().isEmpty())
			return null;
		return checkText;
	}
	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}
	
	
	public R_AndClickStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setCheckText((String)xpath.evaluate(
				"ParamBinding[@name='checkText']/@value", step,
				XPathConstants.STRING));
	}
	
	
	@Override
	public void Excut(Solo solo) {
		/*List<View> listAll = new ArrayList<View>();
		listAll = solo.getViews();*/
		
		if((this.getAdvanced()+"").contains("neglect") && this.getAdvanced() != null){
			ArrayList<View> viewList = new ArrayList<View>();
			
			long endTime = SystemClock.uptimeMillis() + 5000;
			do {
				
				viewList = TestHelper.screenView(this);;
				if (viewList.size() != 0)
					break;
			} while (SystemClock.uptimeMillis() < endTime);
			
			if(viewList != null){
				View view = viewList.get(this.getIndex());
				this.screenShot(solo);	
				solo.clickOnView(view);
				return;
			}
			return;
		}
		
		
		
		View view = TestHelper.screen(this);
		
		if (view == null )
			throw new IllegalArgumentException("NoControlIsFound");   
		
		this.screenShot(solo);	
		
		if(this.getAdvanced() !=null && this.getAdvanced().contains("longClick"))//后期再区分高级配置
			solo.clickLongOnView(view);
		else{
			solo.clickOnView(view);
		}
		
		if(getCheckText()!=null)
		{
			if(!solo.waitForText(getCheckText(), 1, 5000, false))
				throw new IllegalArgumentException("NoControlIsFound");  
		}
		
			
	}

	
}

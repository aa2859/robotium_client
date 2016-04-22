package com.WebControl.control;



import javax.xml.xpath.XPathExpressionException;



import org.w3c.dom.Element;

import android.util.Log;

import com.robotium.solo.By;
import com.robotium.solo.Solo;


public class R_SelectStep extends TestStep {

	public R_SelectStep(Element step) throws XPathExpressionException {
		super(step);
		//this.setTagName("select");
	}
	
	

	@Override
	public String XPath() {
		// TODO Auto-generated method stub
		StringBuffer xp = new StringBuffer();
		
		xp.append("//select");
		
		xp.append("[");
		
		if (this.getId()!=null){
			xp.append("@id='"+this.getId()+"' and ");
		}
		
		if (this.getClassName()!=null){
			xp.append("@class='"+this.getClassName()+"' and ");
		}
		
		xp.append("1=1]");
		
		
		xp.append("[position()="+this.getIndex()+"]");
		
		Log.i("123","XPath:"+xp.toString());
		return xp.toString();
	}



	@Override
	public void Excut(Solo solo) {
		if(solo.waitForWebElement(By.xpath(this.XPath())))
		{
			this.screenShot(solo);
			solo.selectSelected(By.xpath(this.XPath()),this.getTextContent());
		}else
		{
			throw new IllegalArgumentException("NoControlIsFound");  
		}
	}

	

}

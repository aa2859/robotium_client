package com.WebControl.control;


import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;


import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.util.Log;

import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_CheckStep extends TestStep {

	private String checkMode;
	public String getCheckMode() {
		return checkMode;
	}

	public void setCheckMode(String checkMode) {
		this.checkMode = checkMode;
	}
	
	
	public R_CheckStep(Element step) throws XPathExpressionException
	{
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setCheckMode((String)xpath.evaluate(
				"ParamBinding[@name='checkMode']/@value", step,
				XPathConstants.STRING));
	}
	
	
	@Override
	public void Excut(Solo solo)
	{
		if(getCheckMode().equalsIgnoreCase("notfound"))
		{
			if(!TestHelper.unfindWebElement(this))//检查失败
				throw new IllegalArgumentException("检查失败"); 
		}
		else
		{
			WebElement we = TestHelper.findWebElement(this);
			
			
			if(we==null)
				throw new IllegalArgumentException("NoControlIsFound"); 
			//super.Excut(solo);
	
//			String id = we.getId();
//			String className = we.getClassName();
//			String tagName = we.getTagName();
//			String text = we.getText();
//			String name = we.getName();
//			this.step.setAttribute("obj", "id:"+id+",text:"+text+
//					",className:"+className+",tagName:"+tagName+",name:"+name);
			
		}
		this.screenShot(solo);
	}
	

}

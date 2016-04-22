package com.WebControl.control;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;



import android.util.Log;

import com.WebControl.cpic.cmp.CommandUtil;
import com.robotium.solo.Solo;

public class R_KeyEvent extends TestStep{

	private String  key;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		if (key != null && key.trim().isEmpty())
			this.key = "-1";
		else{
			
			this.key = key;
			
		}
			
	}
	
	public R_KeyEvent(Element step) throws XPathExpressionException{
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setKey((String)xpath.evaluate(
				"ParamBinding[@name='key']/@value", step,
				XPathConstants.STRING));
	}
	
	@Override
	public void Excut(Solo solo){
		
		
		if(this.getKey()!= "-1"){
			this.screenShot(solo);
			
		
			if(this.getKey().length()>2){
				
				CommandUtil commandUtil = new CommandUtil();
				try {
					
				
					commandUtil.executeCommand("input text "+this.getKey());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//TestHelper.execCommand("input text "+this.getKey());
			}else{
				solo.sendKey(Integer.parseInt(this.key));
			}
			
			
			
		}else {
			throw new IllegalArgumentException("NoParameter"); 
		}
	}
}

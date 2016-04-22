package com.WebControl.control;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.util.Log;

import com.robotium.solo.Solo;

public class R_KeyEvent extends TestStep{

	private int  key;

	public int getKey() {
		return key;
	}

	public void setKey(String key) {
		if (key != null && key.trim().isEmpty())
			this.key = -1;
		else
			this.key = Integer.parseInt(key);
	}
	
	public R_KeyEvent(Element step) throws XPathExpressionException{
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setKey((String)xpath.evaluate(
				"ParamBinding[@name='key']/@value", step,
				XPathConstants.STRING));
	}
	
	@Override
	public void Excut(Solo solo) {
		if(this.getKey()!= -1){
			this.screenShot(solo);
			Log.i("123", "ccss"+this.getKey());
			solo.sendKey(this.getKey());
		}else {
			throw new IllegalArgumentException("NoParameter"); 
		}
	}
}

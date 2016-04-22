package com.WebControl.control;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.app.Activity;


import com.robotium.solo.Solo;

public class R_InitStep extends TestStep{
	
	public R_InitStep(Element step) throws XPathExpressionException {
		super(step);
	}

	
	@Override
	public void Excut(Solo solo) {
		
		Activity act = TestHelper.ts.getActivity();
		this.screenShot(solo);
		//流量监控
		
		TestHelper.startFlow(act);
		
		super.Excut(solo);
	}
	
}

package com.WebControl.control;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.util.Log;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_LoginStep2 extends  R_LoginStep{

	public R_LoginStep2(Element step) throws XPathExpressionException {
		super(step);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Excut(Solo solo) {

		this.screenShot(solo);
		WebElement userName = solo.getWebElement(By.id("userName"), 0);
		if(userName==null) throw new IllegalArgumentException("NoControlIsFound"); 
		
		WebElement dark_text_password =  solo.getWebElement(By.id("dark_text_password"), 0);
		if(dark_text_password==null) throw new IllegalArgumentException("NoControlIsFound");
		
		solo.enterTextInWebElement(By.id("userName"), this.getUsername());
		solo.enterTextInWebElement(By.id("dark_text_password"), this.getPassword());
		
		//密码 fan123456
		String js = "cpic.getEncrypt = function (a) { return \"DkY0ZbOH2t7vsxRY6AMq3A==\";};"
				+ "cpic.getEncryptedClientRandom = function (a) { return \"Eb3hWoNiMPKpa3O7kWnS8m/erHC99FqDZIVi027X9VW4pgKNhVwCk4+O+Sqgwf9rmv8KPFBFuu+qtG58YTWYCgU+r/ihT3stoTE4llfZbnLv2cyA+wvtVm4flSlj3cMy34KoYGfsbFLSWNU+qT4nvj3TW7P35M299Mvpha+4qw0=\";};"
				+ "cpic.checkRegex = function (a) { return true;}";
		solo.DebugJS(js);
		Log.i("123", js);
		solo.sleep(1000);
		WebElement loginBTN =   solo.getWebElement(By.xpath("//div[@class='button' and text()='登录']"), 0);
		if(loginBTN==null) throw new IllegalArgumentException("NoControlIsFound");
		
		solo.clickOnWebElement(loginBTN);
		
	
	}

	
}

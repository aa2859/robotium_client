package com.WebControl.control;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.app.Activity;



import android.util.Log;
import android.view.View;

import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;


public class R_LoginStep extends TestStep {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * 
	 * 太付钱包登录
	 */
	public R_LoginStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setUsername(((String)xpath.evaluate(
				"ParamBinding[@name='username']/@value", step,
				XPathConstants.STRING)));
		
		
		this.setPassword(((String)xpath.evaluate(
				"ParamBinding[@name='password']/@value", step,
				XPathConstants.STRING)));
	}

	@Override
	public void Excut(Solo solo) {

		
		solo.getView(View.class, 10);//等待UI加载完成
		solo.sleep(5000);
		this.screenShot(solo);
		
		//Log.i("123", "solo");
		Activity act =  solo.getCurrentActivity();
		
		try {
			Field requestHandlerF;
			
			
			requestHandlerF = act.getClass().getSuperclass().getDeclaredField("requestListHandler");
		
			requestHandlerF.setAccessible(true);
			Object requestListHandler = requestHandlerF.get(act);
			
			
			Field mContextF = act.getClass().getSuperclass().getSuperclass().getDeclaredField("mContext");
			mContextF.setAccessible(true);
			
			Object mContext = mContextF.get(act);
			
			Field requestTypeF = act.getClass().getDeclaredField("requestType");
			requestTypeF.setAccessible(true);
			requestTypeF.set(act, "login_copy");
			
			
			Class<?> NetUtils_class = Class.forName("com.cpic.pay.net.NetUtils");
			Method[] mds = NetUtils_class.getMethods();
			Method login=null;
			for(Method md :mds)
			{
				if(md.getName().equals("login_copy"))
				{
					login = md;
					break;
				}
			}
			//密码:qwe123
			/*
			login.invoke(null, mContext,this.username,"lt5yIvBZzAY=",requestHandler,
					"dFUuUQJdYwcpNCQW1xtwbOMz5WRi1oGWj9Umww+PnbPmwtfSolRY3Ie1e9VS+lnKce82/"
					+ "erya4ha0ihHOd2KAa6VvE5H8IyH86h7l/4deKCzsXJtL7dnCid0ygCFHnJWVwRbTRq0"
					+ "zvSpL2efHAONC7FlDx4MsXo6I3zUJzk3t2NjbcHLzhLSdbRq8HJbdl0iu7d4aF77LJn"
					+ "YxmaH8ZNiemSFaHDStJ+3MVPYYKJYSPkCIPp3/iGcyhCAf0TzdPl/QngJF5yCskRMYs"
					+ "CPwLg/lmbo2nUdkX8Trt89kyD421FywwWuD74vdEdSDfQSopICJlNbpRDRt+LM8XY8p"
					+ "Qm2Pg==");
					*/
			login.invoke(null, mContext,this.username,"4lXPWy77lYzSI+15GLXzxQ==",
					requestListHandler,"QSs227biCpOU/r3gbGFcUrqRV+O07XmNFPZkvAaapO2qgUeVG1poPQooHKfOppZaRVavLfEkZgA8Z4t0SCVwhso1IefyApqnj0R2oaEjb3WQT7SWhezQPpcw5KmWQVlcWP6zAYo5nTswDGzsmh/4Ik2czKCKIuRpM8KwDvzgEXSbcO5g8pRKatZzgzRi8W3MplPbJeeMPHUXSE5JRrdc0wEiemDn47+3h22d0dU9aNRf6cox6f6K3juPpFw/y3nQxuLt2+u8+eWzVxn5KRsnLn/ZG2Sw9MNsH+jqJkrC83TfJm6/fPsnXA0NNE7KtaSgj+VAVv/u5W4UjllLfYMC1A==");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage()); 
		} 
		
	
	}
	
	
	

}

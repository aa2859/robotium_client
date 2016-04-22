package com.WebControl.control;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.w3c.dom.Element;


import com.robotium.solo.Solo;



import android.app.Activity;
import android.content.Intent;


import android.telephony.TelephonyManager;


public class R_LoginMamForPad extends TestStep{
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
	
	
	public R_LoginMamForPad(Element step) throws XPathExpressionException {
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
		
		try {
			TelephonyManager telephonyManager= (TelephonyManager)TestHelper.ts.getInstrumentation().getContext().getSystemService(TestHelper.ts.getInstrumentation().getContext().TELEPHONY_SERVICE);
			String imei=telephonyManager.getDeviceId();
			String json = getHttp(imei, this.username, this.password);

			JSONObject  dataJson = new JSONObject(json);
			JSONObject  result = dataJson.getJSONObject("result");
			String sessionToken = result.getString("sessionToken");
			String sessionRandom = result.getString("sessionRandom");
			
			Intent intent = new Intent();
			intent.setClassName(Test.packageName, Test.mainActiviy);
			//intent.putExtra("encryptCode", encryptCode); 

			intent.putExtra("useid", this.username);
			intent.putExtra("sessionToken", sessionToken);
			intent.putExtra("sessionRandom", sessionRandom);
			intent.putExtra("devicesn", imei);
			intent.putExtra("businessparams", "");
			intent.putExtra("SxtbSubarea", "sxtbsit2.cpic.com.cn");
			intent.setFlags(805306368);

			Activity act = TestHelper.ts.getInstrumentation().startActivitySync(intent);
			
		
			TestHelper.startFlow(act);
			
			this.screenShot(solo);
			
			
			
			
			
		} catch (Exception e) {
			throw new IllegalArgumentException("登录失败!"); 
		}

		
	}
	
	

	
	public static String getHttp(String adress,String username,String password){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		String date = formatter.format( new Date()); 
		  
		String sessionToken = (adress+date+"ASDWERTYUIOPERTYUIOPKQWEHKLOJSADKJAKJSADKAFGHJKLFGHJADS").substring(0,31);
		//Log.i("123", sessionToken);
		String body = "{\"id\":1,\"method\":\"com.apperian.easesdk.authenticateuser\"," +
				"\"params\":{\"orgId\":\"\",\"sessionToken\":\""+sessionToken+"\"," +
				"\"clientsession\":\""+sessionToken+"\",\"email\":\""+username+"\"," +
				"\"devicesn\":\""+adress+"\",\"bundleId\":\"com.ihandy.xgx.browsersit\"," +
				"\"devicetype\":0,\"udid\":\"5C:F8:A1:39:DB:2C\",\"password\":\""+password+"\"," +
				"\"sysinfo\":{\"osname\":\"Android\",\"screen_height_px\":\"752\",\"model\":\"GT-N8000\"," +
				"\"cell_id\":\"353988051233640\",\"density\":\"medium\",\"manufacturer\":\"samsung\"," +
				"\"name\":\"p4noterf\",\"screen_width_px\":\"1280\",\"osver\":\"4.4.2\"," +
				"\"freeram\":\"1082568704\",\"sdkVersion\":\"19\"},\"packname\":\"com.ihandy.xgx.browsersit\"," +
				"\"version\":\"6.5\"},\"jsonrpc\":\"2.0\",\"!version\":1}";
		//System.out.println(body);
		
		
		String sr=TestHelper.sendPost("http://mamsit.cpic.com.cn/public-ease-sdk.interface.php",body,sessionToken );
        return sr;      
}
}

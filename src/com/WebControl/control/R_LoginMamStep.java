package com.WebControl.control;


import java.lang.reflect.Method;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.w3c.dom.Element;

import android.app.Activity;
import android.content.Intent;

import android.telephony.TelephonyManager;

import com.robotium.solo.Solo;

public class R_LoginMamStep extends TestStep{
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

	public R_LoginMamStep(Element step) throws XPathExpressionException {
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
			//Method decrypt=  Class.forName("com.innofidei.tools.InnofideiTools").getMethod("decrypt", new Class[]{String.class,String.class});
			Method encrypt=  Class.forName("com.innofidei.tools.InnofideiTools").getMethod("encrypt", new Class[]{String.class,String.class});
		
			
			TelephonyManager telephonyManager= (TelephonyManager)TestHelper.ts.getInstrumentation().getContext().getSystemService(TestHelper.ts.getInstrumentation().getContext().TELEPHONY_SERVICE);
			String imei=telephonyManager.getDeviceId();
			String json = getHttp(imei, this.getUsername(), this.getPassword());
			
			JSONObject  dataJson = new JSONObject(json);
			JSONObject  result = dataJson.getJSONObject("result");
			String sessionToken = result.getString("sessionToken");
			String sessionRandom = result.getString("sessionRandom");
			String p13info = result.getString("p13info");
			
			String encode = "{\"id\":\"1\",\"method\":\"com.apperian.easesdk.authenticateserver\",\"params\":{\"sessionRandom\":\""+sessionRandom+"\",\"fromApp\":\"com.ihandy.xgx.browser\",\"sessionToken\":\""+sessionToken+"\",\"p13info\":\""+p13info+"\",\"businessparams\":\"\",\"devicesn\":\""+imei+"\",\"userid\":\"LIJINSHENG\",\"turnToApp\":\"com.cpic.ecarsit\"},\"jsonrpc\":\"2.0\",\"!version\":\"1\"}";
		
			String encryptCode = (String) encrypt.invoke(null, encode, "CpicAppstore");
			
			Intent intent = new Intent();
			intent.setClassName(Test.packageName, Test.mainActiviy);
			intent.putExtra("encryptCode", encryptCode);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Activity act = TestHelper.ts.getInstrumentation().startActivitySync(intent);
			
			TestHelper.startFlow(act);
			this.screenShot(solo);
			//at.startActivity(intent);
			
			
		} catch (Exception e) {
			throw new IllegalArgumentException("登录失败!"); 
		}
		// TODO Auto-generated method stub
		super.Excut(solo);
	}

	
	public static String getHttp(String adress,String username,String password){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		String date = formatter.format( new Date());
		
		String sessionToken = (adress+date+"ASDWERTYUIOPERTYUIOPKQWEHKLOJSADKJAKJSADKAFGHJKLFGHJADS").substring(0,31);	
		String body = "{\"id\":1,\"method\":\"com.apperian.easesdk.mobile_authenticateuser\"," +
				"\"params\":{\"orgId\":\"\",\"sessionToken\":\""+sessionToken+"\",\"password_type\":\"\"," +
				"\"clientsession\":\""+sessionToken+"\",\"email\":\""+username+"\",\"devicesn\":\""+adress+"\"," +
				"\"bundleId\":\"com.ihandy.xgx.browser\",\"devicetype\":97,\"udid\":\"F0:25:B7:43:B3:A3\"," +
				"\"password\":\""+password+"\",\"sysinfo\":{\"osname\":\"Android\",\"screen_height_px\":\"1920\"," +
				"\"model\":\"GT-I9507V\",\"cell_id\":\""+adress+"\",\"density\":\"\",\"manufacturer\":\"samsung\"," +
				"\"name\":\"jftdd\",\"screen_width_px\":\"1080\",\"osver\":\"4.4.2\",\"freeram\":\"1082814464\"," +
				"\"sdkVersion\":\"19\"},\"packname\":\"com.ihandy.xgx.browser\",\"version\":\"3.67\"},\"jsonrpc\":\"2.0\"," +
				"\"!version\":1}";
		//System.out.println(body);
		String sr=TestHelper.sendPost("http://mamsit.cpic.com.cn/public-ease-sdk.interface.php",body,sessionToken );
        return sr;
      
        
}
	
}

package com.WebControl.control;




import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;


public class Test extends ActivityInstrumentationTestCase2 {

	

	//private String path =Environment.getExternalStorageDirectory().getAbsolutePath()+"/test";
	private String path ="/sdcard/test";

	private static Class<?> launchActivityClass;
	public static Instrumentation inst;
	
	//中太----
	//public static String mainActiviy = "com.cpic.cmp.activity.BangerActivity";
	//public static String packageName = "com.cpic.cmp";
	
	//E保通---2
	//public static String mainActiviy = "com.wondertek.cpicmobile.ui.main.MainActivity";
	//public static String packageName = "com.wondertek.cpicmobile";
	
	//车E保---4
	//com.wondertek.mist.cxxssit   SIT版
	//com.wondertek.mist.cxxssgsit SIT商改版
	//public static String packageName = "com.wondertek.mist.cxxssgsit";
	//public static String mainActiviy = "com.wondertek.mist.StartActivity";
	
	//第三方支付----3
	//public static String mainActiviy = "com.cpic.pay.ui.activity.LoginActivity";
	//public static String packageName = "com.cpic.pay";
	
	//记事本----5
	//public static String packageName = "com.cpic.mobilenote";
	//public static String mainActiviy = "com.cpic.view.SplashActivity";
	
	
	
	//2-4----6
	//public static String packageName = "com.cpic.cmp2";
	//public static String mainActiviy = "com.cpic.cmp2.views.MainActivity";
	
	
	//中太企业版----7
	//public static String packageName = "com.cpic.appstore";
	//public static String mainActiviy = "com.apperian.ease.appcatalog.ActivityCpicSplash";
	
	
	
	//车E保手机----8
	//public static String packageName = "com.cpic.ecarsit";
	//public static String mainActiviy = "com.cpic.ecar.app.activity.LoadingActivity";
	
	//稳健人生----10
	//public static String packageName = "com.crm_i13sit";
	//public static String mainActiviy = "com.crm_i13uat.WebActivity";
			
	
	//即时通----9
	//public static String packageName = "com.cpic.jst";
	//public static String mainActiviy = "com.cpic.jst.ui.activity.LaunchActivity";
	
	//手机版MAM入口----11
										  
	//public static String packageName = "com.ihandy.xgx.browser";
	//public static String mainActiviy = "com.apperian.ease.appcatalog.SplashActivity";
										
	//手机MAM入口SIT环境 ----11
	public static String packageName = "com.ihandy.xgx.browsersit";
	public static String mainActiviy = "com.apperian.ease.appcatalog.SplashActivity";
	
	//太平洋保险箱SIT ----12
	//public static String packageName = "com.cpic.sxbxxesit";
	//public static String mainActiviy = "com.cpic.sxbxxe.ui.main.MainActivity";
	
	static {
		try {
			launchActivityClass = Class.forName(mainActiviy);			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Test() {
		super(packageName, launchActivityClass);
	
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.i("123","打开程序");
		
		Config config=new Config();
		config.screenshotSavePath=path;
		config.timeout_small=60000;
		config.timeout_large=60000;
		inst = getInstrumentation();
		TestHelper.solo=new Solo(getInstrumentation(),config);
		//solo.sleep(9000);
		
		TestHelper.ts = this;
		//mActivity=getActivity();
		
	}



	@Override
	protected void tearDown() throws Exception {
		Log.i("123","关闭程序");
		//sendOrderBroadCast();	
		TestHelper.solo.finishOpenedActivities();
		super.tearDown();
	}

	/*
	 * 离线版发送广播	
	 * private void sendOrderBroadCast() {
		Intent intent=new Intent("com.WebControl.receiver");//清单文件中配置的
		intent.putExtra("msg", "true");
		mActivity.getApplicationContext().sendBroadcast(intent);//receiverPermission:是自定义个权限
	}
*/
	TestCase case1;
	public void testCase() throws Exception {
		case1 = new TestCase(path+"/test.xml");
		Log.i("123","案例步骤:"+case1.getStepList().size());
		//solo.takeScreenshot("start");
		case1.run(TestHelper.solo);
	}
}

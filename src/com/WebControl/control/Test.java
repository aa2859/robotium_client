package com.WebControl.control;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;

public class Test extends ActivityInstrumentationTestCase2 {

	private Solo solo;
	private Activity mActivity;
	//private String path =Environment.getExternalStorageDirectory().getAbsolutePath()+"/test";
	private String path ="/sdcard/test";

	private static Class<?> launchActivityClass;
	//private static String mainActiviy = "com.cpic.cmp.activity.BangerActivity";
	//private static String packageName = "com.cpic.cmp";
	//E保通
	//private static String mainActiviy = "com.wondertek.cpicmobile.IndexActivity";
	//private static String packageName = "com.wondertek.cpicmobile";
	
	//车E保
	private static String packageName = "com.wondertek.mist.cxxssit";
	private static String mainActiviy = "com.wondertek.mist.StartActivity";
	
	//第三方支付
	//private static String mainActiviy = "com.cpic.pay.ui.activity.LoginActivity";
	//private static String packageName = "com.cpic.pay";
	
	//记事本
	//private static String packageName = "com.cpic.mobilenote";
	//private static String mainActiviy = "com.cpic.view.SplashActivity";
	

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
		
		
		/*Log.i("123", "pm clear "+packageName);
		Process proc = Runtime.getRuntime().exec("pm clear "+packageName);
		proc.waitFor();*/
		//这么挫的删除缓存准备什么时候改?
		/*
		deleteFile(new File("/data/data/com.cpic.cmp/app_database"));
		deleteFile(new File("/data/data/com.cpic.cmp/app_push_dex"));
		deleteFile(new File("/data/data/com.cpic.cmp/app_push_lib"));
		deleteFile(new File("/data/data/com.cpic.cmp/app_push_update"));
		deleteFile(new File("/data/data/com.cpic.cmp/cache"));
		deleteFile(new File("/data/data/com.cpic.cmp/databases"));
		deleteFile(new File("/data/data/com.cpic.cmp/files"));
		deleteFile(new File("/data/data/com.cpic.cmp/shared_prefs"));
		*/
		Log.i("123","打开程序");
		
		Config config=new Config();
		config.screenshotSavePath=path;
		config.timeout_small=120000;
		config.timeout_large=120000;
		
		this.solo=new Solo(getInstrumentation(),config);
		TestHelper.solo = this.solo;
		mActivity=getActivity();
	
	}
	
	private int appinfo(){
		int uid = 0;
		ActivityManager activityManager = (ActivityManager) mActivity.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		for(ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()){
			if(appProcess.processName.equals(packageName)){
				uid = appProcess.uid;
			}
		}
		return uid;
	}

	@Override
	protected void tearDown() throws Exception {
		Log.i("123","关闭程序");
		//sendOrderBroadCast();	
		this.solo.finishOpenedActivities();

		super.tearDown();
	}
	
	private void sendOrderBroadCast() {
		
		Intent intent=new Intent("com.WebControl.receiver");//清单文件中配置的
		intent.putExtra("msg", "true");
		mActivity.getApplicationContext().sendBroadcast(intent);//receiverPermission:是自定义个权限
		
	}

	TestCase case1;
	public void testCase() throws Exception {
		solo.getCurrentActivity();
		
		//流量监控
		int  uid = appinfo();
		long rx1 = TrafficStats.getUidRxBytes(uid);
		long tx1 = TrafficStats.getUidTxBytes(uid);
		
		//solo.sleep(2000);
		case1 = new TestCase(path+"/test.xml");
		Log.i("123","案例步骤:"+case1.getStepList().size());
		solo.takeScreenshot("start");
		case1.run(solo);
		
		//流量计算
		long rx2 = TrafficStats.getUidRxBytes(uid);
		long tx2 = TrafficStats.getUidTxBytes(uid);
		
		long rx = (rx2-rx1)/1024;
		long tx = (tx2-tx1)/1024;
		
		case1.caseElement.setAttribute("flow", "接受流量:"+rx+"KB"+",发送流量:"+tx+"KB");	
		case1.creatXML();
	
	}
	

	//删除文件
		private void deleteFile(File file) {
			if (file.exists()) { // 判断文件是否存在
				if (file.isFile()) { // 判断是否是文件
					file.delete(); // delete()方法 你应该知道 是删除的意思;
				} else if (file.isDirectory()) { // 否则如果它是一个目录
					File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
			} else {
				//Constants.Logdada("文件不存在！" + "\n");
			}
		}

}

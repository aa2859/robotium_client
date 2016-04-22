package com.WebControl.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.net.TrafficStats;
import android.util.Log;
import com.robotium.solo.Solo;


public class TestCase {
	private String caseID;
	private String caseName;//案例名
	private String resultFilePath;
	private List<TestStep> stepList = new ArrayList<TestStep>();
	private Document testDocument;
	public Element caseElement;
	public String getCaseID() {
		return caseID;
	}

	public String getCaseName() {
		return caseName;
	}

	public List<TestStep> getStepList() {
		return stepList;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public void setStepList(List<TestStep> stepList) {
		this.stepList = stepList;
	}

	public TestCase(String Path) {
		try {
			Init(Path);
		} catch (Exception e) {
			Log.i("123","test初始化错误:"+e.getMessage());
		}
	}

	public TestCase() {
		
	}

	/*
	 * xml解析
	 */
	public void Init(String path) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		File caseFile = new File(path);
		resultFilePath = caseFile.getParent()+"/test_result.xml";
		//Log.i("123", "resultFilePath:"+resultFilePath);
		File resultFile  = new File(resultFilePath);
		resultFile.delete();
		TestHelper.copyFile(caseFile,resultFile);
		
		testDocument = db.parse(caseFile);
		

		/*
		 * 解析XML全过程
		 */
		caseElement = testDocument.getDocumentElement();
		
		this.setCaseName(caseElement.getAttribute("name"));
		this.setCaseID(caseElement.getAttribute("customno"));
		NodeList list = caseElement.getElementsByTagName("Step");
		
		//Log.i("123", "step:"+list.getLength());
		for (int i = 0; i < list.getLength(); i++) {
			Element step = (Element) list.item(i);
			String name = step.getAttribute("name");
			
			TestStep ts = TestHelper.getTS(name, step);
			
			
			
			stepList.add(ts);
			
		}
		
		
	}
	
	public void run(Solo solo)
	{
		
		for (int i=0;i<this.getStepList().size();i++) {
			TestStep step = getStepList().get(i);
			step.screenName = i+"";
			try {
				step.runDate = new Date();
				step.Excut(solo);
				solo.sleep(step.getWaitTime());
				step.setResultStatic("1");
				step.setResultMsg("执行成功"); 
			}catch (Exception e) {
				
				if(e.getClass()==java.lang.NullPointerException.class || e.getMessage().equals("NoControlIsFound")){
					step.setResultStatic("2");
					step.setResultMsg("找不到控件  执行不成功");
				}else if(e.getMessage().equals("NoParameter")){
					step.setResultStatic("2");
					step.setResultMsg("无参数  执行不成功");
				}else{
					step.setResultStatic("3");
					step.setResultMsg(e.getMessage());
				}
				try
				{
					solo.takeScreenshot("fail");
				}catch(Exception e1){}
			}
			
			step.inputElement();
			this.creatXML();
			Log.i("123","第"+i+"步:"+ step.getDesc() +"执行结果："+step.getResultStatic()+step.getResultMsg());
			/*try {
				solo.takeScreenshot(i+"");
			}catch (Exception e){ 
				Log.i("123","截图失败");
			}*/
			
			if(!step.getResultStatic().equals("1"))
			{
				Log.i("123","执行失败");
				break;
			}	
		} 
		TestHelper.endFlow();
		
		caseElement.setAttribute("flow", "接收流量:"+
		(TestHelper.endUpFlow-TestHelper.startUpFlow)/1024+"KB"+
				",发送流量:"+(TestHelper.endDownFlow-TestHelper.startDownFlow)/1024+"KB");	
		creatXML();
		
	}
	
	public void creatXML()
	{
		
		//Log.i("123", "creatXML");
        try {
        	
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
           
            DOMSource source = new DOMSource(testDocument);
           
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(resultFilePath));
            StreamResult result = new StreamResult(pw);
           
            transformer.transform(source, result);
            
        } catch (Exception e) { 
        	Log.i("123","生成XML文件失败:"+e.getMessage()); 
        	e.printStackTrace();
        } 
		
	}

	
}

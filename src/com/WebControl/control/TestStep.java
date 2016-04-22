package com.WebControl.control;

import java.text.SimpleDateFormat;
import java.util.Date;











import javax.xml.xpath.XPathExpressionException;





import org.w3c.dom.Element;





import org.w3c.dom.NodeList;

import android.util.Log;

import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class TestStep {

	public Date runDate;

	

	

	private String Desc;// 步骤描述

	public String getDesc() {
		if (Desc != null && Desc.trim().isEmpty())
			return null;
		return Desc;
	}

	public void setDesc(String Desc) {
		this.Desc = Desc;
	}

	private String id;// 控件ID

	public String getId() {
		if (id != null && id.trim().isEmpty())
			return null;
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String tagName;// 标签

	public String getTagName() {
		if (tagName != null && tagName.trim().isEmpty())
			return null;
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	private String className;// class名字

	public String getClassName() {
		if (className != null && className.trim().isEmpty())
			return null;
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private String textContent;//click为查找text标识

	public String getTextContent() {
		if (textContent != null && textContent.trim().isEmpty())
			return null;
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	protected int index;// 位置

	public int getIndex() {
		if (index <= 0) {
			return 1;
		}
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private String waitTime;// 等待时间

	public int getWaitTime() {
		if (waitTime == null || waitTime.trim().isEmpty()) {
			return 100;
		}
		return (int) (Float.parseFloat(waitTime)*1000);
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	private String ResultStatic = "0";// 执行状态 0未执行 1执行成功 2执行失败 3报错

	public String getResultStatic() {
		return ResultStatic;
	}

	public void setResultStatic(String resultStatic) {
		ResultStatic = resultStatic;
	}

	private String ResultMsg;// 执行结果

	public String getResultMsg() {
		return ResultMsg;
	}

	public void setResultMsg(String resultMsg) {
		ResultMsg = resultMsg;
	}

	private String userXPath;

	public String getUserXPath() {
		//Log.i("123","userXPath:"+userXPath);
		if (userXPath != null && userXPath.trim().isEmpty())
			return null;
		return userXPath;
	}

	public void setUserXPath(String userXPath) {
		this.userXPath = userXPath;
	}
	
	private String Advanced;
	

	public String getAdvanced() {
		if (Advanced != null && Advanced.trim().isEmpty())
			return null;
		return Advanced;
	}

	public void setAdvanced(String advanced) {
		Advanced = advanced;
	}



	public String XPath() {
		StringBuffer xp = new StringBuffer();
		if (this.getTagName() == null) {
			xp.append("//*");
		} else {
			xp.append("//" + this.getTagName());
		}
		

		if (this.getId() != null) {
			xp.append("[@id='" + this.getId() + "']");
		}

		if (this.getClassName() != null) {
			xp.append("[@class='" + this.getClassName() + "']");
		}

		final boolean isInput = "input".equals(this.getTagName()) || "textarea".equals(this.getTagName());
		//Log.i("123", "TagName:"+this.getTagName());
		
		//Log.i("123", "isInput:"+isInput);
		if (this.getTextContent() != null && !isInput)
		{
			if(this.getTextContent().contains("|like"))
			{
				xp.append("[contains(text(),'" + this.getTextContent().split("\\|")[0] + "')]");
			}else
				xp.append("[text()='" + this.getTextContent() + "']");
		}

		return xp.toString();

	}
	
	public String screenName;
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Element step;

	public TestStep() {

	}
/*
	public TestStep(Element step)  throws XPathExpressionException {

		this.step = step;
		XPath xpath = XPathFactory.newInstance().newXPath();

		this.setDesc((String) xpath.evaluate("@desc", step,
				XPathConstants.STRING));

		this.setOrder((String) xpath.evaluate("@order", step,
				XPathConstants.STRING));

		this.setId((String) xpath.evaluate("ParamBinding[@name='id']/@value",
				step, XPathConstants.STRING));

		this.setTagName((String) xpath.evaluate(
				"ParamBinding[@name='tagName']/@value", step,
				XPathConstants.STRING));

		this.setClassName((String) xpath.evaluate(
				"ParamBinding[@name='className']/@value", step,
				XPathConstants.STRING));

		this.setTextContent((String) xpath.evaluate(
				"ParamBinding[@name='textContent']/@value", step,
				XPathConstants.STRING));

		String ind = (String) xpath.evaluate(
				"ParamBinding[@name='index']/@value", step,
				XPathConstants.STRING);
		if (ind != null && !ind.trim().isEmpty())
			this.setIndex(Integer.parseInt(ind));

		this.setUserXPath((String) xpath.evaluate(
				"ParamBinding[@name='userXPath']/@value", step,
				XPathConstants.STRING));

		this.setWaitTime((String) xpath.evaluate(
				"ParamBinding[@name='waitTime']/@value", step,
				XPathConstants.STRING));
		
		this.setAdvanced((String) xpath.evaluate(
				"ParamBinding[@name='Advanced']/@value", step,
				XPathConstants.STRING));

	}
	*/
	
	public TestStep(Element step) throws XPathExpressionException {

		this.step = step;
		this.setDesc(step.getAttribute("desc"));
		NodeList list = step.getElementsByTagName("ParamBinding");
		for (int i = 0; i < list.getLength(); i++) {
			Element el = (Element) list.item(i);
			switch (el.getAttribute("name").toString()) {
			
			
			case "id":
				this.setId(el.getAttribute("value"));
				break;
			case "tagName":
				this.setTagName(el.getAttribute("value"));
				break;
			case "className":
				this.setClassName(el.getAttribute("value"));
				break;
			case "textContent":
				this.setTextContent(el.getAttribute("value"));
				break;
			case "index":
				String ind = el.getAttribute("value");
				if (ind != null && !ind.trim().isEmpty())
					this.setIndex(Integer.parseInt(ind));
				break;
			case "userXPath":
				this.setUserXPath(el.getAttribute("value"));
				break;
			case "waitTime":
				this.setWaitTime(el.getAttribute("value"));
				break;
			case "Advanced":
				this.setAdvanced(el.getAttribute("value"));
				break;
			default:
				break;
			}
		}
	}

	

	public void Excut(Solo solo) {
		
	}

	public void inputElement()  {

		this.step.setAttribute("ResultStatic", this.getResultStatic());
		this.step.setAttribute("ResultMsg", this.getResultMsg());
		
		

	}
	public void screenShot(Solo solo){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = formatter.format( new Date());
		try {
			solo.takeScreenshot(screenName+"_"+date);
			this.step.setAttribute("Photo", screenName+"_"+date+".jpg");
		} catch (Exception e) {
			Log.i("123", "截图失败");
		}
	}

	public void clickOnWebElement(WebElement we)
	{
		
		if(getAdvanced()==null)
		{
			TestHelper.solo.clickOnWebElement(we);
			return;
		}
		
		if(getAdvanced().indexOf("clickRight")>=0)
		{
			int width = we.getLowerRightCornerX()-we.getUpperLeftCornerX();
			int clickX = (int)(we.getUpperLeftCornerX()+width*0.8);
			int clickY = we.getLocationY();
			TestHelper.solo.clickOnScreen(clickX, clickY);
		}else if(getAdvanced().indexOf("clickLeft")>=0)
		{
			int width = we.getLowerRightCornerX()-we.getUpperLeftCornerX();
			int clickX = (int)(we.getUpperLeftCornerX()+width*0.2);
			int clickY = we.getLocationY();
			TestHelper.solo.clickOnScreen(clickX, clickY);
		}else
		{
			TestHelper.solo.clickOnWebElement(we);
		}
	}
	
}

package com.WebControl.control;



import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.util.DisplayMetrics;
import android.util.Log;

import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_SwipStep extends TestStep {
	
	private String Direction;// 方向
	public String getDirection() {
		if (Direction == null || Direction.trim().isEmpty()) {
			return "up";
		}
		return Direction.trim();
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	
	private String Point;// 点坐标
	public String getPoint() {
		return Point;
	}
	public void setPoint(String point) {
		Point = point;
	}
	
	public R_SwipStep(Element step) throws XPathExpressionException
	{
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setDirection((String)xpath.evaluate(
				"ParamBinding[@name='Direction']/@value", step,
				XPathConstants.STRING));
		
		
		this.setPoint((String)xpath.evaluate(
				"ParamBinding[@name='Point']/@value", step,
				XPathConstants.STRING));
		
		
	}

	@Override
	public void Excut(Solo solo) {
		DisplayMetrics dm = new DisplayMetrics();
		solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm) ;
		//this.screenShot(solo);
		int wide=dm.widthPixels;
		int height=dm.heightPixels;
		
		float fromX = 0;
		float fromY = 0;
		float toX = 0;
		float toY = 0;
		int count = 20;
		String[] arrayPoint = getPoint().split(",");
		if(getPoint()!=null && !getPoint().trim().isEmpty())
		{	
			fromX = getPoint(wide,arrayPoint[0]);
			fromY = getPoint(height,arrayPoint[1]);
			toX = getPoint(wide,arrayPoint[2]);
			toY = getPoint(height,arrayPoint[3]);
			if(arrayPoint.length == 5){
				count = Integer.parseInt(arrayPoint[4]);
			}
		}else if(this.getId()!=null || this.getTagName()!=null || 
				this.getClassName()!=null || this.getTextContent()!=null || this.getUserXPath()!=null || this.index!=0){
			WebElement view = TestHelper.findWebElement(this);
			
			
			fromX = view.getLocationX();
			fromY = view.getLocationY();
			switch (this.getDirection()) {
			case "right":
				toX = wide-10;
				toY = fromY;
				break;
			case "left":
				toX = 10;
				toY = fromY;
				break;
			case "up":
				toX = fromX;
				toY = 10;
				break;
			case "down":
				toX = fromX;
				toY = height-10;
				break;
			case "Oblique":
				toX = fromX+100;
				toY = fromY+100;
				break;
			default:
				break;
			}
		}else{
			switch (this.getDirection()) {
			case "right":
				fromX = 10;
				fromY = height/2;
				toX = wide-10;
				toY = height/2;
				break;
			case "left":
				fromX = wide-10;
				fromY = height/2;
				toX = 10;
				toY = height/2;
				break;
			case "up":
				fromX = wide/2;
				fromY = height/2;
				toX = wide/2;
				toY = 10;
				break;
			case "down":
				fromX = wide/2;
				fromY = height/2;
				toX = wide/2;
				toY = height-10;
				break;
			case "Oblique":
				fromX = 300;
				fromY = 300;
				toX = 400;
				toY = 400;
				break;
			default:
				break;
			}
		}
			Log.i("123", "我要滑了:"+fromX+"_____"+fromY);
			solo.drag(fromX, toX, fromY, toY, count);
			Log.i("123", "我滑好了:"+toX+"_____"+toY);
	}

	
	
	public float getPoint(int length,String point){
		if(point.contains("%")){
			 return length*((Float.parseFloat(point.replace("%", "").trim()))/100);
		}else{
			return Float.parseFloat(point.trim());
		}
	}
	
	
}

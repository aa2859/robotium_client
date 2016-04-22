package com.WebControl.cpic.cmp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.app.Instrumentation;
import android.graphics.Point;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;


import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_SwipSquaredStep extends TestStep {
	private String dragPoint;

	public String getDragPoint() {
		return dragPoint;
	}

	public void setDragPoint(String dragPoint) {
		this.dragPoint = dragPoint;
	}

	public R_SwipSquaredStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setDragPoint((String) xpath.evaluate(
				"ParamBinding[@name='dragPoint']/@value", step,
				XPathConstants.STRING));

	}

	@Override
	public void Excut(Solo solo) {
		WebElement view = TestHelper.findWebElement(this);	
			Point[][] pts = aaa(view);
			
			
			
			Point[] ptt = new Point[9];//九个点赋值
			ptt[0]= pts[1][1];
			ptt[1]= pts[3][1];
			ptt[2]= pts[5][1];
			ptt[3]= pts[1][3];
			ptt[4]= pts[3][3];
			ptt[5]= pts[5][3];
			ptt[6]= pts[1][5];
			ptt[7]= pts[3][5];
			ptt[8]= pts[5][5];
			
	
			String tmp[] = this.dragPoint.split(",");
			int pointIndex[] = new int[tmp.length];
			for(int i=0;i<tmp.length;i++)
			{
				pointIndex[i]= Integer.parseInt(tmp[i]);//带有1,2,3等的string类型数字转换成int
			}
		
			    
			down(ptt[pointIndex[0]]);
			
			for(int i=1;i<pointIndex.length;i++)
			{
				int pt1 = pointIndex[i-1];//上一个点的数字
				int pt2 = pointIndex[i];//下一个点的数字
				Point formp = ptt[pt1-1];//上一个点的数字大于数组下标值1
				Point top = ptt[pt2-1];//下一个点的数字大于数组下标值1
				move(formp,top);
			}
			up(ptt[pointIndex[pointIndex.length-1]-1]);//最后一个值
		
		
		
	}
	
	
			
	
	public Point[][] aaa(WebElement view){
		
		Point[][] pts =  new Point[7][7];
		
	
		int centerX = view.getLocationX();
		int centerY = view.getLocationY();
		// 左上X,左上Y
		int upperLeftX = view.getUpperLeftCornerX();
		int upperLeftY = view.getUpperLeftCornerY();

		Point upperLeftPoint = new Point(upperLeftX, upperLeftY);
		
		Log.i("123", "upperLeftPoint:"+upperLeftPoint.x+","+upperLeftPoint.y);

		// 右上X，右上Y
		int upperRightX = view.getLowerRightCornerX();
		int upperRightY = upperLeftY;
		Point upperRightPoint = new Point(upperRightX, upperRightY);

		// 坐下X,坐下Y
		int lowerLeftX = upperLeftX;
		int lowerLeftY = (centerY - upperLeftY) * 2 + upperLeftY;
		Point lowerLeftPoint = new Point(lowerLeftX, lowerLeftY);

		// 右下X，右下Y
		int lowerRightX = upperRightX;
		int lowerRightY = lowerLeftY;
		Point lowerRightPoint = new Point(lowerRightX, lowerRightY);

		
		int webWidth = upperRightX-upperLeftX;
		int webHeigth = lowerLeftY-upperLeftY;
		
	
		
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<7;j++)
			{
				Point pt = new Point();
				pt.x= upperLeftPoint.x+webWidth/7*(i+1) - webWidth/7/2;
				pt.y = upperLeftPoint.y+webHeigth/7*(j+1) - webHeigth/7/2;
				
				pts[i][j]= pt;
			}
		}
		
		
		
		return pts;
		
	}
	
	
	public void down(Point pt)
	{
		Instrumentation inst = Test.inst;
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		
	MotionEvent	event = MotionEvent.obtain(downTime, eventTime,
				MotionEvent.ACTION_DOWN, pt.x, pt.y, 0);
	
	inst.sendPointerSync(event);
	}
	
	public void move(Point formp,Point top)
	{
		Instrumentation inst = Test.inst;
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		int stepCount = 50;
		
		float y = formp.y;
		float x = formp.x;
		
		float yStep = (top.y - formp.y) / stepCount;
		float xStep = (top.x - formp.x) / stepCount;
		
		for (int j = 0; j < 50; j++) {
			y += yStep;
			x += xStep;
			
			eventTime = SystemClock.uptimeMillis();
			MotionEvent event = MotionEvent.obtain(downTime, eventTime,
					MotionEvent.ACTION_MOVE, x, y, 0);
			inst.sendPointerSync(event);
			
		}
	}
	
	public void up(Point pt)
	{
		Instrumentation inst = Test.inst;
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		Log.i("123", pt.x+","+pt.y);
		MotionEvent event = 
				MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,pt.x, pt.y, 0);
		
		inst.sendPointerSync(event);
	}
		
}

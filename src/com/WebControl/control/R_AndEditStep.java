package com.WebControl.control;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.robotium.solo.Solo;

public class R_AndEditStep extends TestStep {

	private String inputText;// 输入文字
	


	

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}


	public R_AndEditStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setInputText((String) xpath.evaluate(
				"ParamBinding[@name='inputText']/@value", step,
				XPathConstants.STRING));


	}

	@Override
	public String getTagName() {
		
		return "edittext";	
	
	}


	@Override
	public void Excut(Solo solo) {
		
		if((this.getAdvanced()+"").contains("neglect") && this.getAdvanced() != null){
			ArrayList<View> viewList = new ArrayList<View>();;
			
			long endTime = SystemClock.uptimeMillis() + 5000;
			do {
				viewList = TestHelper.screenView(this);;
				if (viewList.size() != 0)
					break;
			} while (SystemClock.uptimeMillis() < endTime);
			
			if(viewList != null){
				View view = viewList.get(this.getIndex());
				this.screenShot(solo);	
				solo.clearEditText((EditText) view);
				solo.enterText((EditText) view, this.getInputText());
				return;
			}
			return;
		}
		
		View view = TestHelper.screen(this);
	
			
			if (view != null) {
			solo.clearEditText((EditText) view);
			solo.enterText((EditText) view, this.getInputText());
			}
	
		else
			throw new IllegalArgumentException("NoControlIsFound");

		this.screenShot(solo);
		/*
		 * // 用ID直接判断 if (this.getId() != null) { View view =
		 * solo.getView(this.getId(),this.getIndex()-1); if (view != null) {
		 * this.screenShot(solo); solo.clearEditText(((EditText) view));
		 * solo.enterText(((EditText) view), this.getInputText());
		 * 
		 * } } else {
		 * 
		 * List<View> listAll = new ArrayList<View>(); listAll =
		 * solo.getViews(); // 找到所有输入框 this.screenShot(solo); List<EditText>
		 * listUser = new ArrayList<EditText>(); List<EditText> listUser2 = new
		 * ArrayList<EditText>(); for (View view : listAll) { if
		 * (view.getClass().toString()
		 * .equalsIgnoreCase("class android.widget.EditText")) {
		 * listUser.add((EditText) view); } }
		 * 
		 * if (this.getTextContent() != null) { for (EditText editText :
		 * listUser) { if (editText.getText().equals(this.getTextContent())) {
		 * listUser2.add(editText); } } } else { listUser2 = listUser; }
		 * 
		 * if (listUser2.size() > 0) {
		 * 
		 * solo.enterText(listUser2.get(this.getIndex() - 1),
		 * this.getInputText()); } else throw new
		 * IllegalArgumentException("NoControlIsFound"); }
		 */
	}


}

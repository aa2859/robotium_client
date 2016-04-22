package com.WebControl.control;




import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;


import android.view.View;

import com.robotium.solo.Solo;




public class R_AndClickStep extends TestStep {
	private String checkText;
	public String getCheckText() {
		if (checkText != null && checkText.trim().isEmpty())
			return null;
		return checkText;
	}
	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}
	
	
	public R_AndClickStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setCheckText((String)xpath.evaluate(
				"ParamBinding[@name='checkText']/@value", step,
				XPathConstants.STRING));
	}
	
	/*public ArrayList<View> screenView(Solo solo,String tagName){
		ArrayList<View> viewList = new ArrayList<View>();
		viewList = solo.getViews();
		
		ArrayList<View> tagList = new ArrayList<View>();
		ArrayList<View> idList = new ArrayList<View>();
		ArrayList<View> textList = new ArrayList<View>();
		
		
		String textCont = "";
		String tvText = "";
		
		if(this.getTextContent() != null){
			textCont = this.getTextContent();
			Log.i("123", "aaa"+textCont);
			View view = solo.onTextView(textCont, this.getIndex());
			Log.i("123", "aaatextlist:"+textList);
			tvText = ((TextView) view).getText()+"";
			Log.i("123", "aaa"+tvText);
			if(tvText.equals(textCont)){
			textList.add(view);
			
			}
		}else{
			textList = viewList;
		}
		
		
		if(this.getId() != null){
			for(View view : viewList){
				if(TestHelper.getId(this.getId()) == view.getId()){	
					
					
					idList.add(view);
					Log.i("123", "idview:"+view);
					
				}
			}
		}else{
			idList = textList;
		}
		
		
		if(tagName != null){
			tagName = this.getTagName().toLowerCase();
			for(View view : idList){
			if(("class android.widget."+tagName).equals(view.getClass().toString().toLowerCase())){
				tagList.add(view);
				}
			}
		}else{
			tagList = idList;
		}
		
		return tagList;
	}*/
	
	
	@Override
	public void Excut(Solo solo) {
		/*List<View> listAll = new ArrayList<View>();
		listAll = solo.getViews();*/
		
		View view = TestHelper.screen(this);
		
		if (view == null )
			throw new IllegalArgumentException("NoControlIsFound");   
		
		this.screenShot(solo);	
		solo.clickOnView(view);
		
		if(getCheckText()!=null)
		{
			if(!solo.waitForText(getCheckText(), 1, 5000, false))
				throw new IllegalArgumentException("NoControlIsFound");  
		}
		/*if(this.getId() != null){
			for(View view : viewList){
				if(TestHelper.getId(this.getId()) == view.getId()){
				idList.add(view);
				Log.i("123", ""+view.getId());
				}
			}
		}else{
			idList = viewList;
		}
	
	
			
		if(idList.size()>0){
			Log.i("123", ""+idList.size());
			if(this.getTextContent() != null) {
				this.screenShot(solo);
				Log.i("123", this.getTextContent());
				solo.clickOnText(this.getTextContent(), this.getIndex());
				
			}else{
				if(this.getTagName() != null){
					for(View view : idList){
						String tagName = this.getTagName().toLowerCase();
						
						if(("class android.widget."+tagName).equals(view.getClass().toString().toLowerCase())){
							Log.i("123", "输入:"+"class android.widget."+tagName);
							Log.i("123", view.getClass().toString().toLowerCase());
							switch (tagName) {
							case "imagebutton":
								solo.clickOnImageButton(this.getIndex() - 1);
								break;
							case "listview":
								solo.scrollListToTop(0);
								solo.clickOnText(this.getTextContent());
								break;
							case "imageview":
								solo.clickOnImage(this.getIndex() - 1);
								break;
							case "checkbox":
								solo.clickOnCheckBox(this.getIndex() - 1);
								break;
							default:
								break;
							}
						}
					}
				}else{
			for(View view : idList){
				this.screenShot(solo);
				solo.clickOnView(view);
				Log.i("123", "idlist");
			}
				}
		}
		}else
			throw new IllegalArgumentException("NoControlIsFound");*/
		
		
		
		
		

		/*
			if (this.getId() != null) {
				View view = solo.getView(this.getId(),this.getIndex()-1);
				this.screenShot(solo);
				solo.clickOnView(view);
			}
			else if (this.getTextContent() != null) {
				solo.clickOnText(this.getTextContent(), this.getIndex());
				this.screenShot(solo);
			}else if (this.getTagName() != null) {
				String tagName = this.getTagName().toLowerCase();// 全部转成小写
				this.screenShot(solo);
				switch (tagName) {
				case "imagebutton":
					solo.clickOnImageButton(this.getIndex() - 1);
					break;
				case "listview":
					solo.scrollListToTop(0);
					solo.clickOnText(this.getTextContent());
					break;
				case "imageview":
					solo.clickOnImage(this.getIndex() - 1);
					break;
				case "checkbox":
					solo.clickOnCheckBox(this.getIndex() - 1);
					break;
				default:
					break;
				}
				
			}//要改
*/		
	
			
	}

	
}

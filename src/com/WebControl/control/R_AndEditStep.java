package com.WebControl.control;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import android.view.View;
import android.widget.EditText;
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

		/*ArrayList<View> viewList = new ArrayList<View>();
		viewList = solo.getViews();

		ArrayList<EditText> classList = new ArrayList<EditText>();
		ArrayList<EditText> textList = new ArrayList<EditText>();
		ArrayList<EditText> idList = new ArrayList<EditText>();
		
		String edText = "";
		String textCont = "";
		for (View view : viewList) {
			if (view.getClass().toString()
					.equalsIgnoreCase("class android.widget.EditText")) {
				classList.add((EditText) view);
			}
			
		}


		if (this.getId() != null) {
			for (EditText editText : classList) {
				if (TestHelper.getId(this.getId()) == editText.getId()) {
					idList.add(editText);
					
				}
			}
		} else {
			idList = classList;
		}
		if (this.getTextContent() != null) {
			for (EditText editText : idList) {
				edText = ""+editText.getText();
				textCont = this.getTextContent();
				if (edText.equals(textCont)) {
					textList.add(editText);
				}
			}
		} else {
			textList = idList;
		}
*/
		
		View view = TestHelper.screen(this);
		
	/*if (textList.size() < this.getIndex())
			this.setIndex(1);
	//throw new IllegalArgumentException("ParameterError");
*/
		if (view != null) {
			this.screenShot(solo);
			
			solo.clearEditText((EditText)view);
			solo.enterText((EditText)view, this.getInputText());
			
		} else
			throw new IllegalArgumentException("NoControlIsFound");

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

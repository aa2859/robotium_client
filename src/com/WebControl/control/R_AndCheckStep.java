package com.WebControl.control;






import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;



import org.w3c.dom.Element;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class R_AndCheckStep extends TestStep {
	
	private String checkMode;
	public R_AndCheckStep(Element step) throws XPathExpressionException
	{
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setCheckMode((String)xpath.evaluate(
				"ParamBinding[@name='checkMode']/@value", step,
				XPathConstants.STRING));
	}
	
	public String getCheckMode() {
		return checkMode;
	}

	public void setCheckMode(String checkMode) {
		this.checkMode = checkMode;
	}

	@Override
	public void Excut(Solo solo) {
		//查找模式
				if (getCheckMode().equalsIgnoreCase("notfound")) {
					long endTime = SystemClock.uptimeMillis() + 10000;//查找时间
					List<View> list = new ArrayList<View>();
					do {
						list = TestHelper.screenView(this);
						solo.sleep(1000);
					} while (SystemClock.uptimeMillis() < endTime);

					if (list.size()>0) {
						throw new IllegalArgumentException("NoControlIsFound");
					}
				} else {
					View view = TestHelper.screen(this);
					if (view == null) {
						throw new IllegalArgumentException("NoControlIsFound");
					}
					int id = view.getId();
					String viewClass = view.getClass().toString();
					String viewText = null;
					if (this.getTextContent() != null) {
						viewText = ((TextView) view).getText() + "";
					}
					this.step.setAttribute("obj", "id:" + id + ",text:" + viewText
							+ ",class:" + viewClass);
					
				}
				this.screenShot(solo);
				
				
	}
}

package com.WebControl.control;





import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;



import org.w3c.dom.Element;








import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;

public class R_EditStep extends TestStep {
	
	
	private String inputText;// 输入文字
	
	public String getInputText() {
		return TestHelper.getTrueString(inputText) ;//时间变量替换
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public R_EditStep(Element step) throws XPathExpressionException
	{
		super(step);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setInputText((String)xpath.evaluate(
				"ParamBinding[@name='inputText']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public String getTagName() {
		if(super.getTagName()==null){
			return "input";
		}
		return super.getTagName();
	}
	
	
	
	@Override
	public void Excut(Solo solo)
	{
		
		this.screenShot(solo);
		
		 if ((this.getAdvanced()+"").equals("neglect")) {
				String xPath = this.XPath();
				if (this.getUserXPath() != null){
					xPath = this.getUserXPath();
				}
			
				if(TestHelper.solo.waitForWebElement(By.xpath(xPath), 5000, false)){
					WebElement we = TestHelper.findWebElement(this);
				
					solo.enterTextInWebElement(By.xpath(this.XPath()), this.getInputText());
					return;
				}else{
					return;
				}	
			}
		
		
		WebElement we = TestHelper.findWebElement(this);
		if(we==null)
			throw new IllegalArgumentException("NoControlIsFound"); 
		solo.enterTextInWebElement(By.xpath(this.XPath()), this.getInputText());
		
		
		
	}
	
	
}

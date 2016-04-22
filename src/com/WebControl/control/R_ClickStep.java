package com.WebControl.control;





import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;








import org.w3c.dom.Element;












import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;


import android.util.Log;
import android.widget.TextView;



public class R_ClickStep extends TestStep {
	
	private String checkText;
	public String getCheckText() {
		if (checkText != null && checkText.trim().isEmpty())
			return null;
		return checkText;
	}
	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}
	
	private boolean untilNotFound;
	public boolean getUntilNotFound() {
		return untilNotFound;
	}
	public void setUntilNotFound(boolean untilNotFound) {
		this.untilNotFound = untilNotFound;
	}
	
	
	public R_ClickStep(Element step) throws XPathExpressionException
	{
		super(step);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setUntilNotFound(xpath.evaluate(
				"ParamBinding[@name='untilNotFound']/@value", step,
				XPathConstants.STRING).equals("true"));
		
		this.setCheckText((String)xpath.evaluate(
				"ParamBinding[@name='checkText']/@value", step,
				XPathConstants.STRING));

	}

	@Override
	public void Excut(Solo solo)
	{
		
		
	
		
		
		
		if(!this.getUntilNotFound())//是否为连续点击
		{
			
			
			 if ((this.getAdvanced()+"").equals("neglect")) {
					String xPath = this.XPath();
					if (this.getUserXPath() != null){
						xPath = this.getUserXPath();
					}
				
					if(TestHelper.solo.waitForWebElement(By.xpath(xPath), 5000, false)){
						WebElement web = TestHelper.findWebElement(this);
						TestHelper.solo.clickOnWebElement(web);
						return;
					}else{
						return;
					}
					
				}
			
			WebElement we = TestHelper.findWebElement(this);
			
			solo.sleep(100);
			this.screenShot(solo);
			if(we!=null)
			{	
			
				this.clickOnWebElement(we);
				
			}else if(this.getTextContent()!=null)
				{
					//找安卓控件
					if(solo.waitForText(this.getTextContent(),this.getIndex(),1000)){
						TextView bt = (TextView) solo.getTextView(this.getTextContent(), this.getIndex());
						
						solo.clickOnView(bt);
					
					}else{
						throw new IllegalArgumentException("NoControlIsFound"); 
					}
			}else{
				throw new IllegalArgumentException("NoControlIsFound"); 
			}
			
			//Log.i("123", "检查:"+getCheckText());
			//检查会消失的弹出框
			if(getCheckText()!=null)
			{
				
				if(!solo.waitForText(getCheckText(), 1, 5000, false))
				{
					throw new IllegalArgumentException("NoControlIsFound"); 
				}
			}
			
		}else{
			String xpath = this.getUserXPath()==null?this.XPath():this.getUserXPath();
			//Log.i("123", "UntilNotFound点击xpath:"+xpath);
			if(!solo.waitForWebElement(By.xpath(xpath)))//如果找不到就直接结束了
			{
				throw new IllegalArgumentException("NoControlIsFound"); 
			}
			this.screenShot(solo);
			int flag =0;
			
			while(solo.waitForWebElement(By.xpath(xpath), 1000, false))
			{
				
				WebElement weUntil = solo.getWebElement(By.xpath(xpath),this.getIndex()-1);
				
				//Log.i("123", "UntilNotFound点击次数:"+flag);
				Log.i("123", "UntilNotFound点击坐标:"+weUntil.getLocationX()+" "+weUntil.getLocationY());
				this.clickOnWebElement(weUntil);
				
				if(++flag>3)break;
				//保险箱点击0.5秒一次,点3次,其余项目2秒一次,点5次
				solo.sleep(1000);
			}
			
		}
		
	}
	
	
	
}

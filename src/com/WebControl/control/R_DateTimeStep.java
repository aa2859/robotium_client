package com.WebControl.control;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.robotium.solo.Solo;

public class R_DateTimeStep extends TestStep {

	private String dateTime;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public R_DateTimeStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setDateTime((String) xpath.evaluate(
				"ParamBinding[@name='dateTime']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(Solo solo) {
		// TODO Auto-generated method stub
		String dateTimeStr = this.getDateTime();
		String dateStr = null;
		String timeStr = null;
		if (dateTimeStr.contains("-") && dateTimeStr.contains(":")) {
			String[] dateTime = dateTimeStr.split(" ");
			dateStr = dateTime[0];
			timeStr = dateTime[1];
		} else {
			if (dateTimeStr.contains("-")) {
				dateStr = dateTimeStr;
			} else if (dateTimeStr.contains(":")) {
				timeStr = dateTimeStr;
			}
		}
		
		if (dateStr != null) {
			this.setTagName("DatePicker");
			View view = TestHelper.screen(this);
			Log.i("123", view+"");
			
			if (view != null) {
				DatePicker datePicker = (DatePicker) view;

				String date[] = dateStr.split("-");

				int mYear = Integer.parseInt(date[0]);
				int mMonth = Integer.parseInt(date[1]);
				int mDay = Integer.parseInt(date[2]);
				solo.setDatePicker(datePicker, mYear, mMonth - 1, mDay);

			}
		}
		if (timeStr != null) {
			this.setTagName("TimePicker");
			View view = TestHelper.screen(this);
			if (view != null) {
				TimePicker timePicker = (TimePicker) view;
				String time[] = timeStr.split(":");

				int mHour = Integer.parseInt(time[0]);
				int mMinute = Integer.parseInt(time[1]);
				solo.setTimePicker(timePicker, mHour, mMinute);
			}

		}
		
		
		
		this.screenShot(solo);
	}
}

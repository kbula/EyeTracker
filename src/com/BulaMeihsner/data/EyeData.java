package com.BulaMeihsner.data;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EyeData extends Coordinates{
	
	private float x;
	private float y;
	private String date;
	
	public EyeData(float x, float y )
	{
		super(x,y);
		long currentDateTime = System.currentTimeMillis();
		Date currentDate = new Date(currentDateTime);
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
		
		date = df.format(currentDate);
		this.x = x;
		this.y = y;
		
	}
	
	
	public String getDate()
	{
		return date;
	}
}

package com.BulaMeihsner.data;

import android.graphics.Point;
import java.io.Serializable;

public class FPoint implements Serializable{
	private static final long serialVersionUID = 1L;
	public double x;
	public double y;
	
	
	
	public FPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}


	public Point getPoint() {
		return new Point((int)x,(int)y);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x+","+y;	
		}
}

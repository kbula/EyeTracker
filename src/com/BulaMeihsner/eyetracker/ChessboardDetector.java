package com.BulaMeihsner.eyetracker;

//import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.BulaMeihsner.data.FPoint;

/**
 * Znajduje punkt œrodka szachownicy (ale takiej jak na polskich samolotach - z 4 polami/2 czarne/2 bia³e)
 * Szachownica musi mieæ czarne pola NE i SW!
 * @author pawel
 *
 */
public class ChessboardDetector {
	//TODO to jakieœ logi mo¿e zapisywaæ jak na razie nie widzê u¿ytecznoœci
	//Logger log = Logger.getLogger(ChessboardDetector.class);
	FPoint mid = new FPoint(0,0);
	
	/**
	 * Zwraca ostanio znalezione wspó³rzêdne œrodka szachownicy
	 * @return
	 */
	public FPoint getMid() {
		return mid;
	}

	/**
	 * Znajduje szachownicê i zwraca obrazek z zaznaczonym œrodkiem
	 * W mid wspó³rzêdne
	 * @param image
	 * @return
	 */
	public Mat detect(Mat image) {
		Mat image1 = new Mat();
		image.copyTo(image1);
		Mat image2 = new Mat();
		Imgproc.cvtColor(image1, image2, Imgproc.COLOR_RGB2GRAY);
		Imgproc.integral(image2, image2);

		double max = 0;
		Size size = image2.size();
//		System.out.println("Size="+size);
//		System.out.println("h="+size.height);
//		System.out.println("w="+size.width);
		int x = -1;
		int y = -1;
		int rw = -1;
		for(int w=1;w<40;w+=10)
			for(int j=w;j<size.height-w-1;j+=10)
				for(int i=w;i<size.width-w-1;i+=10) {
					double se = calcArea(image2, i, j, w);
					double ne = calcArea(image2, i-w, j, w);
					double nw = calcArea(image2, i-w, j-w, w);
					double sw = calcArea(image2, i, j-w, w);
					double result = (se+nw-ne-sw)/w;
					if(result>max) {
						//System.out.println("result="+result+" for ("+i+","+j+") w="+w);
						//System.out.println("se="+se+" ne="+ne+" nw="+nw+" sw="+sw);
						max = result;
						x = i;
						y = j;
						rw = w;
					}
				}
		//TODO tutaj siê coœ logowa³o TM
		//log.trace("x,y = "+x+","+y+" w="+rw);
		//TODO: poprawiæ!!!
		if(x<rw+5) x=rw+6;
		if(y<rw+5) y=rw+6;
		int x2=x;
		int y2=y;
//		if()
		int w2 = rw;
		int w = rw;
		//for(int w=rw-5;w<rw+5;w+=1)
			for(int j=y-5;j<y+5;j+=1)
				for(int i=x-5;i<x+5;i+=1) {
					double se = calcArea(image2, i, j, w);
					double ne = calcArea(image2, i-w, j, w);
					double nw = calcArea(image2, i-w, j-w, w);
					double sw = calcArea(image2, i, j-w, w);
					double result = (se+nw-ne-sw)/w;
					if(result>max) {
//						System.out.println("result="+result+" for ("+i+","+j+") w="+w);
//						System.out.println("se="+se+" ne="+ne+" nw="+nw+" sw="+sw);
						max = result;
						x2 = i;
						y2 = j;
						w2 = w;
					}
				}
		x=x2;
		y=y2;
		rw=w2;
		Core.rectangle(image1, new Point(x-3,y-3), new Point(x+2, y+2), new Scalar(0,255,0), 2);
		
		//Core.rectangle(image1, new Point(x,y), new Point(x+rw, y+rw), new Scalar(255,0,0), 1);
		mid = new FPoint(x,y);
		return image1;
	}

	double calcCross(Mat image,int i,int j,int w) {
		double se = calcArea(image, j, i, w);
		double ne = calcArea(image, j-w, i, w);
		double nw = calcArea(image, j-w, i-w, w);
		double sw = calcArea(image, j, i-w, w);
		//		double result = se+nw-ne-sw;
		double result = se+nw-ne-sw;
		return result;
	}
	double calcArea(Mat image,int i,int j,int w) {
		double result = 0;
		try{
			double a = image.get(j, i)[0];
			double b = image.get(j+w, i)[0];
			double c = image.get(j, i+w)[0];
			double d = image.get(j+w, i+w)[0];
			result = d - c -b +a;
		}catch(Exception ex) {
			throw new RuntimeException("Exception = ("+i+","+j+") "+w);
		}
		return result;
	}

	String dispArray(double[] a) {
		String txt = "";
		for(int i=0;i<a.length;i++) txt+=a[i]+",";
		return txt;
	}
}

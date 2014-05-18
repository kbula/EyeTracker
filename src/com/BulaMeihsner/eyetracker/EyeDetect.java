package com.BulaMeihsner.eyetracker;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class EyeDetect {

	public static Mat getImageWithContours(Mat image,int threshold) {

		Mat image2 = new Mat();
		image.copyTo(image2);
		Imgproc.cvtColor(image2, image2, Imgproc.COLOR_RGB2GRAY);
		Imgproc.equalizeHist(image2, image2);
		Imgproc.threshold(image2, image2, threshold, 255, Imgproc.THRESH_BINARY);
		Imgproc.dilate(image2, image2, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
		Imgproc.erode(image2, image2, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5)));    
		Imgproc.dilate(image2, image2, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(7,7)));
		Imgproc.erode(image2, image2, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(4,4)));    
		Imgproc.Canny(image2, image2, threshold, threshold*2, 3, true);
		return image2;
	}
	
	
	public static int[] getContours(Mat image,int threshold) {
		Mat contimage = getImageWithContours(image,threshold);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		Imgproc.findContours(contimage, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
//		System.out.println("Contours found: "+contours.size());

		double maxArea=0;
		int imax=-1;
		for(int i=0; i< contours.size();i++){
			double area = Imgproc.contourArea(contours.get(i));
//			Rect rect = Imgproc.boundingRect(contours.get(i));
//			int d = 20;
			if(area>maxArea 
//					&& 
//					rect.x>destimage.width()/d &&  //tylko mniej wiêcej w œrodku
//					rect.y>destimage.height()/d &&
//					rect.x+rect.width<destimage.width()-destimage.width()/d &&
//					rect.y+rect.height<destimage.height()-destimage.height()/d
					) {
				maxArea = area;
				imax = i;
			}
	//		System.out.println("Area="+area);
		}

		if(imax>=0) {		
//			Rect rect = Imgproc.boundingRect(contours.get(imax));
//			System.out.println("H="+rect.height);
//			if (rect.height > 5){ //28
//				System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
//			Core.rectangle(destimage, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
//			}
			Imgproc.drawContours(image, contours, imax, new Scalar(255,0,0));
			
			Moments moments = Imgproc.moments(contours.get(imax));
			double m10 = moments.get_m10();
			double m01 = moments.get_m01();
			double area = moments.get_m00();
			int posX = (int) (m10/area);
			int posY = (int) (m01/area);
			MatOfPoint2f corners2f = new MatOfPoint2f();
			contours.get(imax).convertTo(corners2f, CvType.CV_32FC2);
			Point center = new Point(0,0);
			float[] radius = new float[2];
			Imgproc.minEnclosingCircle(corners2f, center, radius);
			Core.circle(image, center, (int)radius[0] ,new Scalar(255,0,0));
			Core.circle(image, center, 3 ,new Scalar(255,0,0));
			return new int[] {posX,posY};
		}
		return new int[] {-1,-1};

	}
}

package com.BulaMeihsner.eyetracker;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import android.graphics.Bitmap;

public class EyeTrackerHelper {
	
	public static Bitmap findEye(Bitmap camera , int threshold)
	{
    	Mat image = new Mat();
    	Utils.bitmapToMat(camera, image);
    	int[] xy  = EyeDetect.getContours(image, threshold);
    	Core.circle(image, new Point(xy[0],xy[1]), 5,new Scalar(0,0,255),-10);
    	Utils.matToBitmap(image, camera);
    	
    	return camera;
	}
}
